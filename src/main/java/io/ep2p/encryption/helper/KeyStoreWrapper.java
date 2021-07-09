package io.ep2p.encryption.helper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.*;

@RequiredArgsConstructor
public class KeyStoreWrapper {
    private final KeyStore keyStore;
    private final String address;
    private final String password;
    private String publicKeyHash;
    private PrivateKeyProvider privateKeyProvider;

    private synchronized PrivateKeyProvider getPrivateKeyProvider(){
        if(privateKeyProvider == null){
            privateKeyProvider = new PrivateKeyProvider(keyStore, password);
        }
        return privateKeyProvider;
    }

    public synchronized void addCertificate(Certificate certificate, String userId) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        keyStore.setCertificateEntry(userId, certificate);
        store();
    }

    private void store() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(address));
        keyStore.store(fileOutputStream, password.toCharArray());
        fileOutputStream.close();
    }

    public void deleteCertificate(String userId) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        keyStore.deleteEntry(userId);
        store();
    }

    public Certificate getEncodedCertificate(String base64) throws CertificateException {
        byte[] bytes = new Base64().decode(base64);
        return getCertificate(bytes);
    }

    public Certificate getCertificate(byte[] bytes) throws CertificateException {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(bytes);
        return certFactory.generateCertificate(in);
    }

    public void addCertificate(String base64, String userId) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        addCertificate(getEncodedCertificate(base64), userId);
    }
    public void addCertificate(byte[] bytes, String userId) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        addCertificate(getCertificate(bytes), userId);
    }

    public Certificate getCertificate(String alias) throws KeyStoreException {
        return keyStore.getCertificate(alias);
    }

    public List<Certificate> getCertificatesList() throws KeyStoreException {
        Enumeration<String> aliases = keyStore.aliases();
        List<Certificate> certificates = new ArrayList<>();
        while (aliases.hasMoreElements()){
            String element = aliases.nextElement();
            certificates.add(keyStore.getCertificate(element));
        }
        return certificates;
    }

    public Map<String, Certificate> getCertificatesMap() throws KeyStoreException {
        Enumeration<String> aliases = keyStore.aliases();
        Map<String, Certificate> certificates = new HashMap<>();
        while (aliases.hasMoreElements()){
            String element = aliases.nextElement();
            certificates.put(element, keyStore.getCertificate(element));
        }
        return certificates;
    }

    public KeyStore getKeyStore(){
        return keyStore;
    }

    public synchronized String getPublicKeyHash(){
        if(publicKeyHash == null){
            try {
                Certificate certificate = getCertificate("main");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                publicKeyHash = new Base64().encodeAsString(md.digest(certificate.getPublicKey().getEncoded()));
            } catch (NoSuchAlgorithmException | KeyStoreException e) {
                throw new RuntimeException(e);
            }
        }

        return publicKeyHash;
    }

    public KeyPair getMainKeyPair() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        PublicKey publicKey = getCertificate("main").getPublicKey();
        PrivateKey privateKey = getPrivateKeyProvider().getPrivateKey();
        return new KeyPair(publicKey, privateKey);
    }
}
