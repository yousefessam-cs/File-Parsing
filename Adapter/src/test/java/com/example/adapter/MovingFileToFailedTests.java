package com.example.adapter;

import com.example.adapter.constants.AppConfig;
import com.example.adapter.service.MovingFileToFailed;
import lombok.SneakyThrows;

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
class MovingFileToFailedTests {
    @Autowired
    private MovingFileToFailed movingFileToFailed;
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

    @SneakyThrows
    @Test
    void validateMoveProcessingFileToFailedFolder_CheckFileIsEmpty_ReturnsInvalidFileData() {

        Path processsPath = Paths.get(appConfig.getProcessingPath());
        Path failedPath = Paths.get(appConfig.getFailedPath());
        checkDirectory(processsPath);
        checkDirectory(failedPath);

        File processingDir = new File(appConfig.getProcessingPath());
        File failedDir = new File(appConfig.getFailedPath());

        File emptyTextFile = new File(processingDir, "empty.txt");
        assertTrue(emptyTextFile.createNewFile());

        //call the method i working on

        movingFileToFailed.moveFileToFailedFolder(emptyTextFile);

        File[] failedFiles = failedDir.listFiles();
//        assertEquals(1, failedFiles.length);
        assertTrue(Arrays.stream(failedFiles).toList().stream().map(File::getName).toList().contains("empty.txt"));
    System.out.println("found 1 file in Failed folder");
    }

    @Test
    void validateMoveProcessingFileToFailedFolder_CheckNonTxtFileExtention_ReturnsInvalidFileData() throws IOException {

            File processingDir = new File(appConfig.getProcessingPath());
            File failedDir = new File(appConfig.getFailedPath());

            File nonTxtFile = new File(processingDir, "nonTxtFile.pdf");
            assertTrue(nonTxtFile.createNewFile());

            //call the method under test
            movingFileToFailed.moveFileToFailedFolder(nonTxtFile);

            File[] failedFiles = failedDir.listFiles();
//            assertEquals(1, failedFiles.length);
        assertTrue(Arrays.stream(failedFiles).toList().stream().map(File::getName).toList().contains("nonTxtFile.pdf"));
            System.out.println("Found 1 file in the Failed folder");

        }

    @Test
    void validateMoveSourceFileToFailedFolder_CheckFileIsEmpty_ReturnsInvalidFileData() throws IOException {

            File sourceDir = new File(appConfig.getSourcePath());
            File failedDir = new File(appConfig.getFailedPath());

            File nonTxtFile = new File(sourceDir, "empty2.txt");
            assertTrue(nonTxtFile.createNewFile());

            //call the method under test
            movingFileToFailed.moveSourceFileToFailedFolder(nonTxtFile);

            File[] failedFiles = failedDir.listFiles();
//            assertEquals(1, failedFiles.length);
        assertTrue(Arrays.stream(failedFiles).toList().stream().map(File::getName).toList().contains("empty2.txt"));
            System.out.println("Found 1 file in the Failed folder");
        }
    @Test
    void validateMoveSourceFileToFailedFolder_CheckNonTxtFileExtention_ReturnsInvalidFileData() throws IOException {
            File sourceDir = new File(appConfig.getSourcePath());
            File failedDir = new File(appConfig.getFailedPath());

            File nonTxtFile = new File(sourceDir, "nonTxtFile2.pdf");
            assertTrue(nonTxtFile.createNewFile());

            //call the method under test
            movingFileToFailed.moveSourceFileToFailedFolder(nonTxtFile);

            File[] failedFiles = failedDir.listFiles();
//            assertEquals(1, failedFiles.length);
            assertTrue(Arrays.stream(failedFiles).toList().stream().map(File::getName).toList().contains("nonTxtFile2.pdf"));
            System.out.println("Found 1 file in the Failed folder");
        }
    @Test
    void validateMoveProcessingFileToFailedFolder_CheckFileLengthLessThan84_ReturnsInvalidFileData() throws IOException {

            File processDir = new File(appConfig.getProcessingPath());
            File failedDir = new File(appConfig.getFailedPath());

            File wrongDataFile = new File(processDir, "wrong_data.txt");
            FileWriter writer = new FileWriter(wrongDataFile);
            writer.write("FH0004\n" +
                    "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36b\n" +
                    "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                    "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                    "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab");
            writer.close();

            //call the method under test
            movingFileToFailed.moveFileToFailedFolder(wrongDataFile);

            File[] failedFiles = failedDir.listFiles();
//            assertEquals(1, failedFiles.length);
            assertTrue(Arrays.stream(failedFiles).toList().stream().map(File::getName).toList().contains("wrong_data.txt"));
            System.out.println("Found 1 file in the Failed folder");
        }
    @Test
    void validateMoveProcessingFileToFailedFolder_CheckFileLengthGreaterThan84_ReturnsInvalidFileData() throws IOException {

            File processDir = new File(appConfig.getProcessingPath());
            File failedDir = new File(appConfig.getFailedPath());

            File wrongDataFile = new File(processDir, "wrong_data2.txt");
            FileWriter writer = new FileWriter(wrongDataFile);
            writer.write("FH0004\n" +
                    "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36b443\n" +
                    "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                    "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                    "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab");
            writer.close();

            //call the method under test
            movingFileToFailed.moveFileToFailedFolder(wrongDataFile);

            File[] failedFiles = failedDir.listFiles();
//            assertEquals(1, failedFiles.length);
            assertTrue(Arrays.stream(failedFiles).toList().stream().map(File::getName).toList().contains("wrong_data2.txt"));
            System.out.println("Found 1 file in the Failed folder");
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



