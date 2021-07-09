package io.ep2p.encryption.key;

import io.ep2p.encryption.Generator;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.KeyPair;

public class ChallengedKeyGeneratorDecoratorTest {

    @Test
    public void generate(){
        long begin = System.currentTimeMillis();
        UserIdGenerator<BigInteger> userId128Generator = new PubHashUserIdGenerator();
        Generator<KeyPair> keyPairGenerator = new ChallengedKeyGeneratorDecorator(new KeyGenerator(),2, userId128Generator);
        KeyPair keyPair = keyPairGenerator.generate();
        long end = System.currentTimeMillis();
        System.out.println(userId128Generator.generate(keyPair.getPublic()));
        System.out.println(end - begin);

        begin = System.currentTimeMillis();
        Generator<KeyPair> keyPairGenerator1 = new MultiThreadChallengedKeyGenerator(new ChallengedKeyGeneratorDecorator(new KeyGenerator(),2, userId128Generator), 5);
        keyPair = keyPairGenerator1.generate();
        end = System.currentTimeMillis();
        System.out.println(userId128Generator.generate(keyPair.getPublic()));
        System.out.println(end - begin);
    }

}
