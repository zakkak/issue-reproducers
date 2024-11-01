# Reproducer for native-image issue

Reproducer of https://github.com/oracle/graal/issues/10008

```
cd /tmp
git clone --branch 2024-11-01-jep472-manifest-ignored https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
# run in JVM
$JAVA_HOME/java \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# generate native-image
$JAVA_HOME/native-image \
  --no-fallback \
  --initialize-at-build-time=.  \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# Build prints warning about native access
```

The warning is:
  
```
WARNING: A restricted method in java.lang.System has been called
WARNING: java.lang.System::loadLibrary has been called by com.aayushatharva.brotli4j.Brotli4jLoader in an unnamed module (file:/home/zakkak/code/issue-reproducers/target/reproducer-1.0-SNAPSHOT.jar)
WARNING: Use --enable-native-access=ALL-UNNAMED to avoid a warning for callers in this module
WARNING: Restricted methods will be blocked in a future release unless native access is enabled
```