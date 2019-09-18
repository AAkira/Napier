![logo][logo]

Napier is a logger library for Kotlin Multiplatform.  
It supports for the android, ios, jvm, js.  
Logs written in common module are displayed on logger viewer of each platform.

* Android

format: `[Class name]$[Method name]: [Your log]`

![preview-android][preview-android]

* ios

format: `[Date time][Symbol][Log level][Class name].[Method name] - [Your log]`

![preview-ios][preview-ios]

* js

![preview-js][preview-js]

* jvm

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
        } catch(e: Exception) {
            Napier.e("Napier Error", e)
        }
    }
}
```

## Download

### Repository

You can download this library from jCenter or maven repository.

* jCenter

```gradle
repositories {
    jCenter()
}
```

* maven

```gradle
repositories {
    maven { url "https://dl.bintray.com/aakira/maven" }
}
```

### Version

Set the version name in your build.gradle

[![Download Napier](https://api.bintray.com/packages/aakira/maven/napier/images/download.svg)](https://bintray.com/aakira/maven/napier/_latestVersion)

`def napierVersion = "[latest version]"`

### Common

```gradle
dependencies {
    implementation "com.github.aakira:napier:$napierVersion"
}
```

### ios

uses the `print`

```gradle
dependencies {
    // include x64, arm64, arm32
    implementation "com.github.aakira:napier-ios:$napierVersion"
    
    // x64
    implementation 'com.github.aakira:napier-iosX64:$napierVersion'
    // arm
    implementation 'com.github.aakira:napier-iosArm32:$napierVersion'
    implementation 'com.github.aakira:napier-iosArm64:$napierVersion'
}
```

### android

uses the `android.util.Log`(Logcat)

```gradle
dependencies {
    implementation "com.github.aakira:napier-android:$napierVersion"
}
```

### js

uses the `console.log`

```gradle
dependencies {
    implementation "com.github.aakira:napier-js:$napierVersion"
}
```

### jvm

uses the `java.util.logging.Logger`

```jvm
dependencies {
    implementation "com.github.aakira:napier-jvm:$napierVersion"
}
```

## Usage

### How to use

### Common module

```kotlin

// verbose log
Napier.v("Hello napier")

// you can set a tag for each log
Napier.d("optional tag", tag = "your tag")

try {
    ...
} catch(e: Exception) {
    // you can set the throwable
    Napier.e("Napier Error", e)
}

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

* Call initialize code from ios project.

```swift
NapierProxyKt.debugBuild()
```

### Clear antilog

```kotlin
Napier.takeLogarithm()
```

## Log level

| Platform      | Sample      |
|:--------------|:------------|
| VERBOSE       | Napier.v()  |
| DEBUG         | Napier.d()  |
| INFO          | Napier.i()  |
| WARNING       | Napier.w()  |
| ERROR         | Napier.e()  |
| ASSERT        | Napier.wtf()|

## Advancement

You can inject custom `Antilog`.  
So, you should change Antilogs in debug build or release build.

### Crashlytics

Crashlytics AntiLog samples

Sample projects use the Firebase Crashlytics.  
You must set authentication files to `android/google-services.json` and `ios/Napier/GoogleService-Info.plist`.

Check the firebase document. [[Android](https://firebase.google.com/docs/android/setup),
[iOS](https://firebase.google.com/docs/ios/setup)]

* [Android](https://github.com/AAkira/Napier/blob/master/android/src/main/java/com/github/aakira/napier/sample/CrashlyticsAntilog.kt)

Write this in your application class.

```kotlin
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
Fabric.with([Crashlytics.self])

// init napier
NapierProxyKt.releaseBuild(antilog: CrashlyticsAntilog(
    crashlyticsAddLog: { priority, tag, message in
        let args = [tag, message].compactMap { $0 }
        CLSLogv("%@", getVaList(args))
        return .init()
},
    crashlyticsSendLog: { throwable in
        Crashlytics.sharedInstance().recordError(throwable)
        return .init()
}))
#endif
```

## License

```
Copyright (C) 2019 A.Akira

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Credit

This library is inspired by [Timber](https://github.com/JakeWharton/timber).  
I recommend use it if it supports kotlin multiplatform project.😜

Thanks for advice.  
[@horita-yuya](https://github.com/horita-yuya), 
[@terachanple](https://github.com/terachanple)

[logo]: /arts/logo.jpg
[preview-android]: /arts/screenshot-android.jpg
[preview-ios]: /arts/screenshot-ios.jpg
[preview-js]: /arts/screenshot-js.jpg
[preview-jvm]: /arts/screenshot-jvm.jpg
