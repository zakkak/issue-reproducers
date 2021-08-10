import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String[] mechanisms = new String[]{"DIGEST-MD5"};
        String authzid = "";
        String protocol = "HTTPS";
        String serverName = "test";
        Map<String, ?> props = null;
        CallbackHandler callbackHandler = callbacks -> { };
        try {
            SaslClient sc = Sasl.createSaslClient(mechanisms, authzid, protocol, serverName, props, callbackHandler);
        } catch (SaslException e) {
            e.printStackTrace();
        }
    }
}
