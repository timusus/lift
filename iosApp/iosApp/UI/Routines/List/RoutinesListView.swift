import SwiftUI
import shared

struct RoutinesView: View {

    @StateObject var viewModel: RoutinesViewModel

    let onSelected: (_ routine: Routine) -> Void

    var body: some View {
        ZStack {
            switch (viewModel.viewState) {
            case .loading:
                Text("Loading")
            case .ready(let routines):
                ScrollView {
                    LazyVStack(alignment: .leading) {
                        ForEach(routines, id: \.id) { routine in
                            RoutineRow(routine: routine) {
                                onSelected(routine)
                            }
                            .frame(minHeight: 40)
                        }
                    }
                    .padding()
                }
            }
        }
        .task {
            await viewModel.getRoutines()
        }
    }
}