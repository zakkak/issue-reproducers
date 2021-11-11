# Reproducer for native-image issue

```
cd /tmp
git clone --branch graal-21.3-java17-mbean-register-issue https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
export JAVA_HOME=/opt/jvms/graalvm-ce-java17-21.3.0
mvn package
$JAVA_HOME/bin/native-image \
  --no-fallback -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar
./reproducer-1.0-SNAPSHOT
```
