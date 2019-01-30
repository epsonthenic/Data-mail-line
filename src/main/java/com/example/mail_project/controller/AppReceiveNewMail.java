package com.example.mail_project.controller;

import com.example.mail_project.service.AppMailDataService;
import com.sun.mail.imap.IMAPFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class AppReceiveNewMail {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    String email_id = "pingge12345678@gmail.com";
    String password = "E12345678";

    @Autowired
    private AppMailDataService appMailDataService;


    public List<String> receiveNewMail(){


        List<String> JsonList = new LinkedList<String>();
        String json="";

        CheckThisEmail c = new CheckThisEmail();
        AppReceiveMail appReceiveMail = new AppReceiveMail();

        autoReply2 a = new autoReply2();
        a.autoReply(false);

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            //set properties
            Properties properties = new Properties();
            //You can use imap or imaps , *s -Secured
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
            Folder folder = store.getFolder("inbox");
            IMAPFolder pfolder = null;
            if (folder instanceof IMAPFolder){
                pfolder = (IMAPFolder) folder;
            }else{
                System.out.println("NOT AN INSTANCE OF imaps Folder");
            }
            folder.open(Folder.READ_WRITE);

            //เอาเฉพาะ email ที่ยังไม่อ่าน
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message mess[]= folder.search(unseenFlagTerm);

            Message messages[] = folder.getMessages();

            //Inbox email count
            System.out.println("Total Messages in INBOX:- " + mess.length);//จำนวนไฟล์ที่ยังไม่อ่าน
            System.out.println("------------------------------------------------------------");


            for (int i = 0; i <mess.length; i++) {
                Message mes = mess[i];

                System.out.println("Subject: "+mes.getSubject());

                String s = mes.getFrom()[0].toString();

                if (s.contains("=?UTF-8?B?")==true){
                    base64_test b = new base64_test();
                    s=b.decodeText(s);

                }else {

                }
                System.out.println("From: "+s);

                String sender = c.splitWord2(s);

                String emil = c.splitWord1(s);


                String CC ="",BCC="";

                if(mes.getRecipients(RecipientType.CC)==null){

                }
                else if(mes.getRecipients(RecipientType.CC)!=null){
                    Address cc = mes.getRecipients(RecipientType.CC)[0];
                    System.out.println("CC : "+cc.toString());
                    CC = cc.toString();
                }
                if(mes.getRecipients(RecipientType.BCC)==null){

                }
                else if(mes.getRecipients(RecipientType.BCC)!=null){
                    Address bcc = mes.getRecipients(RecipientType.BCC)[0];
                    System.out.println("BCC : "+bcc.toString());
                    BCC = bcc.toString();
                }


                System.out.println("Sent date: "+mes.getSentDate());

                String contentType = mes.getContentType();

                String attachFiles = "";
                String result="";
                String saveDirectory = "/home/pang/File_mail";



                if (contentType.contains("multipart")) {// มีไฟล์ ATTACHMENT
                    Multipart multiPart = (Multipart) mes.getContent();
                    int numberOfParts = multiPart.getCount();
                    //System.out.println(numberOfParts);
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        //messageContent = part.getContent().toString();

                        AppReceiveMail r = new AppReceiveMail();
                        result = r.getTextFromMimeMultipart(multiPart,partCount);//

                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            //messageContent = part.getContent().toString();
                            // this part is attachment

                            String fileName = formatter.format(mes.getSentDate()) + "-"+appReceiveMail.RandomNumber()+"-"+ part.getFileName();//rename วันที่ + ชื่อไฟล์
                            attachFiles += fileName + ", ";
                            System.out.println("Attachments: " +attachFiles);
                            part.saveFile(saveDirectory + File.separator + fileName);

                            System.out.println();
                        }else {
                            //messageContent = part.getContent().toString();
                        }
                        if(part.isMimeType("image/jpeg")||part.isMimeType("image/png")) {

                            System.out.print("image");
                            String fileName = formatter.format(mes.getSentDate()) + "-"+appReceiveMail.RandomNumber()+"-"+ part.getFileName();
                            System.out.println("["+fileName+"]");

                            File f = new File(saveDirectory + File.separator + fileName);

                            boolean check = new File(saveDirectory + File.separator,fileName).exists();
                            // check directory มีไฟล์ชื่อนี้ไหม


                            if(check==false){//ถ้าไม่มีไฟล์
                                System.out.println("Download : "+fileName);
                                part.saveFile(saveDirectory + File.separator + fileName);//ดาวน์โหลดไฟล์
                                //base64_test base64 = new base64_test();// เข้ารหัส Base64
                                //System.out.println("BASE64ENCODER : ["+base64.encoder(f.toString())+"]");

                            }else if (check==true){ //มีไฟล์แล้ว
                                System.out.println("Have this file");//แสดงข้อความ
                            }

                        }
                    }
                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                }
                System.out.println("Content : "+result);
                boolean checkL = result.contains("Chitsanu");
                if(checkL==true){
                    System.out.println("Hello Chitsanu!");
                }

                System.out.println(attachFiles);
                System.out.println(json);

                System.out.println("------------------------------------------------------------");

                Timestamp timestamp = new java.sql.Timestamp(mes.getSentDate().getTime());

                json = "{\"sender\":\""+sender+"\",\"send_To\":\""+email_id+"\",\"email\":\""+emil+"\",\"msg\":\""+result+"\",\"attachments\":\""+attachFiles+"\",\"responsible\":\"----\",\"sentDate\":\""+timestamp.getTime()+"\",\"status\":\"wait..\",\"type\":\"email\",\"subject\":\""+mes.getSubject()+"\",\"CC\":\""+CC+"\",\"BCC\":\""+BCC+"\"}";

                JsonList.add(json);
            }


            //appMailDataService.SaveByJsonCus(json);

            folder.close(true);
            properties.clear();
            store.close();



        } catch (Exception e) {
            e.printStackTrace();
        }

        return JsonList;
    }

}

