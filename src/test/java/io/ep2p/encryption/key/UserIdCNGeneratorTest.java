package io.ep2p.encryption.key;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserIdCNGeneratorTest {

    @Test
    public void getCN() {
        String s = UUID.randomUUID().toString();
        assertEquals(new UserIdCNGenerator(s).generate(), "cn="+s+".neoroutes");
    }
}