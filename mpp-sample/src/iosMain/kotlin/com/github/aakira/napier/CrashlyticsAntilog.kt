package com.github.aakira.napier

class CrashlyticsAntilog(
    private val crashlyticsAddLog: (priority: Int, tag: String?, message: String?) -> Unit,
    private val crashlyticsSendLog: (throwable: Throwable) -> Unit
) : Antilog() {

    override fun performLog(priority: Napier.Level, tag: String?, throwable: Throwable?, message: String?) {
        // send only error log
        if (priority < Napier.Level.ERROR) return

        crashlyticsAddLog.invoke(priority.ordinal, tag, message)

        throwable?.let {
            when {
                // e.g. http exception, add a customized your exception message
//                it is KtorException -> {
//                    crashlyticsAddLog.invoke(priority.ordinal, "HTTP Exception", it.response?.errorBody.toString())
//                }
            }
            crashlyticsSendLog.invoke(it)
        }
    }
}