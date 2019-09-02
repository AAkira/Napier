import UIKit
import Crashlytics
import Fabric
import Firebase
import Common

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        #if DEBUG
        // Debug build
        
        // init napier
        NapierProxyKt.debugBuild()
        
        #else
        // Others(Release build)
        
        // init firebase crashlytics
        FirebaseApp.configure()
        Fabric.with([Crashlytics.self])
        
        // init napier
        NapierProxyKt.releaseBuild(antilog: CrashlyticsAntilog(
            crashlyticsAddLog: { priority, tag, message in
                let args = [tag, message].compactMap { $0 }
                CLSLogv("%@", getVaList(args))
                return .init()
        },
            crashlyticsSendLog: { throwable in
                Crashlytics.sharedInstance().recordError(throwable)
                return .init()
        }))
        #endif

        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
    }

    func applicationWillTerminate(_ application: UIApplication) {
    }
}

extension KotlinThrowable: Swift.Error {}
