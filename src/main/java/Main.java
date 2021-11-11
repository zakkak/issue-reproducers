import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) {
		try {
			Class clazz = Object.class;
			MethodHandles.Lookup lookup = MethodHandles.lookup();
			MethodHandle constructorHandle = lookup.findConstructor(clazz, MethodType.methodType(void.class));
	  
			CallSite site = LambdaMetafactory.metafactory(lookup, "apply", MethodType.methodType(Function.class),
				constructorHandle.type().generic(), constructorHandle, constructorHandle.type());
  
			System.out.println(site);
    	} catch (Throwable t) {
			System.out.println("FAILED");
		}
    }
}
