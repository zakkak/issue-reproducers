# Reproducer for native-image issue

Reproducer of https://github.com/oracle/graal/issues/1725

```
cd /tmp
git clone --branch 2024-09-13-delete-no-class-def-found https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
# run with Unreachable on the classpath
java -cp target/classes Main
# run with Unreachable not on the classpath
java \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# generate native-image with Unreachable not on the classpath
native-image \
  --no-fallback \
  -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# Run binary
./reproducer-1.0-SNAPSHOT
# generate native-image with Unreachable not on the classpath and pass --report-unsupported-elements-at-runtime
native-image \
  --no-fallback \
  --report-unsupported-elements-at-runtime \
  -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# Build fails
```
