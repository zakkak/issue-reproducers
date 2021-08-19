# Reproducer for native-image issue

```
cd /tmp
git clone --branch serialization-registration https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
export JAVA_HOME=/opt/jvms/graalvm-ce-java11-21.2.0
$JAVA_HOME/bin/java -agentlib:native-image-agent=config-output-dir=META-INF/native-image -jar target/reproducer-1.0-SNAPSHOT.jar
$JAVA_HOME/bin/native-image --initialize-at-build-time \
  --no-fallback -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar
./reproducer-1.0-SNAPSHOT
```
