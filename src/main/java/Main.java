import io.netty.util.internal.PlatformDependent;

public class Main {
    public static void main(String[] args) {
        long maxDirectMemory = PlatformDependent.maxDirectMemory();
        System.out.println("maxDirectMemory= " + maxDirectMemory/1024/1024/1024 + " GB");
    }
}
