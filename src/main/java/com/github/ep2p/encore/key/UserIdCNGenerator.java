package com.github.ep2p.encore.key;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserIdCNGenerator implements CNGenerator {
    private final String userId;

    @Override
    public String generate() {
        return "cn="+ userId + ".neoroutes";
    }
}
