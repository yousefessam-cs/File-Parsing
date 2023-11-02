package com.example.adapter.scheduler;

import com.example.adapter.constants.AppConfig;
import com.example.adapter.entity.FileData;
import com.example.adapter.repository.FileDataRepository;
import com.example.adapter.service.ProcessingFile;
import com.example.adapter.service.impl.LoadProcessingFileImpl;
import com.example.adapter.service.impl.MoveFileToProcessingImpl;
import com.example.adapter.service.impl.MovingFileToFailedImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;

@Component
@AllArgsConstructor

public class AdapterScheduler {

//    private final AdapterService adapterService;
    private MoveFileToProcessingImpl moveFileToProcessingImpl;
    private LoadProcessingFileImpl loadProcessingFileImpl;
    private ProcessingFile processingFile;
    private final AppConfig appConfig;
    private MovingFileToFailedImpl movingFileToFailed;
    private FileDataRepository fileDataRepository;
    // if file exists in source folder
    // move to processing folder
    // load data from processing folder file
    // process file (validation)
    // if (process) returns valid
    // send to Parser Service
    // else save processing file to Failed folder
    @Scheduled(fixedDelay = 5000)
    public void execute() {

        try {
            File sourceDir = new File(appConfig.getSourcePath());
            File[] files = sourceDir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (!file.getName().toLowerCase().endsWith(".txt") || file.length() == 0) {
                            movingFileToFailed.moveSourceFileToFailedFolder(file);
                            continue;
                        } else {
                            FileData fileData=new FileData();
                            fileData.setFileName(file.getName());
                            fileData.setId(generateFileId());
                            fileDataRepository.save(fileData);
                            File movedFile=moveFileToProcessingImpl.moveFileToProcessingFolder(file);
                            String data = loadProcessingFileImpl.loadFileData(movedFile);
                            processingFile.processFile(data,file,fileData.getId(),fileData.getFileName());
                        }
                    }
                }
            } else {
                System.out.println("No files found in source directory");
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    public long generateFileId() {
        long timestamp = Instant.now().toEpochMilli();
        return timestamp;
    }

}
