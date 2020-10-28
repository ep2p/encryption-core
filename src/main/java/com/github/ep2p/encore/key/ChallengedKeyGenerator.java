package com.github.ep2p.encore.key;

import com.github.ep2p.encore.Generator;

import java.security.KeyPair;

public class ChallengedKeyGenerator implements Generator<KeyPair> {
    private final int zeros;
    private final Generator<KeyPair> keyPairGenerator;
    private final String challenge;
    private volatile boolean interrupt = false;

    public ChallengedKeyGenerator(int zeros, Generator<KeyPair> keyPairGenerator) {
        this.zeros = zeros;
        this.keyPairGenerator = keyPairGenerator;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < zeros; i++){
            stringBuilder.append("0");
        }
        this.challenge = stringBuilder.toString();
    }

    @Override
    public synchronized KeyPair generate() {
        KeyPair keyPair = null;
        boolean con = true;
        while (con && !interrupt){
            keyPair = keyPairGenerator.generate();
            PubHashUserId128Generator pubHashUserId128Generator = new PubHashUserId128Generator();
            String string = pubHashUserId128Generator.generate(keyPair.getPublic()).toString();
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
