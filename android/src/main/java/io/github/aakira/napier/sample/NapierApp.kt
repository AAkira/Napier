package io.github.aakira.napier.sample

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class NapierApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Debug build
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)

            // init napier
            Napier.base(DebugAntilog())
        } else {
            // Others(Release build)
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

            // init napier
            Napier.base(CrashlyticsAntilog(this))
        }
    }
}
