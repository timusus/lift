import Foundation
import SwiftUI
import shared

enum NavigationDestination: Hashable {
    case routineDetail(Routine)
}

struct RoutinesNavigationStack: View {
    let dependencies: GlobalDependencies

    @Binding var destinations: [NavigationDestination]
    
    @State var editingSessionExercise: SessionExercise? = nil

    var body: some View {
        NavigationStack(path: $destinations) {
            RoutinesView(viewModel: RoutinesViewModel(dependencies: dependencies)) { routine in
                destinations.append(NavigationDestination.routineDetail(routine))
            }
            .navigationTitle("Routines")
            .navigationDestination(
                for: NavigationDestination.self,
                destination: { destination in
                    switch destination {
                    case let .routineDetail(routine):
                        SessionDetailView(
                            viewModel: SessionDetailViewModel(
                                dependencies: dependencies,
                                routine: routine
                            ),
                            onEditSessionExercise: { sessionExercise in
                                editingSessionExercise = sessionExercise
                            },
                            onNavigateBack: {
                                destinations.popLast()
                            }
                        )
//                        .navigationTitle(routine.name)
                    }
                }
            )
        }
        .sheet(item: $editingSessionExercise) { sessionExercise in
            EditExerciseSheet(sessionExercise: sessionExercise)//, weight: sessionExercise.routineExercise.weight)
                .presentationDetents([.medium])
        }
    }
}

extension SessionExercise: Identifiable {}
