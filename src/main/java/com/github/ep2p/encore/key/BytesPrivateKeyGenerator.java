package com.github.ep2p.encore.key;

import com.github.ep2p.encore.IOGenerator;
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
