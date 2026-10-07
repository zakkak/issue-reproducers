# Reproducer for Native Image Issue with missing permitted classes of sealed class

`ServiceMonitor` is a concrete sealed class permitting `JfrServiceMonitor`.
The application only creates `ServiceMonitor` and calls its virtual `message()` method.
Maven compiles both classes, but the packaging filter excludes `JfrServiceMonitor.class` from the JAR, simulating removal of an unreachable class.
The sealed class still names the missing subclass in its `PermittedSubclasses` attribute.

```shell
export JAVA_HOME=/path/to/graalvm
export PATH="$JAVA_HOME/bin:$PATH"
mvn clean package

# Confirm that the packaged JAR does not contain JfrServiceMonitor.class.
jar tf target/reproducer-1.0-SNAPSHOT.jar

# The incomplete JAR runs on the JVM and prints "monitor works".
java -jar target/reproducer-1.0-SNAPSHOT.jar

# Native Image fails during analysis while resolving the missing subclass.
native-image -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar

# Control: include the subclass by using the complete compiler output.
native-image -cp target/classes Main complete
./complete
# Prints "monitor works".
```

On the affected build, the incomplete JAR fails with:

```text
Caused by: java.lang.ClassNotFoundException: JfrServiceMonitor
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:580)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:490)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageClassLoader.loadClass(NativeImageClassLoader.java:629)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:490)
	... 72 more
```

Verified with GraalVM Community Edition built from `oracle/graal`, branch `main`, commit `2b20107559e4af0bed6233dd32a4d659ec322d2c`.
The distribution reports `GraalVM CE 25.5.5-dev+1.1`, using LabsJDK `25.0.4.1.1+1-jvmci-25.4-b23` on Linux AMD64.
The complete classpath builds and runs successfully with the same distribution.
