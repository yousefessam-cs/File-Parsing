package com.example.reciever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RecieverApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecieverApplication.class, args);
    }

}
