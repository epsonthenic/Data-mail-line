package com.example.mail_project;

import com.example.mail_project.entity.CustomerLog;
import com.example.mail_project.repository.CustomerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataLog implements CommandLineRunner{

    @Autowired
    private CustomerLogRepository customerLogRepository;

    @Override
    public void run(String... args) throws Exception {

        CustomerLog customerLog = new CustomerLog();

        customerLog.setSender("chitsanu");
        customerLog.setSend_To("boonkhun");
        customerLog.setEmail("pang_lovegood@hotmail.com");
        customerLog.setMsg("pang to test");

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateInString = "07-06-2013 00:00:00";


        //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = dateFormat.parse(dateInString);
//        System.out.println("date na :"+date);
//        Timestamp timestamp = new java.sql.Timestamp(date.getTime());
//
//        System.out.println("timestamp : "+timestamp.getTime());

        customerLog.setSentDate(date);

        customerLog.setStatus("test");
        customerLog.setType("pang");

        customerLogRepository.save(customerLog);

    }
}
