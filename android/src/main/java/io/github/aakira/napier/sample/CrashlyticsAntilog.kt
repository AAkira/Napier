package io.github.aakira.napier.sample

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.Napier

class CrashlyticsAntilog(private val context: Context) : Antilog() {

    override fun performLog(
        priority: Napier.Level,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        // send only error log
        if (priority < Napier.Level.ERROR) return

        throwable?.let {
            when (it) {
                // e.g. http exception, add a customized your exception message
//                is KtorException -> {
//                    FirebaseCrashlytics.getInstance()
//                        .log("${priority.ordinal}, HTTP Exception, ${it.response?.errorBody}")
//                }
                else -> FirebaseCrashlytics.getInstance().recordException(it)
            }
        }
    }
}
