package com.github.ep2p.encore.key;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class PubHashUserIdGenerator implements UserIdGenerator<String> {
    @Override
    public String generate(PublicKey publicKey) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(publicKey.getEncoded());
            return new String(new Base64().encode(hash), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
