package com.example.adapter.service.impl;

import com.example.adapter.constants.AppConfig;
import com.example.adapter.service.MovingFileToFailed;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class MovingFileToFailedImpl implements MovingFileToFailed {


    private final AppConfig appConfig;


    @Override
    @SneakyThrows
    public void moveFileToFailedFolder(File thefile) {
//        File target = new File(appConfig.getFailedPath());
//        File[] files = processDir.listFiles();
        Path processsPath = Paths.get(appConfig.getProcessingPath());
        Path failedPath = Paths.get(appConfig.getFailedPath());

        checkDirectory(processsPath);
        checkDirectory(failedPath);
        System.out.println(thefile.getName());
        String filename = thefile.getName();
        File processFile = new File(processsPath.toString(),filename);
        File destination = new File(failedPath.toString(), filename);
        Files.move(processFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Invalid File Data ... Check Failed Folder");
 }




    @SneakyThrows
    public void moveSourceFileToFailedFolder(File file) {
        Path sourcePath = Paths.get(appConfig.getSourcePath());
        Path failedPath = Paths.get(appConfig.getFailedPath());

        checkDirectory(sourcePath);
        checkDirectory(failedPath);
        System.out.println(file.getName());
        String filename = file.getName();
        File processFile = new File(sourcePath.toString(),filename);
        File destination = new File(failedPath.toString(), filename);
        Files.move(processFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Invalid File Data ... Check Failed Folder");
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
