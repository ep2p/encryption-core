package com.github.ep2p.encore.key;

import com.github.ep2p.encore.IOGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static com.github.ep2p.encore.helper.CertificateHelper.generateCertificate;
import static com.github.ep2p.encore.helper.CertificateHelper.getValByAttributeTypeFromIssuerDN;

public class KeyStoreGenerator implements IOGenerator<KeyStoreGenerator.KeyStoreGeneratorInput, KeyStore> {

    @Getter
    @Setter
    public final static class KeyStoreGeneratorInput {
        private CNGenerator cnGenerator;
        private String address;
        private String password;
        private KeyPair keyPair;

        public KeyStoreGeneratorInput(CNGenerator cnGenerator, String address, String password) {
            this.cnGenerator = cnGenerator;
            this.address = address;
            this.password = password;
        }

        public KeyStoreGeneratorInput(CNGenerator cnGenerator, String address, String password, KeyPair keyPair) {
            this.cnGenerator = cnGenerator;
            this.address = address;
            this.password = password;
            this.keyPair = keyPair;
        }
    }

    @SneakyThrows
    @Override
    public KeyStore generate(KeyStoreGeneratorInput input) {
        File file = new File(input.getAddress());
        boolean fileExists = false;
        if(!file.exists()) {
            file.createNewFile();
        }else{
            fileExists = true;
        }

        if(fileExists){
            KeyStore keyStore = getExistingKeyStore(file, input.getPassword());
            if(isValidKeyStore(keyStore, input.getCnGenerator())){
                return keyStore;
            }
        }
        return doGenerate(file, input.getCnGenerator(), input.getKeyPair(), input.getPassword());
    }

    private boolean isValidKeyStore(KeyStore keyStore, CNGenerator cnGenerator) {
        try {
            Certificate certificate = keyStore.getCertificate("main");
            X509Certificate x509Certificate = (X509Certificate) certificate;
            String dn = x509Certificate.getIssuerDN().getName();
            String CN = getValByAttributeTypeFromIssuerDN(dn,"CN=");
            if ((cnGenerator.generate().replace("cn=", "").equals(CN))) {
                return true;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return false;
    }

    private KeyStore getExistingKeyStore(File file, String password) {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(file), password.toCharArray());
            return keyStore;
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("Could not load existing key. Is password right?", e);
        }
    }

    private KeyStore doGenerate(File file, CNGenerator cnGenerator, KeyPair keyPair, String password){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);

            Certificate[] chain = {generateCertificate(cnGenerator.generate(), keyPair, 365 * 10, "SHA256withRSA")};
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setKeyEntry("main", keyPair.getPrivate(), password.toCharArray(), chain);
            keyStore.store(fileOutputStream, password.toCharArray());
            return keyStore;
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
