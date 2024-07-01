![logo][logo]

Napier is a logger library for Kotlin Multiplatform.  
It supports Android, Darwin(iOS, macOS, watchOS, tvOS), JVM, JavaScript.  
Logs written in common module are displayed on logger viewer of each platform.

## Preview

### Android

format: `[Class name]$[Method name]: [Your log]`

uses the `android.util.Log`(Logcat)

![preview-android][preview-android]

### Darwin(iOS, macOS, watchOS, tvOS)[Intel/Apple silicon]

format: `[Date time][Symbol][Log level][Class name].[Method name] - [Your log]`

Added `[async]` label at the end, if it is called from suspend functions.

uses the `print`

![preview-ios][preview-ios]

### JavaScript

uses the `console.log`

![preview-js][preview-js]

### JVM

uses the `java.util.logging.Logger`

![preview-jvm][preview-jvm]

* common sample code

```kotlin

class Sample {

    fun hello(): String {
        Napier.v("Hello napier")
        Napier.d("optional tag", tag = "your tag")

        return "Hello Napier"
    }

    suspend fun suspendHello(): String {
        Napier.i("Hello")

        delay(3000L)

        Napier.w("Napier!")

        return "Suspend Hello Napier"
    }

    fun handleError() {
        try {
            throw Exception("throw error")
        } catch (e: Exception) {
            Napier.e("Napier Error", e)
        }
    }
}
```

## Download

### Repository

You can download this library from MavenCentral or jCenter repository.

* Maven central

You can download this from `1.4.1`.  
Package name is `io.github.aakira`

```groovy
repositories {
    mavenCentral()
}
```

* jCenter

You can download this until `1.4.1`.  
Package name is `com.github.aakira`

```groovy
repositories {
    jCenter()
}
```

### Version

Set the version name in your build.gradle

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.aakira/napier/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.aakira/napier)

`def napierVersion = "[latest version]"`

### Common

Add the dependency to your commonMain dependencies

* groovy

```groovy
sourceSets {
    commonMain {
        dependencies {
            // ...
            implementation "io.github.aakira:napier:$napierVersion"
        }
    }
}
```

* kts

```kotlin
sourceSets {
    val commonMain by getting {
        dependencies {
            implementation("io.github.aakira:napier:$napierVersion")
        }
    }
}
```

## Usage

### How to use

### Common module

```kotlin

// verbose log
Napier.v("Hello napier")
Napier.v { "Hello napier" }

// you can set a tag for each log
Napier.d("optional tag", tag = "your tag")
Napier.d(tag = "your tag") { "optional tag" }

try {
    ...
} catch (e: Exception) {
    // you can set the throwable
    Napier.e("Napier Error", e)
    Napier.e(e) { "Napier Error" }
}

// you can also use top-level function
log { "top-level" }
log(tag = "your tag") { "top-level" }

```

### Initialize

You must initialize the Napier in your module.

#### Android

```kotlin
Napier.base(DebugAntilog())
```

#### iOS

* Write initialize code in your kotlin mpp project.

```kotlin
fun debugBuild() {
    Napier.base(DebugAntilog())
}
```

|argument|type|description| |-|-| |coroutinesSuffix|Boolean|Added `[async]` label at the end, if it is called from
suspend functions|

* Call initialize code from ios project.

```swift
NapierProxyKt.debugBuild()
```

### Clear antilog

```kotlin
Napier.takeLogarithm()
```

## Log level

| Platform | Sample       |
|:---------|:-------------|
| VERBOSE  | Napier.v()   |
| DEBUG    | Napier.d()   |
| INFO     | Napier.i()   |
| WARNING  | Napier.w()   |
| ERROR    | Napier.e()   |
| ASSERT   | Napier.wtf() |

## Run background thread

You can use this library on the background thread on iOS
using [Kotlin.coroutines](https://github.com/Kotlin/kotlinx.coroutines) as native-mt.

* Define scope

```kotlin
internal val mainScope = SharedScope(Dispatchers.Main)

internal val backgroundScope = SharedScope(Dispatchers.Default)

internal class SharedScope(private val context: CoroutineContext) : CoroutineScope {
    private val job = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("[Coroutine Exception] $throwable")
    }

    override val coroutineContext: CoroutineContext
        get() = context + job + exceptionHandler
}
```

* Usage

```kotlin
backgroundScope.launch {
    suspendFunction()
}
```

## Advancement

You can inject custom `Antilog`.  
So, you should change Antilogs in debug build or release build.

### Crashlytics

Crashlytics AntiLog samples

Sample projects use the Firebase Crashlytics.  
You must set authentication files to `android/google-services.json` and `ios/Napier/GoogleService-Info.plist`.

Check the firebase document. [[Android](https://firebase.google.com/docs/android/setup),
[iOS](https://firebase.google.com/docs/ios/setup)]

* [Android](https://github.com/AAkira/Napier/blob/master/android/src/main/java/io/github/aakira/napier/sample/CrashlyticsAntilog.kt)

Write this in your application class.

```kotlin
if (BuildConfig.DEBUG) {
    // Debug build

    // disable firebase crashlytics
    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
    // init napier
    Napier.base(DebugAntilog())
} else {
    // Others(Release build)

    // enable firebase crashlytics
    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    // init napier
    Napier.base(CrashlyticsAntilog(this))
}
```

* [iOS](https://github.com/AAkira/Napier/blob/master/mpp-sample/src/darwinMain/kotlin/io/github/aakira/napier/mppsample/CrashlyticsAntilog.kt)

Write this in your AppDelegate.

```swift
#if DEBUG
// Debug build

// init napier
NapierProxyKt.debugBuild()

#else
// Others(Release build)

// init firebase crashlytics
FirebaseApp.configure()

// init napier
NapierProxyKt.releaseBuild(antilog: CrashlyticsAntilog(
    crashlyticsAddLog: { priority, tag, message in
        Crashlytics.crashlytics().log("\(String(describing: tag)): \(String(describing: message))")
},
    crashlyticsSendLog: { throwable in
        Crashlytics.crashlytics().record(error: throwable)
}))
#endif
```

## License

```
Copyright (C) 2019 A.Akira

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Credit

This library is inspired by [Timber](https://github.com/JakeWharton/timber).  
I recommend using it if it supports kotlin multiplatform project.😜

Thanks for advice.  
[@horita-yuya](https://github.com/horita-yuya),
[@terachanple](https://github.com/terachanple)

[logo]: arts/logo.jpg

[preview-android]: arts/screenshot-android.jpg

[preview-ios]: arts/screenshot-ios.jpg

[preview-js]: arts/screenshot-js.jpg

[preview-jvm]: arts/screenshot-jvm.jpg
