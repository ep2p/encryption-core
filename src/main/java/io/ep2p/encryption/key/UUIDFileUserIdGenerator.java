package io.ep2p.encryption.key;

import io.ep2p.encryption.IOGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

public class UUIDFileUserIdGenerator implements IOGenerator<String, String> {
    @Override
    public String generate(String filePath) {
        File file = new File(filePath);
        if(file.exists()){
            StringBuilder stringBuilder = new StringBuilder();
            try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
                stream.forEach(stringBuilder::append);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return stringBuilder.toString();
        }else{
            String uuid = UUID.randomUUID().toString();
            try (PrintWriter out = new PrintWriter(filePath)) {
                out.println(uuid);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return uuid;
        }
    }
}
