# Jvm sample

## Run Script

```sh
./gradlew :jvm:build
java -jar jvm/build/libs/jvm.jar
```

## Known issues

* java.lang.NoClassDefFoundError

Downgrade Gradle to 4.x if it doesn't work,
and change the Gradle dependencies.

```groovy
compile project(":napier") 
compile project(":mpp-sample")  
```
