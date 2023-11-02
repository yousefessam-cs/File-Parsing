package com.example.adapter.service.impl;

import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class ProcessingFile implements com.example.adapter.service.ProcessingFile {


    private final TransactionsHandlerImpl transactionsHandler;
    private final ValidateFileDataImpl validateFileDataImpl;
    private final MovingFileToFailedImpl movingFileToFailedImpl;

    public ProcessingFile(RabbitTemplate rabbitTemplate, TransactionsHandlerImpl transactionsHandler, ValidateFileDataImpl validateFileDataImpl, MovingFileToFailedImpl movingFileToFailedImpl) {
        this.transactionsHandler = transactionsHandler;

        this.validateFileDataImpl = validateFileDataImpl;
        this.movingFileToFailedImpl = movingFileToFailedImpl;
    }




    @Override
    @SneakyThrows
    public void processFile(String data, File file,long fileId,String fileName){

        boolean isTransactionCountValid = validateFileDataImpl.validateTransactionCount(data);
        boolean isTransactionLengthValid = validateFileDataImpl.validateTransactionLength(data);
        if (isTransactionCountValid && isTransactionLengthValid) {
            System.out.println("File processed Successfully");
            transactionsHandler.sendTransactionsToQueue(data,fileId,fileName);
        } else {
            movingFileToFailedImpl.moveFileToFailedFolder(file);
            System.out.println("Validation failed. Check failed file in the failed folder.");

        }

    }
}
