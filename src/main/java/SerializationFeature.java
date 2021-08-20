import com.oracle.svm.core.annotate.AutomaticFeature;
import com.oracle.svm.reflect.serialize.SerializationSupport;
import com.oracle.svm.util.ReflectionUtil;
import org.graalvm.home.Version;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.hosted.Feature;
import sun.reflect.ReflectionFactory;

import java.io.ObjectStreamClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@AutomaticFeature
public class SerializationFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        try {
            registerSerializationForClass(AbstractPerson.class);
            registerSerializationForClass(ExternalizablePerson.class);
            registerSerializationForClass(Person.class);
            registerSerializationForClass(SomeSerializationObject.class);
            registerSerializationForClass(String.class);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void registerSerializationForClass(Class<?> var0) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Version var1 = Version.getCurrent();
        int[] var2 = new int[]{21};
        if (var1.compareTo(var2) >= 0) {
            ClassLoader var5 = Thread.currentThread().getContextClassLoader();
            Version var4 = Version.getCurrent();
            int[] var3 = new int[]{21, 3};
            if (var4.compareTo(var3) >= 0) {
                Class var6 = Class.forName("org.graalvm.nativeimage.hosted.RuntimeSerialization", false, var5);
                Class[] var7 = new Class[]{Class[].class};
                Method var10 = ReflectionUtil.lookupMethod(var6, "register", var7);
                Object[] var8 = new Object[1];
                Class[] var9 = new Class[]{var0};
                var8[0] = var9;
                var10.invoke((Object)null, var8);
                return;
            }

            Class var30 = Class.forName("java.lang.Object", false, var5);
            Class var11 = Class.forName("com.oracle.svm.core.jdk.serialize.SerializationRegistry", false, var5);
            Class var13 = Class.forName("com.oracle.svm.reflect.serialize.hosted.SerializationFeature", false, var5);
            Object var26 = ImageSingletons.lookup(var11);
            Class[] var12 = new Class[]{Class.class, Class.class};
            Method var20 = ReflectionUtil.lookupMethod(var13, "addReflections", var12);
            ReflectionFactory var21 = ReflectionFactory.getReflectionFactory();
            if (Class.forName("java.io.Externalizable", false, var5).isAssignableFrom(var0)) {
                Class[] var14 = new Class[1];
                Class var15 = Class.forName("java.lang.Class", false, var5);
                var14[0] = var15;
                Method var16 = ReflectionUtil.lookupMethod(ObjectStreamClass.class, "getExternalizableConstructor", var14);
                Object[] var17 = new Object[]{var0};
                Class var19 = ((Constructor)var16.invoke((Object)null, var17)).getDeclaringClass();
                Class[] var18 = new Class[]{var0, var19};
                var20.invoke((Object)null, (Object[])var18);
                return;
            }

            Constructor var22;
            if (!Modifier.isAbstract(var0.getModifiers())) {
                var22 = var21.newConstructorForSerialization(var0);
            } else {
                var22 = var21.newConstructorForSerialization(SerializationSupport.StubForAbstractClass.class);
            }

            Class var27 = var22.getDeclaringClass();
            Class[] var23 = new Class[0];
            Method var24 = ReflectionUtil.lookupMethod(Constructor.class, "getConstructorAccessor", var23);
            Object[] var25 = new Object[0];
            Object var28 = var24.invoke(var22, var25);
            ((SerializationSupport)var26).addConstructorAccessor(var0, var27, var28);
            Class[] var29 = new Class[]{var0, var30};
            var20.invoke((Object)null, (Object[])var29);
        }
    }


}