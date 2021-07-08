# Reproducer for native-image issue with batis

```
cd /tmp
git clone --branch mybatis-reflection-reproducer https://github.com/zakkak/issue-reproducers
cd mybatis-reflection-reproducer
mvn package
java -agentlib:native-image-agent=config-output-dir=META-INF/native-image -jar target/getting-started-1.0-SNAPSHOT.jar
native-image --initialize-at-build-time \
  --no-fallback -H:+ReportExceptionStackTraces \
  -jar target/getting-started-1.0-SNAPSHOT.jar
```

# To (re)generate the reflection config

java -agentlib:native-image-agent=config-output-dir=META-INF/native-image -jar /tmp/quarkus-mybatis-reflection-no-quarkus/target/getting-started-1.0-SNAPSHOT.jar
