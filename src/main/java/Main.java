import java.lang.reflect.Field;

import io.netty.util.internal.PlatformDependent;

public class Main {

    public static void main(String[] args) {
        try {
            
            Field maxDirectMemoryField = PlatformDependent.class.getDeclaredField("MAX_DIRECT_MEMORY");
            maxDirectMemoryField.setAccessible(true);
            long maxDirectMemory = (long) maxDirectMemoryField.get(null);
            System.out.println("maxDirectMemory= " + maxDirectMemory/1024/1024/1024 + " GB");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
