import com.oracle.svm.core.annotate.Delete;
import com.oracle.svm.core.annotate.TargetClass;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

@Delete
@TargetClass(className = "Foo")
final class Target_Foo {

}