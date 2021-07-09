package io.ep2p.encryption.userId;

import io.ep2p.encryption.key.UUIDFileUserIdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UUIDFileUserIdGeneratorTest {
    private final String path = "user.uuid";

    @Test
    public void generate(){
        UUIDFileUserIdGenerator uuidFileUserIdGenerator1 = new UUIDFileUserIdGenerator();
        UUIDFileUserIdGenerator uuidFileUserIdGenerator2 = new UUIDFileUserIdGenerator();
        System.out.println(uuidFileUserIdGenerator1.generate(path));
        System.out.println(uuidFileUserIdGenerator2.generate(path));
        assertEquals(uuidFileUserIdGenerator1.generate(path), uuidFileUserIdGenerator1.generate(path));
        assertEquals(uuidFileUserIdGenerator1.generate(path), uuidFileUserIdGenerator2.generate(path));
    }

}
