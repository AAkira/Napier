package io.github.aakira.napier.sample

import android.content.Context
import com.crashlytics.android.Crashlytics
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
        Crashlytics.getInstance().core.log(priority.ordinal, tag, message)

        throwable?.let {
            when {
                // e.g. http exception, add a customized your exception message
//                it is KtorException -> {
//                    Crashlytics.getInstance().core.log(priority.ordinal, "HTTP Exception", it.response?.errorBody.toString())
//                }
            }
            Crashlytics.getInstance().core.logException(it)
        }
    }
}
