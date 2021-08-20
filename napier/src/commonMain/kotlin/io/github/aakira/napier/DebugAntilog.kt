package io.github.aakira.napier

import android.os.Build
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Pattern

expect class DebugAntilog(defaultTag: String = "app") : Antilog