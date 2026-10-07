public final class Main {
    public static void main(String[] args) {
        System.out.println(invoke(new ServiceMonitor()));
    }

    public static String invoke(ServiceMonitor monitor) {
        return monitor.message();
    }
}
