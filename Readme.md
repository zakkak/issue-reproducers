# Reproducer for native-image issue with bouncycastle provider

```
cd /tmp
git clone --branch bouncycastle-services-not-included https://github.com/zakkak/issue-reproducers bouncycastle-services-not-included
cd bouncycastle-services-not-included
mvn package
java -agentlib:native-image-agent=config-output-dir=META-INF/native-image -jar target/graal-issue-bouncycastle-1.0-SNAPSHOT.jar
native-image --initialize-at-build-time \
  --no-fallback -H:+ReportExceptionStackTraces \
  -jar target/graal-issue-bouncycastle-1.0-SNAPSHOT.jar
./graal-issue-bouncycastle-1.0-SNAPSHOT
```
