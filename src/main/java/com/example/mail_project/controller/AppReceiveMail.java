package com.example.mail_project.controller;


import com.example.mail_project.entity.CustomerLog;
import com.example.mail_project.repository.CustomerLogRepository;
import com.example.mail_project.service.AppMailDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AppReceiveMail {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    String email_id = "pingge12345678@gmail.com";
    String password = "E12345678";

    @Autowired
    private AppMailDataService appMailDataService;

    @Autowired
    private CustomerLogRepository customerLogRepository;


    public void receiveMail() {

        List<String> JsonList = new LinkedList<String>();
        String json = "";

        CheckThisEmail c = new CheckThisEmail();

        autoReply2 a = new autoReply2();
        a.autoReply(true);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            //set properties
            Properties properties = new Properties();

            properties.put("mail.store.protocol", "imaps");
            //Host Address of Your Mail
            properties.put("mail.imaps.host", "imap.gmail.com");
            //Port number of your Mail Host
            properties.put("mail.imaps.port", "993");

            //create a session
            Session session = Session.getDefaultInstance(properties, null);
            //SET the store for IMAPS
            Store store = session.getStore("imaps");


            System.out.println("Connection initiated......");
            //Trying to connect IMAP server
            store.connect(email_id, password);
            System.out.println("Connection is ready :)");

            //Get inbox folder
            Folder inbox = store.getFolder("inbox");
            //SET readonly format (*You can set read and write)
            inbox.open(Folder.READ_ONLY);

            Message messages[] = inbox.getMessages();
            //Display email Details

            //Inbox email count
            int messageCount = inbox.getMessageCount();
            System.out.println("Total Messages in INBOX:- " + messageCount);
            System.out.println("------------------------------------------------------------");

            for (int i = 0; i < messageCount; i++) {
                Message mes = messages[i];
                System.out.println("Subject: " + mes.getSubject());

                String sendFrom = mes.getFrom()[0].toString();

                if (sendFrom.contains("=?UTF-8?B?") == true) {
                    base64_test b = new base64_test();
                    sendFrom = b.decodeText(sendFrom);

                } else {

                }
                System.out.println("From: " + sendFrom);

                //ใช้ Class CheckThisMail Method splitWord
                String sender = c.splitWord2(sendFrom);

                String email = c.splitWord1(sendFrom);


                String CC = "", BCC = "";

                //ตรวจว่ามี CC ใน Email หรือไม่
                if (mes.getRecipients(RecipientType.CC) == null) {
                    //ถ้าไม่มี
                } else if (mes.getRecipients(RecipientType.CC) != null) {
                    //ถ้ามี
                    Address cc = mes.getRecipients(RecipientType.CC)[0];
                    System.out.println("CC : " + cc.toString());
                    CC = cc.toString();//แปลงเป็น String
                }
                if (mes.getRecipients(RecipientType.BCC) == null) {//ตรวจว่ามี BCC หรือไม่
                    //ถ้าไม่มี
                } else if (mes.getRecipients(RecipientType.BCC) != null) {//ถ้ามี
                    Address bcc = mes.getRecipients(RecipientType.BCC)[0];
                    System.out.println("BCC : " + bcc.toString());
                    BCC = bcc.toString();
                }


                System.out.println("Sent date: " + mes.getSentDate());

                String contentType = mes.getContentType();

                String attachFiles = "";
                String result = "";
                String saveDirectory = "/home/nick/File_mail";

                //-----------------------------------------------------------------------------------//
                //ตรวจว่า email มี content เป็น อะไร
                if (contentType.contains("multipart")) {// มีไฟล์ ATTACHMENT
                    Multipart multiPart = (Multipart) mes.getContent();//แปลง
                    int numberOfParts = multiPart.getCount();//สร้างจำนวน Part

                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);

                        AppReceiveMail r = new AppReceiveMail();
                        //ใช้ Method getTextFromMimeMultipart
                        result = r.getTextFromMimeMultipart(multiPart, partCount);//เอาข้อความจาก email ออกมา
                        //part ที่มีไฟล์ attachment
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {

                            String fileNameDate=formatter.format(mes.getSentDate());
                            String fileName = fileNameDate+ "-"+RandomNumber()+"-"+ part.getFileName();//rename วันที่ + ชื่อไฟล์
                            attachFiles += fileName + ", ";
                            System.out.println("Attachments: " + attachFiles);
                            part.saveFile(saveDirectory + File.separator + fileName);//save file
                            System.out.println();
                        } else {

                        }
                        //part ที่เป็นประเภท jpeg หรือ png
                        if (part.isMimeType("image/jpeg") || part.isMimeType("image/png")) {

                            System.out.print("image");
                            String fileNameDate=formatter.format(mes.getSentDate());
                            String fileName = fileNameDate + "-"+RandomNumber()+"-"+ part.getFileName();
                            System.out.println("[" + fileName + "]");
                            attachFiles += fileName + ", ";
                            File f = new File(saveDirectory + File.separator + fileName);

                            boolean check = new File(saveDirectory + File.separator, fileName).exists();
                            // check directory มีไฟล์ชื่อนี้ไหม
                            if (check == false) {//ถ้าไม่มีไฟล์
                                System.out.println("Download : " + fileName);
                                part.saveFile(saveDirectory + File.separator + fileName);//ดาวน์โหลดไฟล์

                            } else if (check == true) { //มีไฟล์แล้ว
                                System.out.println("Have this file");//แสดงข้อความ
                            }
                        }
                    }
                    //ตรวจว่ามี attachFiles มากกว่า 1 หรือไม่
                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                }
                //-----------------------------------------------------------------------------------//
                System.out.println("Content : " + result);
                boolean checkL = result.contains("Chitsanu");
                if (checkL == true) {
                    System.out.println("Hello Chitsanu!");
                }

                System.out.println(attachFiles);
                System.out.println(json);

                System.out.println("------------------------------------------------------------");



                CustomerLog customerLog = new CustomerLog();
                customerLog.setSender(sender);
                customerLog.setSend_To(email_id);
                customerLog.setEmail(email);
                customerLog.setMsg(result);
                customerLog.setAttachments(attachFiles);
                customerLog.setResponsible("----");
                customerLog.setSentDate(mes.getSentDate());
                customerLog.setStatus("wait..");
                customerLog.setType("E-MAIL");
                customerLog.setSubject(mes.getSubject());
                customerLog.setCC(CC);
                customerLog.setBCC(BCC);
                customerLog.setMessageNum(mes.getMessageNumber());
                customerLog.setLevel("0");
                LOGGER.info("check repo     :    {}",null == customerLogRepository);
                customerLogRepository.save(customerLog);

//                json = "{\"sender\":\"" + sender + "\",\"send_To\":\"" + email_id + "\",\"email\":\"" + email + "\",\"msg\":\"" + result + "\",\"attachments\":\"" + attachFiles + "\",\"responsible\":\"----\",\"sentDate\":\"" + t + "\",\"status\":\"wait..\",\"type\":\"E-MAIL\",\"subject\":\"" + mes.getSubject() + "\",\"CC\":\"" + CC + "\",\"BCC\":\"" + BCC + "\",\"messageNum\":\""+mes.getMessageNumber()+"\"}";
//                JsonList.add(json);
           }


            inbox.close(true);
            properties.clear();
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //return JsonList;
    }

    //method ที่ทำหน้าที่ getText content จาก email ออกมา
    public String getTextFromMimeMultipart(Multipart mimeMultipart, int partcount) throws Exception {
        String result = "";
        String saveDirectory="/home/nick/File_mail";
        String fileName = "";
        int partCount = mimeMultipart.getCount();
        for (int i = 0; i < partCount; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break;
            }else if ((bodyPart.isMimeType("image/jpg"))||(bodyPart.isMimeType("image/png"))) {
                MimeBodyPart part = (MimeBodyPart) mimeMultipart.getBodyPart(partcount);
                fileName = part.getFileName();
                part.saveFile(saveDirectory + File.separator + fileName);
                result = result + "\n" + "[-"+fileName+"-]";
                break;
            }else if (bodyPart.isMimeType("text/html")) {
                MimeBodyPart part = (MimeBodyPart) mimeMultipart.getBodyPart(partcount);
            }else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent(), partcount);
            }
        }
        return result;
    }

    public String RandomNumber(){
        String randomNumber;
        Random ran = new Random();
        int n = ran.nextInt(99);
        randomNumber = Integer.toString(n);
        return randomNumber;
    }

}

