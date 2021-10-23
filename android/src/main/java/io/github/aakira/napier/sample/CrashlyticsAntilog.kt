package io.github.aakira.napier.sample

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

class CrashlyticsAntilog(private val context: Context) : Antilog() {

    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
        callerInfo: CallerInfo,
    ) {
        // send only error log
        if (priority < LogLevel.ERROR) return

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
