import java.lang.reflect.Field;

import io.netty.util.internal.PlatformDependent;

public class Main {
    public static void main(String[] args) {
        long maxDirectMemory = PlatformDependent.maxDirectMemory();
        System.out.println("maxDirectMemory= " + maxDirectMemory/1024/1024/1024 + " GB");

        try {
            Class platformDependentClass = Class.forName("io.netty.util.internal.PlatformDependent0");
            Field[] declaredFields = platformDependentClass.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                System.out.println("PlatformDependent0." + field.getName() + " = " + field.get(null));
            }
            platformDependentClass = Class.forName("io.netty.util.internal.PlatformDependent");
            declaredFields = platformDependentClass.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                System.out.println("PlatformDependent." + field.getName() + " = " + field.get(null));
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
