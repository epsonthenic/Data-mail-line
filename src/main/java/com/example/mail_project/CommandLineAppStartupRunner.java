package com.example.mail_project;

import com.example.mail_project.controller.AppMailDataController;
import com.example.mail_project.controller.AppReceiveMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    AppMailDataController appMailDataController;


    @Override
    public void run(String...args) throws Exception {

        appMailDataController.getAppCusData();
        System.out.println("++++++");
    }
}
