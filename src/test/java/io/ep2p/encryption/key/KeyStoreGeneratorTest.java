package io.ep2p.encryption.key;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.UUID;

import static io.ep2p.encryption.helper.CertificateHelper.getValByAttributeTypeFromIssuerDN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KeyStoreGeneratorTest {
    private final KeyStoreGenerator keyStoreGenerator;
    private final String keyAddress = "/tmp/key.jks";
    private final String keyPass = "123456";
    private final String userId = UUID.randomUUID().toString();
    private final CNGenerator cnGenerator = new UserIdCNGenerator(userId);


    public KeyStoreGeneratorTest() throws IOException {
        new File(keyAddress).delete();
        keyStoreGenerator = new KeyStoreGenerator();
    }

    @Test
    public void generate() throws KeyStoreException {
        KeyStore keyStore = keyStoreGenerator.generate(new KeyStoreGenerator.KeyStoreGeneratorInput(cnGenerator, keyAddress, keyPass, new KeyGenerator().generate()));
        assertNotNull(keyStore);

        Certificate certificate = keyStore.getCertificate("main");
        X509Certificate x509Certificate = (X509Certificate) certificate;
        String dn = x509Certificate.getIssuerDN().getName();
        String CN = getValByAttributeTypeFromIssuerDN(dn,"CN=");
        assertEquals(cnGenerator.generate().replace("cn=",""), CN);
    }


}