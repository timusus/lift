import SwiftUI
import shared

struct SessionDetailView: View {
    
    @StateObject var viewModel: SessionDetailViewModel
    
    let onEditSessionExercise: (_ sessionExercise: SessionExercise) -> Void
    
    let onNavigateBack: () -> Void
        
    private let timer = Timer.publish(
        every: 1,
        on: .main,
        in: .common
    )
        .autoconnect()
    
    @State private var timeElapsed: String? = nil
        
    var body: some View {
        ZStack {
            switch (viewModel.viewState) {
            case .loading:
                Text("Loading")
            case .existingSessionDialog(let session):
                Text("Loading")
                    .alert(isPresented: .constant(true)) {
                        Alert(
                            title: Text("Continue existing session?"),
                            message: Text("You have an incomplete session from \(viewModel.dateFormatter.string(from: Date(timeIntervalSince1970: TimeInterval(session.startDate.epochSeconds))))"),
                            primaryButton: .default(
                                Text("Continue session"),
                                action: {
                                    Task {
                                        await viewModel.useExistingSession(session: session)
                                    }
                                }
                            ),
                            secondaryButton: .default(
                                Text("New session"),
                                action: {
                                    Task {
                                        await viewModel.createNewSession()
                                    }
                                }
                            )
                        )
                    }

            case .ready(let session):
                VStack {
                    ScrollView {
                        LazyVStack(alignment: .leading) {
                            ForEach(session.exercises, id: \.id) { exercise in
                                SessionExerciseRow(
                                    sessionExercise: exercise,
                                    isCurrentExercise: session.currentExercise?.id == exercise.id,
                                    isResting: viewModel.isResting) {
                                       onEditSessionExercise(exercise)
                                    }
                                    .frame(minHeight: 40)
                            }
                        }
                        .padding()
                    }
                    Button(
                        action: {
                            if session.isComplete {
                                onNavigateBack()
                            } else {
                                Task {
                                    await viewModel.updateProgress(session: session)
                                }
                            }
                        },
                        label: {
                            HStack(spacing: 10) {
                                Text(fabText(session: session, resting: viewModel.isResting))
                                    .fontWeight(.semibold)
                                
                                Image(systemName: "chevron.right.2")
                                    .font(.body)
                            }
                            .foregroundColor(onSecondary)
                            .padding()
                            .background(secondary, in: Capsule())
                            .shadow(color: secondary.opacity(0.3), radius: 5, x: 3, y: 5)
                            
                        }
                    )
                    .padding()
                    .frame(maxWidth: .infinity, alignment: .trailing)
                }
            }
        }
        .navigationTitle(viewModel.routine.name)
        .toolbar {
            if case .ready(let session) = viewModel.viewState {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Text(timeElapsed ?? "")
                        .onReceive(timer) { time in
                            timeElapsed = formatTime(
                                interval: Date().timeIntervalSince1970 - TimeInterval(session.startDate.epochSeconds)
                            )
                        }
                }
            }
        }
        .task {
            await viewModel.loadData()
        }
        .task {
            await viewModel.observeSessionManager()
        }
    }
    
    func formatTime(interval: TimeInterval) -> String? {
        let formatter = DateComponentsFormatter()
        formatter.allowedUnits = [.hour, .minute, .second]
        formatter.unitsStyle = .positional
        return formatter.string(from: interval)
    }
    
    func fabText(session: Session, resting: Bool) -> String {
        var text = "Next"
        if session.isComplete {
            text = "Complete"
        } else {
            if resting {
                text = "Skip"
            }
        }
        return text
    }
}
