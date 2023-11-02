package com.example.adapter.service;

import java.io.File;
import java.io.IOException;

public interface MovingFileToFailed {

    public void moveFileToFailedFolder(File thefile);
    void moveSourceFileToFailedFolder(File file);
}
