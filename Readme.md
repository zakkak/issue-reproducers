# Reproducer for native-image issue with netty plarform dependent fields

```shell
cd /tmp
git clone --branch 2023-11-23-quarkus-17839-reproducer-netty-platform-dependent https://github.com/zakkak/issue-reproducers reproducer
cd reproducer
mvn package
java -jar ./target/reproducer-1.0-SNAPSHOT.jar
```

Depending on the memory of the system we are running on this prints something like:

```shell
maxDirectMemory= 15 GB
```

Building a native image from it:

```shell
native-image --initialize-at-build-time=. \
  --no-fallback \
  -jar target/reproducer-1.0-SNAPSHOT.jar
```

during the build `native-image` informs us about the resources being used, e.g.:


```shell
Build resources:
 - 7.70GB of memory (12.4% of 61.93GB system memory, determined at start)
 - 32 thread(s) (100.0% of 32 available processor(s), determined at start)
```

and the resulting binary prints:

```shell
maxDirectMemory= 7 GB
```

If we set `-Xmx` we can control the amount of memory used at build time which also affects the `maxDirectMemory` reported by netty:

```shell
native-image --initialize-at-build-time=. \
  -J-Xmx32g \
  --no-fallback \
  -jar target/reproducer-1.0-SNAPSHOT.jar
```

during build this will print:

```shell
Build resources:
 - 28.44GB of memory (45.9% of 61.93GB system memory, set via '-Xmx32g')
 - 32 thread(s) (100.0% of 32 available processor(s), determined at start)
```

and running the binary prints:

```shell
maxDirectMemory= 28 GB
```

Even if we move the binary to a different machine, e.g. one printing:

```shell
maxDirectMemory= 3 GB
```

when run in HotSpot, the native executable still prints:

```shell
maxDirectMemory= 28 GB
```

which is not desirable.