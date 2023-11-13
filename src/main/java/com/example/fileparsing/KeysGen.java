package com.example.fileparsing;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.security.*;

public class KeysGen {
    @SneakyThrows
    public static void main(String[] args){

            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            System.out.println(privateKey);
            System.out.println(publicKey);

            saveKeyToFile(privateKey,"/Keys/Private.pem");
            saveKeyToFile(publicKey,"/Keys/Public.pub");


    }


    @SneakyThrows
    private static void saveKeyToFile(Key key, String fileName){
        byte[] encodedKey = key.getEncoded();
        FileOutputStream fos = new FileOutputStream("D:\\backup\\FileParsing\\Parser\\src\\main\\resources\\keys\\"+fileName);
        fos.write(encodedKey);
        fos.close();
    }




}
