package io.ep2p.encryption.helper;

import java.security.PublicKey;

public interface RingKeyVerifier {
    boolean verify(PublicKey publicKey, byte[] signature, String key, int part);
}
