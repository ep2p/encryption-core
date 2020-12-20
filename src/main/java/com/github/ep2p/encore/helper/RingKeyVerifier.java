package com.github.ep2p.encore.helper;

import java.security.PublicKey;

public interface RingKeyVerifier {
    boolean verify(PublicKey publicKey, byte[] signature, String key, int part);
}
