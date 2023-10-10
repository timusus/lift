import SwiftUI
import shared

struct ContentView: View {

    let dependencies: GlobalDependencies

    @State private var selection = 1

    @State private var destinations = [NavigationDestination]()

    init(dependencies: GlobalDependencies) {
        self.dependencies = dependencies
    }

    var body: some View {
        TabView(selection: $selection) {
            Group {
                NavigationStack {
                    Text("Hello, a!")
                }
                .padding()
                .frame(maxHeight: .infinity)
                .tabItem {
                    Label("Home", systemImage: "house")
                }
                .tag(0)

                RoutinesNavigationStack(dependencies: dependencies, destinations: $destinations)
                .tabItem {
                    Label("Routines", systemImage: "stopwatch")
                }
                .tag(1)

                NavigationStack {
                    Text("Hello, c!")
                }
                .tabItem {
                    Label("Sessions", systemImage: "chart.bar")
                }
                .tag(2)

            }
            .toolbar(destinations.isEmpty ? .visible : .hidden, for: .tabBar)
        }
        
        .accentColor(secondary)
    }

}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(dependencies: GlobalDependencies())
    }
}
