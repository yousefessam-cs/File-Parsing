package com.example.parser.service.Impl;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.PublicKey;
import java.util.Base64;

@Service
public class EncryptTransactionDataImpl {
    public String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
