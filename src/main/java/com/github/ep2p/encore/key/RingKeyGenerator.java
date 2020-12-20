package com.github.ep2p.encore.key;

import com.github.ep2p.encore.Generator;
import lombok.*;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

public class RingKeyGenerator implements Generator<RingKeyGenerator.RingKey> {
    private final int parts;
    private final Generator<KeyPair> keyGenerator;
    private final UserIdGenerator<String> userIdGenerator;

    public RingKeyGenerator(int parts, Generator<KeyPair> keyGenerator, UserIdGenerator<String> userIdGenerator) {
        this.parts = parts;
        this.keyGenerator = keyGenerator;
        this.userIdGenerator = userIdGenerator;
    }

    @Override
    public RingKey generate() {
        List<String> ids = new ArrayList<>();
        List<KeyPair> keyPairs = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < parts; i++){
            KeyPair keyPair = keyGenerator.generate();
            keyPairs.add(keyPair);
            String id = userIdGenerator.generate(keyPair.getPublic());
            ids.add(id);
            stringBuilder.append(id);
            if(i != parts - 1){
                stringBuilder.append("-");
            }
        }
        return RingKey.builder()
                .key(stringBuilder.toString())
                .ids(ids)
                .keyPairs(keyPairs)
                .build();
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RingKey {
        private List<String> ids;
        private List<KeyPair> keyPairs;
        private String key;
    }
}
