package com.example.mail_project;

import com.example.mail_project.controller.AppMailDataController;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class MailProjectApplication {
    public static Path downloadedContentDir;
    //Path sn = Paths.get("/home/nick/File_mail");
    //Path path = FileSystems.getDefault().getPath("/home/nick/File_mail/");

    @Autowired
    static AppMailDataController appMailDataController;

    public static void main(String[] args) throws IOException {



        Logger LOGGER = LoggerFactory.getLogger(MailProjectApplication.class);
        //downloadedContentDir = Files.createTempDirectory("line-bot");// รับส่งรูปภาพ------------------------
        downloadedContentDir = FileSystems.getDefault().getPath("/home/pang/File_Line/");
        LOGGER.info("LOGGER :{}", downloadedContentDir);
        SpringApplication.run(MailProjectApplication.class, args);

//"/home/nick/File_mail"
    }

}

