package com.github.ep2p.encore.key;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.stream.Stream;

public class PubHashUserId128Generator implements UserIdGenerator<BigInteger> {
    private final PublicKey publicKey;
    private BigInteger uuid;
    private String filePath;

    /* Accepts null publicKey when keystore file already exists */
    public PubHashUserId128Generator(String filePath, PublicKey publicKey) {
        this.publicKey = publicKey;
        this.filePath = filePath;
        init();
    }

    private void init(){
        File file = new File(filePath);
        if(file.exists()){
            StringBuilder stringBuilder = new StringBuilder();
            try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
                stream.forEach(stringBuilder::append);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.uuid = new BigInteger(stringBuilder.toString());
        }else{
            this.uuid = doGenerate();
            try (PrintWriter out = new PrintWriter(filePath)) {
                out.println(uuid);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private BigInteger doGenerate() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(publicKey.getEncoded());
            return new BigInteger(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigInteger generate() {
        return uuid;
    }
}
