package com.example.reciever.service.Impl;

import com.example.reciever.constants.AppConfig;
import com.example.reciever.service.FileSaver;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Service
public class FileSaverImpl implements FileSaver {
    private AppConfig appConfig;
//    private static int successCounter = 1;
//    private static int failedCounter = 1;
    @Autowired
    public FileSaverImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
    @Override
    @SneakyThrows
    public void saveFailedFile(String messageData,String transactionRef) {
        File failedFolder = new File(appConfig.getFailedPath());
        if (!failedFolder.exists()) {
            failedFolder.mkdirs();
        }
        String baseFileName = "Failed_transaction_" + transactionRef;
        String extension = ".txt";
        int counter = 1;
        String fileName = baseFileName + extension;
        File file = new File(appConfig.getFailedPath(), fileName);
        while (file.exists()) {
            fileName = baseFileName + "_" + counter + extension;
            file = new File(appConfig.getFailedPath(), fileName);
            counter++;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append(messageData);
//            System.out.println("Saving File Transaction");
        }
    }
    @Override
    @SneakyThrows
    public void saveSuccessFile(String messageData,String transactionRef) {
        File successFolder = new File(appConfig.getSuccessPath());
        if (!successFolder.exists()) {
            successFolder.mkdirs();
        }
        String baseFileName = "succeeded_Transaction_" + transactionRef;
        String extension = ".txt";
        int counter = 1;
        String fileName = baseFileName + extension;
        File file = new File(appConfig.getSuccessPath(), fileName);
        while (file.exists()) {
            fileName = baseFileName + "_" + counter + extension;
            file = new File(appConfig.getSuccessPath(), fileName);
            counter++;
        }
//        String fileName = "Succeeded_transaction_" + successCounter++ + ".txt";
//        String filePath = appConfig.getSuccessPath() + "\\" + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append(messageData);
        }
    }
}
