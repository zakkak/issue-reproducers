import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> greetings = new ArrayList<>();
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("configs");
            BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            System.err.println("Scanning configs classpath folder");

            String filename;
            while ((filename = br.readLine()) != null) {
                System.err.println("Reading config file" + filename);
                try (
                    InputStream in2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("configs/" + filename);
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(in2))) {
                    String greeting;
                    while ((greeting = br2.readLine()) != null) {
                        greetings.add(greeting);
                    }
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        if (greetings.isEmpty()) {
            throw new IllegalStateException("Greeting service has no greetings");
        }
    }
}
