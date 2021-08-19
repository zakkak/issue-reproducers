import com.oracle.svm.core.annotate.*;
import com.oracle.svm.core.jdk.serialize.SerializationRegistry;
import com.oracle.svm.reflect.serialize.SerializationSupport;
import com.oracle.svm.util.ReflectionUtil;
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

    private static void registerSerializationForClass(Class<?> theClass) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> objectClass = Class.forName("java.lang.Object", false, classLoader);

        if (Class.forName("java.io.Externalizable", false, classLoader).isAssignableFrom(theClass)) {
            Class<?> classClass = Class.forName("java.lang.Class", false, classLoader);
            Method getExternalizableConstructor = ReflectionUtil.lookupMethod(ObjectStreamClass.class, "getExternalizableConstructor", classClass);
            Class<?> constructorClass = ((Constructor<?>) getExternalizableConstructor.invoke(null, theClass)).getDeclaringClass();
            com.oracle.svm.reflect.serialize.hosted.SerializationFeature.addReflections(theClass, constructorClass);
            return;
        }

        ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
        Constructor<?> constructor;
        if (!Modifier.isAbstract(theClass.getModifiers())) {
            constructor = reflectionFactory.newConstructorForSerialization(theClass);
        } else {
            constructor = reflectionFactory.newConstructorForSerialization(SerializationSupport.StubForAbstractClass.class);
        }

        Class<?> var18 = constructor.getDeclaringClass();
        Class<?>[] var14 = new Class[0];
        Method var15 = ReflectionUtil.lookupMethod(Constructor.class, "getConstructorAccessor", var14);
        Object[] var16 = new Object[0];
        Object var19 = var15.invoke(constructor, var16);
        Object var17 = ImageSingletons.lookup(SerializationRegistry.class);
        ((SerializationSupport)var17).addConstructorAccessor(theClass, var18, var19);
        com.oracle.svm.reflect.serialize.hosted.SerializationFeature.addReflections(theClass, objectClass);
    }


}