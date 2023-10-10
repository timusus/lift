import Foundation
import SwiftUI
import shared

struct SessionExerciseRow: View {
    let sessionExercise: SessionExercise
    let isCurrentExercise: Bool
    let isResting: Bool
    let onSelected: () -> Void

    var body: some View {
        ZStack(alignment: .leading) {
            RoundedRectangle(cornerRadius: 8, style: .continuous)
            .fill(surfaceVariant)

            VStack(alignment: .leading) {
                HStack {
                    Image(systemName: "dumbbell")
                    .padding(.horizontal, 5)

                    VStack(alignment: .leading) {
                        Text(sessionExercise.routineExercise.exercise.name).font(.caption)
                        Spacer(minLength: 5)
                        Text("\(sessionExercise.sets) sets x \(sessionExercise.reps) reps")
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    
                    if let weight = sessionExercise.weight ?? sessionExercise.routineExercise.weight {
                        Text("\(weight.stringValue) kg")
                    }
                }

                if isCurrentExercise {
                    HStack {
                        if (isResting) {
                            Text("Resting")
                        } else {
                            Text("Set \(sessionExercise.currentSet + 1) of \(sessionExercise.sets)")
                        }
                    }
                    .padding(.top)
                }
            }
            .padding()
        }
        .onTapGesture(perform: onSelected)
    }
}
