package com.github.ep2p.encore;

public interface IOGenerator<I, O> {
    O generate(I input);
}
