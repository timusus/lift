
import Foundation
import SwiftUI
import shared

struct RoutineRow: View {
    let routine: Routine

    let onSelected: () -> Void

    var body: some View {
        ZStack(alignment: .leading) {
            RoundedRectangle(cornerRadius: 8, style: .continuous)
            .fill(surfaceVariant)

            VStack(alignment: .leading) {
                Text(routine.name).font(.title3)
                Spacer().frame(height: 4)
                Text(
                    routine.exercises.map {
                        $0.exercise.name
                    }
                    .joined(separator: ", ")
                )
            }
            .padding()
        }
        .onTapGesture(perform: { onSelected() })
    }
}
