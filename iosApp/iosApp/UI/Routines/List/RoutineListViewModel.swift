import Foundation
import Combine
import shared
import KMPNativeCoroutinesAsync

enum RoutineListViewState {
    case loading
    case ready(routines: [Routine])
}

@MainActor
class RoutinesViewModel: ObservableObject {

    @Published public var viewState: RoutineListViewState = .loading

    private let repository: RoutineRepository

    private let dependencies: GlobalDependencies

    init(dependencies: GlobalDependencies) {
        self.dependencies = dependencies
        repository = dependencies.routineRepository
    }

    func getRoutines() async {
//        do {
//            let stream = asyncSequence(for: repository.getRoutinesNative(ids: nil))
//            for try await routines in stream {
//                viewState = .ready(routines: routines)
//            }
//        } catch {
//            print("Failed with error: \(error)")
//        }
    }
}
