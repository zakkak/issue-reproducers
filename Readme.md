# Reproducer for native-image issue with fields in classes with substituted methods 

See https://github.com/quarkusio/quarkus/issues/37862

```shell
cd /tmp
git clone --branch 2024-01-10-subst-analysis-issue https://github.com/zakkak/issue-reproducers reproducer
cd reproducer
mvn package
java -jar ./target/reproducer-1.0-SNAPSHOT.jar
native-image -cp target/classes/ Main --no-fallback --initialize-at-build-time=.
```