package io.ep2p.encryption.userId;

import io.ep2p.encryption.key.KeyGenerator;
import io.ep2p.encryption.key.PubHashUserId128Generator;
import io.ep2p.encryption.key.UserIdGenerator;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This is just an example
class PubHashUserId128GeneratorTest {

    //TODO: Should write to file and test quality of file data and generated data

    @Test
    void generate() {
        KeyGenerator keyGenerator = new KeyGenerator();
        KeyPair keyPair = keyGenerator.generate();


        UserIdGenerator<BigInteger> pubHashUserIdGenerator = new PubHashUserId128Generator();
        BigInteger generate1 = pubHashUserIdGenerator.generate(keyPair.getPublic());
        BigInteger generate2 = pubHashUserIdGenerator.generate(keyPair.getPublic());
        System.out.println(generate1);
        String g1base64 = new String(new Base64().encode(generate1.toByteArray()), StandardCharsets.UTF_8);
        String g2base64 = new String(new Base64().encode(generate2.toByteArray()), StandardCharsets.UTF_8);
        System.out.println(g1base64);

        assertEquals(generate1, generate2);
        assertEquals(g1base64, g2base64);
    }
}