import SwiftUI
import mpp_sample

@main
struct watchOSApp: App {
    
    init() {
        NapierProxyKt.debugBuild()
        let sample = Sample()
        sample.hello()
        sample.suspendHelloKt()
        sample.handleError()
    }
    
    @SceneBuilder var body: some Scene {
        WindowGroup {
            NavigationView {
                ContentView()
            }
        }

        WKNotificationScene(controller: NotificationController.self, category: "myCategory")
    }
}
