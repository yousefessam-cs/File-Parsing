package com.example.reciever.service;


public interface FileSaver {
    void saveFailedFile(String messageData);
    void saveSuccessFile(String messageData);
}
