import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
        
        let dependencies = GlobalDependencies()
        
		WindowGroup {
            ContentView(dependencies: dependencies)
		}
	}
}
