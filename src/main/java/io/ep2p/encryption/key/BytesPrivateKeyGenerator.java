package io.ep2p.encryption.key;

import io.ep2p.encryption.IOGenerator;
import lombok.SneakyThrows;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.X509EncodedKeySpec;

public class BytesPrivateKeyGenerator implements IOGenerator<byte[], PrivateKey> {
    @SneakyThrows
    @Override
    public PrivateKey generate(byte[] input) {
        return KeyFactory.getInstance("RSA").generatePrivate(new X509EncodedKeySpec(input));
    }
}
