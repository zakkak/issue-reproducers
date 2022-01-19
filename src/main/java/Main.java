import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.Isolates;
import org.graalvm.nativeimage.c.function.CEntryPoint;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello");
		String property = System.getProperty("property.to.override");
		System.out.println("property = " + property);

		var params = Isolates.CreateIsolateParameters.getDefault();
		var isolateThread = Isolates.createIsolate(params);

		try {
			System.out.println("isolated-property " + processInIsolate(isolateThread));
		}
		finally {
			Isolates.tearDownIsolate(isolateThread);
		}
	}

	@CEntryPoint
	private static int processInIsolate(@CEntryPoint.IsolateThreadContext IsolateThread isolateThread) {
		String property = System.getProperty("property.to.override");
		System.out.println("isolated-property = " + property);
		return Integer.parseInt(property);
	}
}