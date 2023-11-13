package com.example.reciever.service;


public interface FileSaver {
    void saveFailedFile(String messageData,String transactionRef);
    void saveSuccessFile(String messageData,String transactionRef);
}
