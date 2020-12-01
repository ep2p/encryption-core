package com.github.ep2p.encore.key;

import com.github.ep2p.encore.IOGenerator;
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
