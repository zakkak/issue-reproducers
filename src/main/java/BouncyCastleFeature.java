import com.oracle.svm.core.annotate.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization;

import java.security.Security;

@AutomaticFeature
public class BouncyCastleFeature implements Feature {

    @Override
    public void afterRegistration(AfterRegistrationAccess access) {
        System.out.println("After Registration Hello!!!!");

        // RuntimeClassInitialization.initializeAtBuildTime("org.bouncycastle");
        // RuntimeClassInitialization.initializeAtRunTime("org.bouncycastle.crypto.CryptoServicesRegistrar");
        // RuntimeClassInitialization.initializeAtRunTime("org.bouncycastle.jcajce.provider.drbg.DRBG$Default");
        // RuntimeClassInitialization.initializeAtRunTime("org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV");

        // Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        System.out.println("BeforeAnalysis Hello!!!!");

        RuntimeClassInitialization.initializeAtBuildTime("org.bouncycastle");
        RuntimeClassInitialization.initializeAtRunTime("org.bouncycastle.crypto.CryptoServicesRegistrar");
        RuntimeClassInitialization.initializeAtRunTime("org.bouncycastle.jcajce.provider.drbg.DRBG$Default");
        RuntimeClassInitialization.initializeAtRunTime("org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV");

        // Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void afterAnalysis(AfterAnalysisAccess access) {
        System.out.println("AfterAnalysis Hello!!!!");

        // Too late... providers need to be registered before the analysis

        // Security.addProvider(new BouncyCastleProvider());
    }
}