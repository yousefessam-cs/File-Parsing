package com.example.parser.service.Impl;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.util.Base64;

@Service
public class DecryptTransactionDataImpl {
    public String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        try {

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
        }
        catch(Exception e){
            System.out.println(encryptedData + "this is the error");

        }
        return null;
    }
}
