package com.github.ep2p.encore.key;


import com.github.ep2p.encore.helper.ChallengedRingKeyVerifierWrapper;
import com.github.ep2p.encore.helper.DefaultRingKeyVerifier;
import com.github.ep2p.encore.helper.MessageSigner;
import com.github.ep2p.encore.helper.RingKeyVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.KeyPair;

public class ChallengedNodeKeyGeneratorTest {

    @Test
    public void canGenerateValidRingKey() throws InvalidKeyException {
        int parts = 10;
        PubHashUserIdPartial128Generator userIdGenerator = new PubHashUserIdPartial128Generator(5);
        ChallengedKeyGeneratorDecorator challengedKeyGeneratorDecorator = new ChallengedKeyGeneratorDecorator(new KeyGenerator(), 2, new PubHashUserId128Generator());
        RingKeyGenerator ringKeyGenerator = new RingKeyGenerator(10, challengedKeyGeneratorDecorator, userIdGenerator);
        RingKeyGenerator.RingKey ringKey = ringKeyGenerator.generate();

        System.out.println(ringKey.getKey());

        RingKeyVerifier ringKeyVerifier = new ChallengedRingKeyVerifierWrapper(2, new DefaultRingKeyVerifier(userIdGenerator), new PubHashUserId128Generator());

        for(int i = 0; i < parts; i++){
            String id = ringKey.getIds().get(i);
            KeyPair keyPair = ringKey.getKeyPairs().get(i);
            MessageSigner messageSigner = new MessageSigner(keyPair.getPrivate());
            boolean verify = ringKeyVerifier.verify(keyPair.getPublic(), messageSigner.sign(ringKey.getKey().getBytes()), ringKey.getKey(), i + 1);
            Assertions.assertTrue(verify);
            System.out.println("Verified " + id);
        }

        System.out.println("All parts of key is verified");

    }

}
