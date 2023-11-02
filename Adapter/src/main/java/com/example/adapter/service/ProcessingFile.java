package com.example.adapter.service;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.List;


public interface ProcessingFile {



    @SneakyThrows
    public void processFile(String data, File thefile,long fileId,String fileName);


}
