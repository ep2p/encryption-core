package io.ep2p.encryption.serialize;

import io.ep2p.encryption.diffieHellman.DiffieHellman;
import io.ep2p.encryption.helper.Serializer;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiffieHellmanSerializeTest {

    @Test
    public void test() throws IOException, ClassNotFoundException {
        DiffieHellman diffieHellman = new DiffieHellman();

        Serializer<DiffieHellman> diffieHellmanSerializer = new Serializer<>();
        byte[] bytes = diffieHellmanSerializer.serialize(diffieHellman);
        DiffieHellman deserialize = diffieHellmanSerializer.deserialize(bytes);

        assertEquals(diffieHellman, deserialize);
    }

    // This just simply means DiffieHellman Objects can be serialized and encoded in base64 and turn into String
    @Test
    public void diffieHellmanBase64SerializeTest() throws IOException, ClassNotFoundException {
        DiffieHellman diffieHellman = new DiffieHellman();

        Serializer<DiffieHellman> diffieHellmanSerializer = new Serializer<>();
        byte[] bytes = diffieHellmanSerializer.serialize(diffieHellman);

        byte[] encodedBase64 = new Base64().encode(bytes);

        String difString = new String(encodedBase64);
        System.out.println(difString);

        byte[] decodedBase64 = new Base64().decode(difString.getBytes());

        DiffieHellman deserialize = diffieHellmanSerializer.deserialize(decodedBase64);

        assertEquals(diffieHellman, deserialize);
    }
}
