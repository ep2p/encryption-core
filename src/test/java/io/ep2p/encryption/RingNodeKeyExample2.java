package io.ep2p.encryption;

import io.ep2p.encryption.key.*;

public class RingNodeKeyExample2 {
    public static void main(String[] args) {
        KeyGenerator keyGenerator = new KeyGenerator();
        UserIdGenerator<String> userIdGenerator = new PubHashUserIdPartial128Generator(6);
        ChallengedKeyGeneratorDecorator challengedKeyGeneratorDecorator = new ChallengedKeyGeneratorDecorator(keyGenerator, 2, new PubHashUserId128Generator());
        RingKeyGenerator ringKeyGenerator = new RingKeyGenerator(10, challengedKeyGeneratorDecorator, userIdGenerator);
        String id = ringKeyGenerator.generate().getKey();
        System.out.println(id);
        for (String part : id.split("-")) {
            System.out.println(part);
        }
    }
}
