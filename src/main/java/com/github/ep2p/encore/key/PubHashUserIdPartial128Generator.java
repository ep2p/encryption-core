package com.github.ep2p.encore.key;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class PubHashUserIdPartial128Generator implements UserIdGenerator<String> {
    private final int partSize;

    public PubHashUserIdPartial128Generator(int partSize) {
        this.partSize = partSize;
    }

    @Override
    public String generate(PublicKey publicKey) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(publicKey.getEncoded());
            return Hex.encodeHexString(hash).toUpperCase().substring(0, partSize);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
