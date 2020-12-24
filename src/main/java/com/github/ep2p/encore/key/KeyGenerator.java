package com.github.ep2p.encore.key;

import com.github.ep2p.encore.Generator;
import lombok.SneakyThrows;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGenerator implements Generator<KeyPair> {
    @SneakyThrows
    @Override
    public KeyPair generate() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
