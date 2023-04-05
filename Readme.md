# Reproducer for native-image issue

```
cd /tmp
git clone --branch 2023-04-05-delayed-error-reporting-causing-issues https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
export JAVA_HOME=/opt/jvms/graalvm-ce-java17-22.3.1
$JAVA_HOME/bin/native-image --initialize-at-build-time=. \
  --no-fallback -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar
```
