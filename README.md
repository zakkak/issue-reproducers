# Test to confirm that padding bytes used in JCTools are removed when using native-image

This is a simple test that uses both a padded and an unpadded queue from JCTools
and uses `sun.misc.Unsafe` and reflection to get the biggest offset of a field
in both cases.

It is used to confirm that padding bytes are being removed by native-image as
dead code.

## How to

```
mvn clean package
java -jar target/my-project-1.0.0-jar-with-dependencies.jar
native-image -g -jar ./target/my-project-1.0.0-jar-with-dependencies.jar
my-project-1.0.0-jar-with-dependencies
gdb my-project-1.0.0-jar-with-dependencies --batch -ex "ptype 'org.jctools.queues.BaseLinkedQueuePad2'"
```

The JVM mode output looks like this:

```
395
16
Hello World
```

While the native mode output looks like this:

```
32
24
Hello World
```

Which shows that the padding fields are indeed eliminated in native compilation.

Furthermore the gdb output (another indication about which fields are eliminated
and which are not) will be:

```
type = class org.jctools.queues.BaseLinkedQueuePad2 : public org.jctools.queues.BaseLinkedQueueConsumerNodeRef {
  public:
    byte b001;
    byte b002;
    byte b003;
    byte b004;
    byte b005;
}
```

Note that only `b001` to `b005` are present from the total of 128 fields used
for padding. That's because we explicitly register these fields for reflection
(to demonstrate that reachable fields are present while unreachable ones are
not). Altering `src/main/resources/META-INF/native-image/reflect-config.json`
you can increase or decrease the number of reachable fields and see how this
affects the above results.