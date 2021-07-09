package io.ep2p.encryption.key;

import io.ep2p.encryption.IOGenerator;
import lombok.SneakyThrows;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class BytesPublicKeyGenerator implements IOGenerator<byte[], PublicKey> {
    @SneakyThrows
    @Override
    public PublicKey generate(byte[] input) {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(input));
    }
}
