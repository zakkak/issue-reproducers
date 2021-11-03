# Reproducer for native-image issue

```
cd /tmp
git clone --branch record-annotations-21.3 https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
export JAVA_HOME=/opt/jvms/graalvm-ce-java17-21.3.0
$JAVA_HOME/bin/native-image \
  --no-fallback -H:+ReportExceptionStackTraces \
  -H:ReflectionConfigurationFiles=./META-INF/native-image/reflect-config.json \
  -jar target/reproducer-1.0-SNAPSHOT.jar
./reproducer-1.0-SNAPSHOT
```
