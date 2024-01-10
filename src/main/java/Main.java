import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

public class Main {

    public static void main(String[] args) {
        Substituted substituted = Substituted.INSTANCE;
        System.out.println(substituted.getMessage());
    }
}

@TargetClass(Substituted.class)
final class Target_Substituted {
    @RecomputeFieldValue(kind = RecomputeFieldValue.Kind.FromAlias)
    @Alias
    public static Target_Substituted INSTANCE = new Target_Substituted();

    @Substitute
    public String getMessage() {
        return new String("Substituted");
    }
}