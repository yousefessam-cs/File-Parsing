package com.example.adapter;

import com.example.adapter.constants.AppConfig;
import com.example.adapter.service.MoveFileToProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MovingFileToProcessingTests {

    @Autowired
    private MoveFileToProcessing moveFileToProcessing;
    @Autowired
    private AppConfig appConfig;
    @BeforeEach
    void init() throws IOException {

        Path sourcePath = Paths.get(appConfig.getSourcePath());
        Path processsPath = Paths.get(appConfig.getProcessingPath());
        Path failedPath = Paths.get(appConfig.getFailedPath());
        checkDirectory(sourcePath);
        checkDirectory(processsPath);
        checkDirectory(failedPath);


    }

    @Test
    void validateMoveSourceFileToProcessing_CheckValidFile_ReturnsFileTransferred() throws IOException {
        File sourceDir = new File(appConfig.getSourcePath());
        File processingDir = new File(appConfig.getProcessingPath());
        File file1 = new File(sourceDir, "file1.txt");
        FileWriter writer = new FileWriter(file1);
        writer.write("FH0004\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab");
        writer.close();
        moveFileToProcessing.moveFileToProcessingFolder(file1);
        File[] processedFiles = processingDir.listFiles();
//        assertEquals(1, processedFiles.length);
        assertTrue(Arrays.stream(processedFiles).toList().stream().map(File::getName).toList().contains("file1.txt"));
        System.out.println("found 1 file in Processed folder");
    }
    private static void checkDirectory(Path Path) {
        if (!Files.exists(Path)){
            try {
                Files.createDirectories(Path);
                System.out.println("Directory+"+ Path.toAbsolutePath() + "created successfully.");
            } catch (Exception e) {
                System.out.println("Error creating directory: " + e.getMessage());
            }
        } else {
            System.out.println("The directory already exists.");
        }
    }


}
