package io.ep2p.encryption.key;

import io.ep2p.encryption.Generator;

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
