package com.github.ep2p.encore.key;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class PubHashUserIdPartial128Generator implements UserIdGenerator<String> {
    private final int part;

    public PubHashUserIdPartial128Generator(int part) {
        this.part = part;
    }

    @Override
    public String generate(PublicKey publicKey) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(publicKey.getEncoded());
            return Hex.encodeHexString(hash).toUpperCase().substring(0, part);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] splitAfterNChars(String input, int splitLen){
        return input.split(String.format("(?<=\\G.{%1$d})", splitLen));
    }
}
