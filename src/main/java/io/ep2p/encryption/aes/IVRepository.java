package io.ep2p.encryption.aes;

import javax.crypto.spec.IvParameterSpec;

public interface IVRepository {
    default IvParameterSpec getIvParameterSpec(){
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        return new IvParameterSpec(iv);
    }
}
