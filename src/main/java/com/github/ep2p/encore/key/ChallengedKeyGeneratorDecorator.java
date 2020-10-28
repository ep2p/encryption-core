package com.github.ep2p.encore.key;

import com.github.ep2p.encore.Generator;

import java.math.BigInteger;
import java.security.KeyPair;

public class ChallengedKeyGeneratorDecorator extends KeyGeneratorDecorator {
    private final int zeros;
    private final String challenge;
    private final UserIdGenerator<BigInteger> userIdGenerator;
    private volatile boolean interrupt = false;

    public ChallengedKeyGeneratorDecorator(Generator<KeyPair> keyPairGenerator, int zeros, UserIdGenerator<BigInteger> userIdGenerator) {
        super(keyPairGenerator);
        this.zeros = zeros;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < zeros; i++){
            stringBuilder.append("0");
        }
        this.challenge = stringBuilder.toString();
        this.userIdGenerator = userIdGenerator;
    }

    @Override
    public synchronized KeyPair generate() {
        KeyPair keyPair = null;
        boolean con = true;
        while (con && !interrupt){
            keyPair = keyPairGenerator.generate();
            String string = userIdGenerator.generate(keyPair.getPublic()).toString();
            String substring = string.substring(string.length() - zeros, string.length());
            con = !substring.equals(challenge);
        }

        return keyPair;
    }

    public void interrupt(){
        this.interrupt = true;
    }

    public boolean isInterrupt() {
        return interrupt;
    }
}
