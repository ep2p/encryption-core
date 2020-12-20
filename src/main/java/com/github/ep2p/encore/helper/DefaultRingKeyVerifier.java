package com.github.ep2p.encore.helper;

import com.github.ep2p.encore.key.UserIdGenerator;

import java.security.InvalidKeyException;
import java.security.PublicKey;

public class DefaultRingKeyVerifier implements RingKeyVerifier {
    private final UserIdGenerator<String> userIdGenerator;

    public DefaultRingKeyVerifier(UserIdGenerator<String> userIdGenerator) {
        this.userIdGenerator = userIdGenerator;
    }

    @Override
    public boolean verify(PublicKey publicKey, byte[] signature, String key, int part) {
        String[] parts = key.split("-");
        if (parts.length >= part) {
            try {
                boolean verify = SignatureVerifier.verify(publicKey, signature, key.getBytes());
                if(!verify)
                    return false;
            } catch (InvalidKeyException e) {
                return false;
            }
            String partialKey = parts[part - 1];
            String generatedPartialKey = userIdGenerator.generate(publicKey);
            return partialKey.equals(generatedPartialKey);
        }
        return false;
    }
}
