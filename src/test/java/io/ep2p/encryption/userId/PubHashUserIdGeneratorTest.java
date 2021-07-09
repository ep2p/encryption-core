package io.ep2p.encryption.userId;

import io.ep2p.encryption.key.KeyGenerator;
import io.ep2p.encryption.key.PubHashUserIdGenerator;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This is just an example
class PubHashUserIdGeneratorTest {

    //TODO: Should write to file and test equality after writing
    @Test
    void generate() {
        KeyGenerator keyGenerator = new KeyGenerator();
        KeyPair keyPair = keyGenerator.generate();


        PubHashUserIdGenerator pubHashUserIdGenerator = new PubHashUserIdGenerator();
        String generate1 = pubHashUserIdGenerator.generate(keyPair.getPublic()).toString();
        String generate2 = pubHashUserIdGenerator.generate(keyPair.getPublic()).toString();
        System.out.println(generate1);

        assertEquals(generate1, generate2);
    }
}