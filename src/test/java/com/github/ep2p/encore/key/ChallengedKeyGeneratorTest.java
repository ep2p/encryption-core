package com.github.ep2p.encore.key;

import com.github.ep2p.encore.Generator;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.KeyPair;

public class ChallengedKeyGeneratorTest {

    @Test
    public void generate(){
        long begin = System.currentTimeMillis();
        Generator<KeyPair> keyPairGenerator = new ChallengedKeyGenerator(2, new KeyGenerator());
        KeyPair generate = keyPairGenerator.generate();
        long end = System.currentTimeMillis();
        System.out.println(new BigInteger(generate.getPublic().getEncoded()).toString());
        System.out.println(end - begin);

        begin = System.currentTimeMillis();
        Generator<KeyPair> keyPairGenerator1 = new MultiThreadChallengedKeyGenerator(3, new KeyGenerator(), 5);
        generate = keyPairGenerator1.generate();
        end = System.currentTimeMillis();
        System.out.println(new BigInteger(generate.getPublic().getEncoded()).toString());
        System.out.println(end - begin);
    }

}
