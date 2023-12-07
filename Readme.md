# Reproducer for native-image issue with netty plarform dependent fields 

See https://github.com/Karm/mandrel-integration-tests/issues/236

```shell
cd /tmp
git clone --branch 2023-12-07-mandrel-it-tests-236 https://github.com/zakkak/issue-reproducers reproducer
cd reproducer
mvn package
java -jar ./target/reproducer-1.0-SNAPSHOT.jar
```