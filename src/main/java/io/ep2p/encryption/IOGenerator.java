package io.ep2p.encryption;

public interface IOGenerator<I, O> {
    O generate(I input);
}
