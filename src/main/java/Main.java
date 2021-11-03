import java.lang.annotation.Annotation;
import java.lang.reflect.RecordComponent;

public class Main {

	public static void main(String[] args) {
		RecordComponent[] recordComponents = F.class.getRecordComponents();
		for (RecordComponent component: recordComponents) {
			System.out.println(component);
			Annotation[] annotations = component.getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.println(annotation);
			}
		}

		F x = new F("x");
		System.out.println(x);
	}

	static record F(String name) {
	}
}
