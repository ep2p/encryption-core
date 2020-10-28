package com.github.ep2p.encore.key;

import com.github.ep2p.encore.Generator;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

public class ChallengedKeyGeneratorDecoratorTest {

    @Test
    public void generate(){
        long begin = System.currentTimeMillis();
        PubHashUserId128Generator pubHashUserId128Generator = new PubHashUserId128Generator();
        Generator<KeyPair> keyPairGenerator = new ChallengedKeyGeneratorDecorator(new KeyGenerator(),2, pubHashUserId128Generator);
        KeyPair keyPair = keyPairGenerator.generate();
        long end = System.currentTimeMillis();
        System.out.println(pubHashUserId128Generator.generate(keyPair.getPublic()));
        System.out.println(end - begin);

        begin = System.currentTimeMillis();
        Generator<KeyPair> keyPairGenerator1 = new MultiThreadChallengedKeyGenerator(new ChallengedKeyGeneratorDecorator(new KeyGenerator(),2, pubHashUserId128Generator), 5);
        keyPair = keyPairGenerator1.generate();
        end = System.currentTimeMillis();
        System.out.println(pubHashUserId128Generator.generate(keyPair.getPublic()));
        System.out.println(end - begin);
    }

}
