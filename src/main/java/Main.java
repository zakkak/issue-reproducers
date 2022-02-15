import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {
        ClassLoader cl = (new Main()).getClass().getClassLoader();
        URL url = cl.getResource("META-INF/");
        URL url2 = cl.getResource("META-INF/./");
        System.out.println("url = " + url);
        System.out.println("url2 = " + url2);
        if (url.toString().endsWith("/") && url2 == null) {
            System.out.println("SUCCESS");
        } else {
            System.out.println("FAILURE");
        }
    }
}