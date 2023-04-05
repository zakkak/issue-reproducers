import com.oracle.svm.core.annotate.Delete;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(Main.class)
public final class Target_Main {

    @Delete
    private static void dontCallMe() {
        // Do nothing;
    }

}