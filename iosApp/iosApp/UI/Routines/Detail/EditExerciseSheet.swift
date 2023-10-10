import Foundation
import SwiftUI
import shared

struct EditExerciseSheet: View {
    
    let sessionExercise: SessionExercise
    
//    @Binding var weight: String
    
    var body: some View {
        VStack {
            Text("Edit \(sessionExercise.routineExercise.exercise.name)")
//            TextField(
//                "Weight",
//                text: $weight
//            )
            .padding(8)
            .border(.secondary)
        }
        .padding(20)
    }
}
