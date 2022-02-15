# Reproducer for native-image issue

```
cd /tmp
git clone --branch resources-with-trailing-slash https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
export JAVA_HOME=/opt/jvms/graalvm-ce-java11-22.0.0.2
$JAVA_HOME/bin/java \
  -jar target/reproducer-1.0-SNAPSHOT.jar
$JAVA_HOME/bin/native-image \
  -jar target/reproducer-1.0-SNAPSHOT.jar
./reproducer-1.0-SNAPSHOT
```
