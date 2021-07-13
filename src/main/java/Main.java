import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

public class Main {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        Provider.Service service = Security.getProvider("BC").getService("Cipher", "RSA");
        assert service != null;
        System.out.println(service.getClassName());
    }
}
