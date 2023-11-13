package com.example.reciever.service.Impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
@Service
public class PrivateKeyLoaderImpl {
    @SneakyThrows
    public PrivateKey loadPrivateKeyFromFile(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            byte[] keyBytes = new byte[inputStream.available()];
            inputStream.read(keyBytes);

            String privateKeyContent = new String(keyBytes);
            privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

            byte[] decodedKey = Base64.getDecoder().decode(privateKeyContent);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
