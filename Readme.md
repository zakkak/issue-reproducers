# Reproducer for native-image issue

```
cd /tmp
git clone --branch sqlite-compilation-npe https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
export JAVA_HOME=/opt/jvms/graalvm-ce-java11-21.3.0
mvn package
$JAVA_HOME/bin/native-image \
  --no-fallback -H:+ReportExceptionStackTraces \
  -H:-ParseOnce \
  -jar target/reproducer-1.0-SNAPSHOT.jar
./reproducer-1.0-SNAPSHOT
```
