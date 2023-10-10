import Foundation
import shared


struct GlobalDependencies {
    
    let driverFactory : DriverFactory
    let databaseHelper : DatabaseHelper
    let appDataSource : AppDataSource
    let exerciseRepository: ExerciseRepository
    let routineRepository: RoutineRepository
    let sessionRepository: SessionRepository
    
    init() {
        self.driverFactory = shared.DriverFactory()
        self.databaseHelper = shared.DatabaseHelper(driverFactory: driverFactory)
        self.appDataSource = shared.AppDataSource(database: databaseHelper.database)
        self.exerciseRepository = shared.LocalExerciseRepository(dataSource: appDataSource)
        self.routineRepository = shared.LocalRoutineRepository(dataSource: appDataSource, exerciseRepository: exerciseRepository)
        self.sessionRepository = shared.LocalSessionRepository(dataSource: appDataSource, routineRepository: routineRepository)
    }    
}
