# Reproducer for non native-image issue

Reproducer of https://github.com/quarkusio/quarkus/issues/44478

```
cd /tmp
git clone --branch2024-11-14-directory-resource https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
# run in JVM
$JAVA_HOME/java \
  -jar target/reproducer-1.0-SNAPSHOT.jar
```