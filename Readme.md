# Reproducer for native-image issue

```
cd /tmp
git clone --branch 2023-03-21-graal-4661-parsing-error https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
export JAVA_HOME=/path/to/graalvm
# Attempt to generate native-image
$JAVA_HOME/bin/native-image \
  --no-fallback \
  --link-at-build-time \
  --initialize-at-build-time=. \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# Run the generated image
./reproducer-1.0-SNAPSHOT
```