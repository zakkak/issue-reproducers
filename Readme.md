# Reproducer for native-image issue

```
cd /tmp
git clone --branch serialization-registration-21.3 https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
export JAVA_HOME=/opt/jvms/graalvm-ce-java11-21.3.0-dev
$JAVA_HOME/bin/native-image --initialize-at-build-time --diagnostics-mode \
  --no-fallback -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar
./reproducer-1.0-SNAPSHOT
```
