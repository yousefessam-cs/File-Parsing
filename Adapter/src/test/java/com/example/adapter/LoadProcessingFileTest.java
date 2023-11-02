package com.example.adapter;

import com.example.adapter.constants.AppConfig;
import com.example.adapter.service.LoadProcessingFile;
import com.example.adapter.service.MoveFileToProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoadProcessingFileTest {
    @Autowired
    private LoadProcessingFile loadProcessingFile;
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
    void testLoadProcessingFile_ValidFileSavingSuccessfully_ReturnsLoadedFileData() throws IOException {
        File processingDir = new File(appConfig.getProcessingPath());
        File testFile = new File(processingDir, "test.txt");
        testFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write("FH0004\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab\n");
        writer.close();
        String fileData =loadProcessingFile.loadFileData(testFile);
        assertEquals("FH0004\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab\n", fileData);



    }
    @Test
    void testLoadProcessingFile_checkEmptyFile_ReturnsFileMovedToFailed() throws IOException {
        File processingDir = new File(appConfig.getProcessingPath());
        File failedDir = new File(appConfig.getFailedPath());
        File emptyTextFile = new File(processingDir, "empty1.txt");
        assertTrue(emptyTextFile.createNewFile());
       loadProcessingFile.loadFileData(emptyTextFile);
        File[] failedFiles = failedDir.listFiles();
        assertNotNull(failedFiles);
        assertTrue(failedFiles.length > 0);
        assertTrue(Arrays.stream(failedFiles).toList().stream().map(File::getName).toList().contains("empty1.txt"));
        System.out.println("found 1 file in Failed folder");

    }
    @Test
    void testLoadProcessingFile_CheckNonTxtFileExtention_ReturnsInvalidFileData() throws IOException{
        File processingDir = new File(appConfig.getProcessingPath());
        File failedDir = new File(appConfig.getFailedPath());

        File nonTxtFile = new File(processingDir, "nonTxtFile.pdf");
        assertTrue(nonTxtFile.createNewFile());

        //call the method under test
        loadProcessingFile.loadFileData(nonTxtFile);

        File[] failedFiles = failedDir.listFiles();
//        assertEquals(1, failedFiles.length);
        assertTrue(Arrays.stream(failedFiles).toList().stream().map(File::getName).toList().contains("nonTxtFile.pdf"));
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
    @Test
    void testLoadProcessingFile_validScenarioSuccessfully_ReturnsLoadedFileData() throws IOException {
        String fileName = "test.txt";
        File testFile = new File(appConfig.getSourcePath(), "test.txt");
        testFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write("FH0004\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab\n");
        writer.close();
        boolean isFileExistInSource= checkFileExistsInSource(fileName);
        assertTrue(isFileExistInSource);
       File movedFile= moveFileToProcessing.moveFileToProcessingFolder(testFile);
        String fileData =loadProcessingFile.loadFileData(movedFile);
        assertEquals("FH0004\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab\n", fileData);

    }
    public boolean checkFileExistsInSource(String fileName) {
        File sourceDirectory = new File(appConfig.getSourcePath());
        File file = new File(sourceDirectory, fileName);
        return file.exists();
    }




}
