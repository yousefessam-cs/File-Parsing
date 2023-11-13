package com.example.reciever.service;

import com.example.reciever.constants.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SaveFileTests {


    @Autowired
    private FileSaver fileSaver;

    private String tempDirPath;
    @Autowired
    private AppConfig appConfig;

    @Test
    void testSaveFailedFile() throws Exception {
        // Arrange
        String messageData = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001300EUR2023081117140114efa3bl";
        String transactionRef = "4efa3bl";

        // Act
        fileSaver.saveFailedFile(messageData, transactionRef);

        // Assert
        String baseFileName = "Failed_transaction_" + transactionRef;
        String extension = ".txt";

        // Check if the file exists
        File file = new File(appConfig.getFailedPath(), baseFileName + extension);
        assertTrue(file.exists());

        // Check if the content of the file is correct
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String fileContent = reader.readLine();
            assertEquals(messageData, fileContent);
        }
    }
    @Test
    void testSaveSuccessFile() throws Exception {
        // Arrange
        String messageData = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36cf";
        String transactionRef = "4efa36cf";

        // Act
        fileSaver.saveSuccessFile(messageData, transactionRef);

        // Assert
        String baseFileName = "succeeded_Transaction_" + transactionRef;
        String extension = ".txt";

        // Check if the file exists
        File file = new File(appConfig.getSuccessPath(), baseFileName + extension);
        assertTrue(file.exists());

        // Check if the content of the file is correct
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String fileContent = reader.readLine();
            assertEquals(messageData, fileContent);
        }
    }



}
