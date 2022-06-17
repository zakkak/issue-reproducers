import java.util.Random;

import com.oracle.svm.core.annotate.NeverInline;

public class Main {

    static final ClassNotFoundException CNFE;

    static
    {
        ClassNotFoundException cnfe;

        try {
            Class.forName("Unreachable");
            cnfe = null;
        } catch (ClassNotFoundException e) {
            cnfe = e;
        }

        CNFE = cnfe;
    }

    @NeverInline("I want to see the BGV")
    public static void main(String[] args) {
        if (unreachableIsReachable()) {
            Unreachable.reached();
        }

        System.out.println("Hello world!");
    }

    private static boolean unreachableIsReachable() {
        return CNFE == null;
    }
}