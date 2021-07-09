package io.ep2p.encryption.helper;

import io.ep2p.encryption.key.UserIdGenerator;

import java.math.BigInteger;
import java.security.PublicKey;

public class ChallengedRingKeyVerifierWrapper implements RingKeyVerifier {
    private final RingKeyVerifier ringKeyVerifier;
    private final String challenge;
    private final UserIdGenerator<BigInteger> challengedUserIdGenerator;

    public ChallengedRingKeyVerifierWrapper(int zeros, RingKeyVerifier ringKeyVerifier, UserIdGenerator<BigInteger> challengedUserIdGenerator) {
        this.ringKeyVerifier = ringKeyVerifier;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < zeros; i++){
            stringBuilder.append("0");
        }
        this.challenge = stringBuilder.toString();
        this.challengedUserIdGenerator = challengedUserIdGenerator;
    }

    @Override
    public boolean verify(PublicKey publicKey, byte[] signature, String key, int part) {
        return ringKeyVerifier.verify(publicKey, signature, key, part) &&
                challengedUserIdGenerator.generate(publicKey).toString().endsWith(challenge);
    }
}
