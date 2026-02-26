import SwiftUI
import ComposeApp // This is your shared Kotlin code

@main
struct iOSApp: App {
    // This is the iOS "Singleton"
    let container: ScanContainer

    init() {
        // 1. Call the Kotlin function we just wrote
        let builder = DatabaseKt.getIosDatabaseBuilder()

        // 2. Initialize the shared ScanContainer
        self.container = ScanContainer(databaseBuilder: builder)
        ScanViewModelProvider.container = self.container
    }

    var body: some Scene {
        WindowGroup {
            ContentView(container: container)
        }
    }
}