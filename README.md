![logo][logo]

Napier is a logger library for Kotlin Multiplatform.  
It supports Android, Darwin (iOS, macOS, watchOS, tvOS), JVM, and JavaScript.  
Logs written in the common module are displayed in the log viewer of each platform.

## Preview

### Android

format: `[Class name]$[Method name]: [Your log]`

It uses `android.util.Log` (Logcat).

![preview-android][preview-android]

### Darwin(iOS, macOS, watchOS, tvOS)[Intel/Apple silicon]

format: `[Date time][Symbol][Log level][Class name].[Method name] - [Your log]`

The `[async]` label is added at the end if it is called from a suspend function.

It uses `print`.

![preview-ios][preview-ios]

### JavaScript

It uses `console.log`.

![preview-js][preview-js]

### JVM

It uses `java.util.logging.Logger`.

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

You can download this library from the Maven Central or jCenter repository.

* Maven Central

Versions `1.4.1` and later are available here.  
The package name is `io.github.aakira`.

```groovy
repositories {
    mavenCentral()
}
```

* jCenter

Versions up to `1.4.1` are available here.  
The package name is `com.github.aakira`.

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

// you can also use the top-level functions
log { "top-level" }
log(tag = "your tag") { "top-level" }

```

### Initialize

You must initialize Napier in your module.

#### Android

```kotlin
Napier.base(DebugAntilog())
```

#### iOS

* Write the initialization code in your Kotlin Multiplatform project.

```kotlin
fun debugBuild() {
    Napier.base(DebugAntilog())
}
```

| argument         | type    | description                                                                    |
|:-----------------|:--------|:-------------------------------------------------------------------------------|
| coroutinesSuffix | Boolean | The `[async]` label is added at the end if it is called from a suspend function |

* Call the initialization code from your iOS project.

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

## Run on a background thread

You can use this library on a background thread on iOS
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

## Testing

Run the unit tests of the `napier` module on all available targets.

```shell
./gradlew :napier:allTests
```

You can also run the tests for a specific target.

```shell
# JVM
./gradlew :napier:jvmTest

# Android (host unit tests)
./gradlew :napier:testAndroidHostTest

# JavaScript (Node.js / browser)
./gradlew :napier:jsNodeTest :napier:jsBrowserTest

# wasmJs (Node.js / browser)
./gradlew :napier:wasmJsNodeTest :napier:wasmJsBrowserTest

# iOS simulator
./gradlew :napier:iosSimulatorArm64Test

# macOS
./gradlew :napier:macosArm64Test
```

Targets that cannot run on the current host are skipped automatically.
For example, `macosX64Test` is skipped on Apple Silicon and the Apple targets are skipped on Linux.

## Samples

This repository contains sample projects for each platform.
All samples call the common module code in [mpp-sample](https://github.com/AAkira/Napier/tree/master/mpp-sample).

### Android

Open this repository in Android Studio and run the `android` module,
or build the apk on the command line.

```shell
./gradlew :android:assembleDebug
```

The sample uses Firebase Crashlytics, so `android/google-services.json` is required.
See the [Crashlytics](#crashlytics) section below.

### JVM

Build the executable jar and run it.

```shell
./gradlew :jvm:build
java -jar jvm/build/libs/jvm.jar

# or
sh jvm/run.sh
```

### JavaScript (Browser)

Start the webpack dev server.
It opens http://localhost:8080/ in your browser and the logs are displayed on the developer console.

```shell
./gradlew :js:jsBrowserDevelopmentRun

# or
sh js/run.sh
```

### iOS

The sample app depends on the `mpp-sample` framework via CocoaPods.

```shell
# generate the podspec and the dummy framework (only needed for a fresh checkout)
./gradlew :mpp-sample:generateDummyFramework

cd ios
pod install
```

Open `ios/Napier.xcworkspace` in Xcode and run the `Napier` scheme,
or build it on the command line.

```shell
xcodebuild -workspace Napier.xcworkspace -scheme Napier \
  -destination 'platform=iOS Simulator,name=iPhone 16' build
```

### macOS

Same as iOS.

```shell
./gradlew :mpp-sample:generateDummyFramework

cd macOS
pod install
```

Open `macOS/macOS.xcworkspace` in Xcode and run the `macOS (macOS)` scheme,
or build it on the command line.

```shell
xcodebuild -workspace macOS.xcworkspace -scheme "macOS (macOS)" build
```

## Advancement

You can inject a custom `Antilog`,  
so you can switch Antilogs between debug and release builds.

### Crashlytics

Crashlytics Antilog samples

The sample projects use Firebase Crashlytics.  
You must put the authentication files at `android/google-services.json` and `ios/Napier/GoogleService-Info.plist`.

Check the Firebase documentation. [[Android](https://firebase.google.com/docs/android/setup),
[iOS](https://firebase.google.com/docs/ios/setup)]

* [Android](https://github.com/AAkira/Napier/blob/master/android/src/main/java/com/github/aakira/napier/sample/CrashlyticsAntilog.kt)

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

* [iOS](https://github.com/AAkira/Napier/blob/master/mpp-sample/src/iosMain/kotlin/com/github/aakira/napier/CrashlyticsAntilog.kt)

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
I would recommend using it if it supported Kotlin Multiplatform projects.😜

Thanks for the advice.  
[@horita-yuya](https://github.com/horita-yuya),
[@terachanple](https://github.com/terachanple)

[logo]: arts/logo.jpg

[preview-android]: arts/screenshot-android.jpg

[preview-ios]: arts/screenshot-ios.jpg

[preview-js]: arts/screenshot-js.jpg

[preview-jvm]: arts/screenshot-jvm.jpg
