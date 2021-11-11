# Reproducer for native-image issue

```
cd /tmp
git clone --branch krb5-credentials-failure-java17 https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
export JAVA_HOME=/opt/jvms/graalvm-ce-java17-21.3.0
mvn package
$JAVA_HOME/bin/native-image \
  --no-fallback -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar
./reproducer-1.0-SNAPSHOT
```
