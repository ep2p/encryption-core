package io.ep2p.encryption.key;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class PubHashUserIdGenerator implements UserIdGenerator<BigInteger> {
    @Override
    public BigInteger generate(PublicKey publicKey) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(publicKey.getEncoded());
            return new BigInteger(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
