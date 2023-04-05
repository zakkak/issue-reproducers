public class Main {

    static {
        dontCallMe();
    }

    private static void dontCallMe() {
        throw new IllegalArgumentException("Don't call me!");
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
