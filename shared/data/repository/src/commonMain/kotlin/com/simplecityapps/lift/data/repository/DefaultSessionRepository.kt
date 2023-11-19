package com.simplecityapps.lift.data.repository

import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
import com.simplecityapps.lift.common.repository.RoutineRepository
import com.simplecityapps.lift.common.repository.SessionRepository
import com.simplecityapps.lift.data.model.toFirestoreData
import com.simplecityapps.lift.data.model.toSession
import com.simplecityapps.lift.data.model.toSessionExercise
import com.simplecityapps.lift.logging.logcat
import com.simplecityapps.shuttle.common.network.LocalJsonDataSource
import com.simplecityapps.shuttle.common.network.RemoteDataSource
import com.simplecityapps.shuttle.common.network.model.toInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class DefaultSessionRepository(
    private val localDataSource: AppDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val routineRepository: RoutineRepository,
    private val localJsonDataSource: LocalJsonDataSource,
) : SessionRepository {

    // Session Exercises

    override suspend fun upsertSessionExercise(
        sessionExercise: SessionExercise
    ) {
        return localDataSource.upsertSessionExercise(
            id = sessionExercise.id,
            sets = sessionExercise.sets,
            reps = sessionExercise.reps,
            weight = sessionExercise.weight,
            sessionId = sessionExercise.sessionId,
            routineExerciseId = sessionExercise.routineExercise.id
        )
    }

    override fun getSessionExercises(sessionIds: Collection<String>?): Flow<List<SessionExercise>> {
        return combine(
            routineRepository.getRoutineExercises(),
            localDataSource.getSessionExercises(sessionIds)
        ) { routineExercises, sessionExercises ->
            sessionExercises.map { sessionExercise ->
                sessionExercise.toSessionExercise(routineExercises.first { it.id == sessionExercise.routineExerciseId })
            }
        }
    }

    override suspend fun updateSessionExercise(sessionExercise: SessionExercise) {
        localDataSource.updateSessionExercise(
            sessionExerciseId = sessionExercise.id,
            currentSet = sessionExercise.currentSet,
            endDate = sessionExercise.endDate,
            weight = sessionExercise.weight
        )
    }

    override suspend fun deleteSessionExercises(session: Session) {
        localDataSource.deleteSessionExercises(
            sessionId = session.id
        )
    }

    // Sessions

    override suspend fun upsertSession(session: Session) {
        return localDataSource.upsertSession(
            id = session.id,
            startDate = session.startDate,
            endDate = session.endDate,
            routineId = session.routine.id
        )
    }

    override fun getSessions(sessionIds: Collection<String>?): Flow<List<Session>> {
        return combine(
            localDataSource.getSessions(sessionIds),
            routineRepository.getRoutines(),
            getSessionExercises()
        ) { sessions, routines, sessionExercises ->
            sessions.map { session ->
                session.toSession(routine = routines.first { session.routineId == it.id },
                    sessionExercises = sessionExercises.filter { it.sessionId == session.id })
            }
        }
    }

    override fun getSession(sessionId: String): Flow<Session?> {
        return combine(
            localDataSource.getSession(sessionId),
            routineRepository.getRoutines(),
            getSessionExercises()
        ) { session, routines, sessionExercises ->
            session?.toSession(
                routine = routines
                    .first { routine -> session.routineId == routine.id },
                sessionExercises = sessionExercises
                    .filter { sessionExercise -> sessionExercise.sessionId == session.id }
            )
        }
    }

    override fun getSessionsForRoutine(routineId: String): Flow<List<Session>> {
        return combine(
            localDataSource.getSessionsForRoutine(routineId),
            routineRepository.getRoutine(routineId),
            getSessionExercises()
        ) { sessions, routine, sessionExercises ->
            sessions.mapNotNull { session ->
                routine?.let {
                    session.toSession(
                        routine = routine,
                        sessionExercises = sessionExercises
                            .filter { sessionExercise -> sessionExercise.sessionId == session.id }
                    )
                }
            }
        }
    }

    override fun getLatestSessionForRoutine(routineId: String): Flow<Session?> {
        return getSessionsForRoutine(routineId).map { sessions -> sessions.maxByOrNull { session -> session.endDate ?: session.startDate } }
    }

    override suspend fun deleteSession(session: Session) {
        localDataSource.deleteSession(
            sessionId = session.id
        )
    }

    override suspend fun updateSession(session: Session) {
        localDataSource.updateSession(
            sessionId = session.id,
            currentExerciseId = session.currentExercise?.id,
            endDate = session.endDate
        )
    }

    override suspend fun importData() {
        val exercises = localJsonDataSource.getExercises()
        val routineExercises = localJsonDataSource.getRoutineExercises(exercises)
        val routines = localJsonDataSource.getRoutines(routineExercises)
        val sessionExercises = localJsonDataSource.getSessionExercises(routineExercises)
        val sessions = localJsonDataSource.getSessions(routines, sessionExercises)
        sessions.forEach {
            upsertSession(it)
        }
        sessionExercises.forEach {
            upsertSessionExercise(it)
        }
    }

    override suspend fun pullData(userId: String) {
        val sessionLastSyncTime = localDataSource.getSessionLastSyncTime()

        // Sessions
        remoteDataSource.getSessionEntitiesAfter(
            userId = userId,
            lastSynced = sessionLastSyncTime
        )
            .also { logcat { "Retrieved ${it.size} sessions" } }
            .forEach { remoteSession ->
                val localSession = localDataSource.getSession(remoteSession.id).firstOrNull()
                if (localSession == null || localSession.last_modified < remoteSession.lastUpdated.toInstant()) {
                    localDataSource.upsertSession(
                        id = remoteSession.id,
                        startDate = remoteSession.startDate.toInstant(),
                        endDate = remoteSession.endDate?.toInstant(),
                        routineId = remoteSession.routineId,
                        lastModified = remoteSession.lastUpdated.toInstant(),
                        lastSynced = remoteSession.lastUpdated.toInstant()
                    )
                }
            }

        val sessionExerciseLastSyncTime = localDataSource.getSessionExerciseLastSyncTime()

        // Session Exercises
        remoteDataSource.getSessionExerciseEntitiesAfter(
            userId = userId,
            lastSynced = sessionExerciseLastSyncTime
        )
            .also { logcat { "Retrieved ${it.size} session exercises" } }
            .forEach { remoteSessionExercise ->
                val localSessionExercise = localDataSource.getSessionExercise(remoteSessionExercise.id).firstOrNull()
                if (localSessionExercise == null || localSessionExercise.last_modified < remoteSessionExercise.lastUpdated.toInstant()) {
                    localDataSource.upsertSessionExercise(
                        id = remoteSessionExercise.id,
                        sets = remoteSessionExercise.sets,
                        reps = remoteSessionExercise.reps,
                        weight = remoteSessionExercise.weight,
                        sessionId = remoteSessionExercise.sessionId,
                        routineExerciseId = remoteSessionExercise.routineExerciseId,
                        lastModified = remoteSessionExercise.lastUpdated.toInstant(),
                        lastSynced = remoteSessionExercise.lastUpdated.toInstant()
                    )
                }
            }
    }

    override suspend fun pushData(userId: String) {
        // Sessions

        val sessionsToSync = localDataSource.getSessionsToSync().first()
        if (sessionsToSync.isNotEmpty()) {
            logcat { "Syncing ${sessionsToSync.size} sessions" }
            remoteDataSource.pushSessionEntities(
                userId = userId,
                sessionRequests = sessionsToSync.map { sessionEntity -> toFirestoreData(sessionEntity) }
            )

            localDataSource.updateSessionLastSynced(
                ids = sessionsToSync.map { sessionEntity -> sessionEntity.id },
                time = Clock.System.now()
            )
        }

        // Session Exercises

        val sessionExercisesToSync = localDataSource.getSessionExercisesToSync().first()
        if (sessionExercisesToSync.isNotEmpty()) {
            logcat { "Syncing ${sessionExercisesToSync.size} session exercises" }
            remoteDataSource.pushSessionExerciseEntities(
                userId = userId,
                sessionExerciseRequests = sessionExercisesToSync.map { sessionExerciseEntity -> toFirestoreData(sessionExerciseEntity) }
            )

            localDataSource.updateSessionExerciseLastSynced(
                ids = sessionExercisesToSync.map { routineEntity -> routineEntity.id },
                time = Clock.System.now()
            )
        }
    }
}
