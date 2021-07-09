package io.ep2p.encryption;

import io.ep2p.encryption.key.*;

public class RingNodeKeyExample {
    public static void main(String[] args) {
        KeyGenerator keyGenerator = new KeyGenerator();
        UserIdGenerator<String> userIdGenerator = new PubHashUserIdPartial128Generator(6);

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 10; i++){
            stringBuilder.append(userIdGenerator.generate(new ChallengedKeyGeneratorDecorator(keyGenerator, 2, new PubHashUserId128Generator()).generate().getPublic()));
            if(i != 9){
                stringBuilder.append("-");
            }
        }
        String id = stringBuilder.toString();
        System.out.println(id);
        for (String part : id.split("-")) {
            System.out.println(part);
        }
    }
}
