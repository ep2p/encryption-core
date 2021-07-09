package io.ep2p.encryption.serialize;

import io.ep2p.encryption.key.BytesPublicKeyGenerator;
import io.ep2p.encryption.key.KeyGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PublicKey;

public class BytesPublicKeyGeneratorTest {
    @Test
    public void test(){
        BytesPublicKeyGenerator bytesPublicKeyGenerator = new BytesPublicKeyGenerator();
        KeyPair keyPair = new KeyGenerator().generate();
        byte[] encoded = keyPair.getPublic().getEncoded();
        PublicKey publicKey = bytesPublicKeyGenerator.generate(encoded);
        System.out.println(publicKey.toString());
        System.out.println(keyPair.getPublic().toString());
        Assertions.assertEquals(publicKey, keyPair.getPublic());
    }
}
