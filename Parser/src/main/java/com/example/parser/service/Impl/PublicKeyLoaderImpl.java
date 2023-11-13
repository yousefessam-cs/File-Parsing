package com.example.parser.service.Impl;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class PublicKeyLoaderImpl {


    public PublicKey loadPublicKeyFromFile(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            byte[] keyBytes = new byte[inputStream.available()];
            inputStream.read(keyBytes);

            String publicKeyContent = new String(keyBytes);
            publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

            byte[] decodedKey = Base64.getDecoder().decode(publicKeyContent);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle exception properly in your code
        }
    }
}
