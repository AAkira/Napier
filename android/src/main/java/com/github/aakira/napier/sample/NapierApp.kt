package com.github.aakira.napier.sample

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier
import io.fabric.sdk.android.Fabric

class NapierApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Debug build

            // init napier
            Napier.base(DebugAntilog())
        } else {
            // Others(Release build)

            // init firebase crashlytics
            Fabric.with(this, Crashlytics())
            // init napier
            Napier.base(CrashlyticsAntilog(this))
        }
    }
}
