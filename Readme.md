# Reproducer for native-image issue

```
cd /tmp
git clone --branch unreachable-unresolved-code https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
export JAVA_HOME=/opt/jvms/graalvm-ce-java11-22.1.0
# run with Unreachable on the classpath
$JAVA_HOME/bin/java -cp target/classes Main
# run with Unreachable not on the classpath
$JAVA_HOME/bin/java \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# generate native-image with Unreachable not on the classpath
$JAVA_HOME/bin/native-image \
  --link-at-build-time \
  --initialize-at-build-time=. \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# Run the generated image
./reproducer-1.0-SNAPSHOT
```

To generate the IGV graph for main use
```
native-image --initialize-at-build-time=. --no-fallback -H:Dump=:1 -H:MethodFilter=Main.main -jar target/reproducer-1.0-SNAPSHOT.jar
```

and render it with
```
seafoam graal_dumps/2022.06.17.16.02.27.868/SubstrateHostedCompilation-3596\[Main.main\(String\[\]\)void\].bgv:0 render
```