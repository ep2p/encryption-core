package com.github.ep2p.encore.key;

import com.github.ep2p.encore.Generator;

import java.security.KeyPair;

public class KeyGeneratorDecorator implements Generator<KeyPair> {
    protected final Generator<KeyPair> keyPairGenerator;

    public KeyGeneratorDecorator(Generator<KeyPair> keyPairGenerator) {
        this.keyPairGenerator = keyPairGenerator;
    }

    @Override
    public KeyPair generate() {
        return keyPairGenerator.generate();
    }
}
