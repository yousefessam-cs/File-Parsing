package com.example.adapter.service.impl;

import com.example.adapter.constants.AppConfig;
import com.example.adapter.service.MoveFileToProcessing;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class MoveFileToProcessingImpl implements MoveFileToProcessing {
    private final AppConfig appConfig;
    @SneakyThrows
    @Override
    public File moveFileToProcessingFolder(File file) {
        Path sourcePath = Paths.get(appConfig.getSourcePath());
        Path processPath = Paths.get(appConfig.getProcessingPath());

        checkDirectory(sourcePath);
        checkDirectory(processPath);

        String originalFilename = file.getName();
        String filename = originalFilename;
        int counter = 1;

        File sourceFile = new File(sourcePath.toString(),filename);
        File destination = new File(processPath.toString(), filename);

        while (destination.exists()) {
            String baseFilename = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            filename = baseFilename + "_" + counter++ + extension;
            destination = new File(processPath.toString(), filename);
        }
        try{
            Files.move(sourceFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File has been moved to processing...");
        }catch(Exception e){
            e.printStackTrace();
        }

        return destination;
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
