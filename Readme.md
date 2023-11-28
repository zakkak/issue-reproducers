# Reproducer for native-image issue with netty plarform dependent fields

NOTE: This is using quarkus version 999-SNAPSHOT, i.e. depends on a local build of the Quarkus `main` branch.

```shell
cd /tmp
git clone --branch 2023-11-28-quarkus-17839-reproducer-netty-platform-dependent-in-quarkus https://github.com/zakkak/issue-reproducers reproducer
cd reproducer
mvn clean package -Dnative
java -jar ./target/code-with-quarkus-1.0.0-SNAPSHOT-native-image-source-jar/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar
```

Depending on the memory of the system we are running on this prints something like:

```shell
maxDirectMemoryStatic= 15 GB
maxDirectMemory= 15 GB
```

Running the corresponding native image:

```shell
./target/code-with-quarkus-1.0.0-SNAPSHOT-runner
```

we get:

```shell
maxDirectMemoryStatic= 8 GB
maxDirectMemory= 8 GB
```

If we increase the max heap size at build time it also affects the `maxDirectMemory` reported by netty:

```shell
mvn clean package -Dnative -Dquarkus.native.native-image-xmx=32g
```

during build time this will print:

```shell
Build resources:
 - 28.44GB of memory (45.9% of 61.93GB system memory, set via '-Xmx32g')
 - 32 thread(s) (100.0% of 32 available processor(s), determined at start)
```

and running the binary prints:

```shell
maxDirectMemoryStatic= 28 GB
maxDirectMemory= 28 GB
```

Even if we move the binary to a different machine, e.g. one printing:

```shell
maxDirectMemoryStatic= 3 GB
maxDirectMemory= 3 GB
```

when run in HotSpot, the native executable still prints:

```shell
maxDirectMemoryStatic= 28 GB
maxDirectMemory= 28 GB
```

which is not desirable.
