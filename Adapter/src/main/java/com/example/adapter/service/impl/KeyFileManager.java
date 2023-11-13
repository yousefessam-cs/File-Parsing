package com.example.adapter.service.impl;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;
@Service
public class KeyFileManager {
    public void savePrivateKeyToFile(Key key, String fileName) {
        try {
            byte[] keyBytes = key.getEncoded();
            String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);
            Path filePath = Paths.get("D:\\backup\\FileParsing\\Parser\\src\\main\\resources\\keys\\" + fileName +".pem");
            Files.write(filePath, base64EncodedKey.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void savePublicKeyToFile(Key key, String fileName) {
        try {
            byte[] keyBytes = key.getEncoded();
            String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);
            Path filePath = Paths.get("D:\\backup\\FileParsing\\Parser\\src\\main\\resources\\keys\\" + fileName +".pub");
            Files.write(filePath, base64EncodedKey.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
