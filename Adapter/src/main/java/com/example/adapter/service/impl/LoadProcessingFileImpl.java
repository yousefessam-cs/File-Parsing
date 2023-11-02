package com.example.adapter.service.impl;

import com.example.adapter.constants.AppConfig;
import com.example.adapter.service.LoadProcessingFile;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
@AllArgsConstructor
public class LoadProcessingFileImpl implements LoadProcessingFile {
    private final AppConfig appConfig;
    private MovingFileToFailedImpl movingFileToFailed;

    @SneakyThrows
    @Override
    public String loadFileData(File file) {
        if (file.isFile()) {
            String filePath = file.getAbsolutePath();
            if (file.length() == 0 || !file.getName().toLowerCase().endsWith(".txt")) {
                movingFileToFailed.moveFileToFailedFolder(file);
                return "Invalid File";
            }
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                System.out.println("Loading File Data");
                StringBuilder data = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    data.append(line).append("\n");
                }
                return data.toString();
            } catch (IOException e) {
                System.err.println("Failed to read file: " + filePath);
            }
        } else {
            return "The specified file is not valid.";
        }

        return "There was an error while loading the file data.";
    }
}




//        Resource classPathResource = new ClassPathResource("Transactions\\trx-sample.txt");
//        String filePath = classPathResource.getFilename();
//        try (
//                BufferedReader br = new BufferedReader(new FileReader(classPathResource.getFile()))) {
//            System.out.println("Loading File Data");
//            StringBuilder data = new StringBuilder();
//            String line;
//            while ((line = br.readLine()) != null) {
//                data.append(line).append("\n");
//            }
//            return data.toString();
//        }
