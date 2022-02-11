import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

// import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Main {

    static {
        System.out.println("Hello!!!!");

        // Too late... providers need to be registered before the analysis, but
        // static initialization takes plave after it.

        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {

        try {
            // KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            // keyPairGenerator.generateKeyPair();
            // System.out.println("Success");
            Cipher rsaInstance = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            System.out.println(rsaInstance.getAlgorithm());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }
}
