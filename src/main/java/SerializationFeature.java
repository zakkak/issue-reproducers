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
            ClassLoader var3 = Thread.currentThread().getContextClassLoader();
            Class var25 = Class.forName("java.lang.Object", false, var3);
            Version var5 = Version.getCurrent();
            int[] var4 = new int[]{21, 3};
            Class var6;
            Class var8;
            if (var5.compareTo(var4) < 0) {
                var6 = Class.forName("com.oracle.svm.core.jdk.serialize.SerializationRegistry", false, var3);
                var8 = Class.forName("com.oracle.svm.reflect.serialize.hosted.SerializationFeature", false, var3);
            } else {
                var6 = Class.forName("com.oracle.svm.reflect.serialize.SerializationRegistry", false, var3);
                var8 = Class.forName("com.oracle.svm.reflect.serialize.hosted.SerializationBuilder", false, var3);
            }

            Object var21 = ImageSingletons.lookup(var6);
            Class[] var7 = new Class[]{Class.class, Class.class};
            Method var15 = ReflectionUtil.lookupMethod(var8, "addReflections", var7);
            ReflectionFactory var16 = ReflectionFactory.getReflectionFactory();
            if (Class.forName("java.io.Externalizable", false, var3).isAssignableFrom(var0)) {
                Class[] var9 = new Class[1];
                Class var10 = Class.forName("java.lang.Class", false, var3);
                var9[0] = var10;
                Method var11 = ReflectionUtil.lookupMethod(ObjectStreamClass.class, "getExternalizableConstructor", var9);
                Object[] var12 = new Object[]{var0};
                Class var14 = ((Constructor)var11.invoke((Object)null, var12)).getDeclaringClass();
                Class[] var13 = new Class[]{var0, var14};
                var15.invoke((Object)null, (Object[])var13);
                return;
            }

            Constructor var17;
            if (!Modifier.isAbstract(var0.getModifiers())) {
                var17 = var16.newConstructorForSerialization(var0);
            } else {
                var17 = var16.newConstructorForSerialization(SerializationSupport.StubForAbstractClass.class);
            }

            Class var22 = var17.getDeclaringClass();
            Class[] var18 = new Class[0];
            Method var19 = ReflectionUtil.lookupMethod(Constructor.class, "getConstructorAccessor", var18);
            Object[] var20 = new Object[0];
            Object var23 = var19.invoke(var17, var20);
            ((SerializationSupport)var21).addConstructorAccessor(var0, var22, var23);
            Class[] var24 = new Class[]{var0, var25};
            var15.invoke((Object)null, (Object[])var24);
        }
    }


}