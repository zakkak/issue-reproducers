import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.jctools.queues.MpscLinkedQueue;
import org.jctools.queues.unpadded.MpscLinkedUnpaddedQueue;

public class Main {

    public static void main(String... args) {
        MpscLinkedQueue<Integer> queue = new MpscLinkedQueue<>();
        MpscLinkedUnpaddedQueue<Integer> queue2 = new MpscLinkedUnpaddedQueue<>();

        System.out.println(sizeOf(queue.getClass()));
        System.out.println(sizeOf(queue2.getClass()));

        System.out.println("Hello World");
    }

    // Thanks to https://stackoverflow.com/a/10587409/1119431
    public static long sizeOf(Class src){
        //
        // Get the instance fields of src class
        // 
        List<Field> instanceFields = new LinkedList<Field>();
        do{
            if(src == Object.class) return 0;
            for (Field f : src.getDeclaredFields()) {
                if((f.getModifiers() & Modifier.STATIC) == 0){
                    instanceFields.add(f);
                }
            }
            src = src.getSuperclass();
        }while(instanceFields.isEmpty());
        //
        // Get the field with the maximum offset
        //  
        long maxOffset = 0;
        for (Field f : instanceFields) {
            long offset = UtilUnsafe.UNSAFE.objectFieldOffset(f);
            if(offset > maxOffset) maxOffset = offset; 
        }
        return  maxOffset; 
    }
}

class UtilUnsafe {
    public static final sun.misc.Unsafe UNSAFE;

    static {
        Object theUnsafe = null;
        Exception exception = null;
        try {
            Class<?> uc = Class.forName("sun.misc.Unsafe");
            Field f = uc.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            theUnsafe = f.get(uc);
        } catch (Exception e) { exception = e; }
        UNSAFE = (sun.misc.Unsafe) theUnsafe;
        if (UNSAFE == null) throw new Error("Could not obtain access to sun.misc.Unsafe", exception);
    }
    private UtilUnsafe() { }
}