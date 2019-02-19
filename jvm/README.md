# Jvm sample

## Run Script

```sh
./gradlew :jvm:build
java -jar jvm/build/libs/jvm.jar
```

## Known issues

* java.lang.NoClassDefFoundError

Downgrade the gradle to 4.x if it doesn't work.
And change the dependencies of gradle.

```groovy
compile project(":napier") 
compile project(":mpp-sample")  
```
