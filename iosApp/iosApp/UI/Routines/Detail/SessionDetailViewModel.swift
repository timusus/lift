import Foundation
import Combine
import shared
import KMPNativeCoroutinesAsync

public enum SessionDetailViewState: Equatable {
    case loading
    case existingSessionDialog(session: Session)
    case ready(session: Session)
}

@MainActor
class SessionDetailViewModel: ObservableObject {

    @Published public var viewState: SessionDetailViewState = .loading

    @Published public var isResting: Bool = false
    
    let routine: Routine
    let dependencies: GlobalDependencies

    let routineRepository: RoutineRepository
    let sessionRepository: SessionRepository
    let sessionManager: SessionManager
    
    let dateFormatter: DateFormatter

    init(dependencies: GlobalDependencies, routine: Routine) {
        self.dependencies = dependencies
        self.routine = routine

        routineRepository = dependencies.routineRepository
        sessionRepository = dependencies.sessionRepository
        sessionManager = SessionManager(sessionRepository: sessionRepository)
        
        dateFormatter = DateFormatter()
        dateFormatter.dateStyle = .medium
        dateFormatter.timeStyle = .short
    }
    
    func loadData() async {
        viewState = .loading
     
        do {
            let recentDate = Date.now.addingTimeInterval(TimeInterval(-(12 * 60 * 60)))
            
            let stream = asyncSequence(for: sessionRepository.getSession(routine: routine)).filter{$0.isComplete == false}
            if let existingSession = try await stream.first(where: { _ in true }),
                Date(timeIntervalSince1970: TimeInterval(existingSession.startDate.epochSeconds)) > recentDate  {
                
                viewState = .existingSessionDialog(session: existingSession)
            } else {
               await createNewSession()
            }
        } catch {
            // Todo: ViewState.error
        }
    }
    
    func createNewSession() async {
        viewState = .loading
        
        do {
            for try await session in asyncSequence(for: sessionRepository.createNewSession()(routine: routine)) {
                viewState = .ready(session: session)
            }
        } catch {
            // Handle error
        }
    }
    
    func useExistingSession(session: Session) async {
        viewState = .loading
        
        do {
            for try await sessions in asyncSequence(for: sessionRepository.getSessions(sessionIds: [session.id])) {
                if let session = sessions.first {
                    viewState = .ready(session: session)
                } else {
                    // Handle error
                }
            }
        } catch {
            // Handle error
        }
    }

    func updateProgress(session: Session) async {
        do {
            let _ = try await asyncFunction(for: sessionManager.updateProgress(session: session))
        } catch {
            print("updateProgress failed: \(error)")
        }
    }

    func observeSessionManager() async {
        do {
            let stream = asyncSequence(for: sessionManager.isRestingFlow)
            for try await data in stream {
                isResting = data.boolValue
            }
        } catch {
            print("isRestingNative failed: \(error)")
        }
    }
}
