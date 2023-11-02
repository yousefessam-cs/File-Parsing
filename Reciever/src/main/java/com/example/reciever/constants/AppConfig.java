package com.example.reciever.constants;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class AppConfig {
    @Value(value = "${files.sourcePath}")
    private String sourcePath;

    @Value(value = "${files.processingPath}")
    private String processingPath;

    @Value(value = "${files.successPath}")
    private String successPath;

    @Value(value = "${files.failedPath}")
    private String failedPath;
}
