package io.github.aakira.napier.mppsample

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.Napier

class CrashlyticsAntilog(
    private val crashlyticsAddLog: (priority: Int, tag: String?, message: String?) -> Unit,
    private val crashlyticsSendLog: (throwable: Throwable) -> Unit
) : Antilog() {

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
//                KtorException -> {
//                    crashlyticsAddLog.invoke(
//                        priority.ordinal,
//                        "HTTP Exception",
//                        it.response?.errorBody.toString(),
//                    )
//                }
                else -> crashlyticsSendLog.invoke(it)
            }
        }
    }
}
