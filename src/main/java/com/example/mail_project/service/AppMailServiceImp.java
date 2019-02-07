package com.example.mail_project.service;


import com.example.mail_project.controller.AppReceiveMail;
import com.example.mail_project.controller.CheckThisEmail;
import com.example.mail_project.controller.autoReply2;
import com.example.mail_project.controller.base64_test;
import com.example.mail_project.entity.CustomerLog;
import com.example.mail_project.entity.LineDataImg;
import com.example.mail_project.entity.MasterDataDetail;
import com.example.mail_project.repository.CustomerLogRepository;
import com.example.mail_project.repository.LineDataImgRepository;
import com.example.mail_project.repository.MasterDataDetailRepository;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.sun.mail.imap.IMAPFolder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class AppMailServiceImp implements AppMailDataService {

    @Autowired
    private CustomerLogRepository customerLogRepository;

    @Autowired
    private LineDataImgRepository lineDataImgRepository;

    @Autowired
    private MasterDataDetailRepository masterDataDetailRepository;

    public static List<String> LIST_KEYWORD = new ArrayList<>();
    public static List<String> LIST_PROGRAM = new ArrayList<>();

    @PersistenceContext
  private EntityManager em;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public void SaveByJson(String json) {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss");//ตั้ง gson format เพื่อ save ข้อมูลลง database

        CustomerLog customerLog = gsonBuilder.create().fromJson(json, CustomerLog.class);
        customerLogRepository.save(customerLog);
        LOGGER.info("Save By Json : {}",customerLog.getId());
    }
    @Override
    public void SaveByJsonCus(String json) {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss");//ตั้ง gson format เพื่อ save ข้อมูลลง database

        LOGGER.info("Json : {}",json);
        CustomerLog customerLog = gsonBuilder.create().fromJson(json, CustomerLog.class);
        customerLogRepository.save(customerLog);
        LOGGER.info("Save By Json : {}",customerLog.getId());
    }

    @Override
    public void DeleteByID(Long id) {
        customerLogRepository.deleteById(id);
        LOGGER.info("Delete Success");
    }

    @Override
    public List<CustomerLog> getAllCusData() {
        return customerLogRepository.findAll();
    }

    @Override
    public CustomerLog findByID(Long ID) {
        return customerLogRepository.findById(ID).get();
    }

    @Override
    public List<CustomerLog> getNewData() {
        return null;
    }

    @Override
    public List<CustomerLog> getAllData(String sender) {
        return customerLogRepository.findByFirstNameContainingIgnoreCase(sender);
    }

    @Override
    public CustomerLog EditData(CustomerLog customerLog, Long id) {
        LOGGER.info("Subject : {}", customerLog.getSubject());
        customerLog.setId(id);
        customerLogRepository.save(customerLog);
        LOGGER.info("Edit sent Date: {}", customerLog.getSentDate());
        LOGGER.info("Edit : {}", customerLog.getId());

        return customerLogRepository.findById(id).get();
    }

    @Override
    public List<CustomerLog> findBySender(String sender1) {
        return customerLogRepository.findByFirstNameContainingIgnoreCase(sender1);
    }

    @Override
    public List<CustomerLog> findBySubject(String subject) {
        return customerLogRepository.findBySubjectContainingIgnoreCase(subject);
    }

    @Override
    public List<CustomerLog> findByEmail(String email) {
        return customerLogRepository.findByEmailContainingIgnoreCase(email);
    }

    @Override
    public List<CustomerLog> findByResponsible(String responsible) {
        return customerLogRepository.findByResponsibleContainingIgnoreCase(responsible);
    }

    @Override
    public void SaveLineText(String json) {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");//ตั้ง gson format เพื่อ save ข้อมูลลง database
        CustomerLog customerLog = gsonBuilder.create().fromJson(json, CustomerLog.class);
        customerLogRepository.save(customerLog);
    }

    @Override
    public void SaveLineImg(String json) {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");//ตั้ง gson format เพื่อ save ข้อมูลลง database
        LineDataImg lineDataImg = gsonBuilder.create().fromJson(json, LineDataImg.class);
        lineDataImgRepository.save(lineDataImg);
    }

    @Override
    public List<CustomerLog> findByContent(String content) {
        return customerLogRepository.findByMsgContainingIgnoreCase(content);
    }


    @Override
    public List<CustomerLog> findByLevel(String level) {
        return customerLogRepository.findByLevelContaining(level);
    }

    @Override
    public List<CustomerLog> findByStatus(String status) {
        return customerLogRepository.findByStatusContaining(status);
    }

    @Override
    public List<CustomerLog> findBytype(String type) {
        return customerLogRepository.findBytypeContainingIgnoreCase(type);
    }

    @Override
    public List<LineDataImg> findByIdlineAndImgg(String idline) {
        return lineDataImgRepository.findByIdlineAndImgg(idline);
    }

    @Override
    public List<CustomerLog> findbyAll(String sender, String subject, String email, String responsible, String msg, String status, String level, String type) {
        return customerLogRepository.findBySenderContainingAndSubjectContainingAndEmailContainingAndResponsibleContainingAndMsgContainingAndLevelContainingAndStatusContainingAndTypeContaining(sender,subject,email,responsible,msg,status,level,type);
    }

    @Override
    public List<CustomerLog> findBySenderAndType(String sender, String type) {
        return customerLogRepository.findBySenderContainingAndTypeContaining(sender, type);
    }

    @Override
    public List<CustomerLog> findBySubjectAndType(String subject, String type) {
        return customerLogRepository.findBySenderContainingAndTypeContaining(subject, type);
    }

    @Override
    public List<CustomerLog> findByEmailAndType(String email, String type) {
        return customerLogRepository.findByEmailContainingAndTypeContaining(email, type);
    }

    @Override
    public List<CustomerLog> findByResponsibleAndType(String responsible, String type) {
        return customerLogRepository.findByResponsibleContainingAndTypeContaining(responsible, type);
    }

    @Override
    public List<CustomerLog> findByMsgAndType(String msg, String type) {
        return customerLogRepository.findByMsgContainingAndTypeContaining(msg,type);
    }

    @Override
    public List<CustomerLog> receiveNewMail() {
        String countlevelMax = "";
        String countlevel = "";
        String email_id = "pingge12345678@gmail.com";
        String password = "E12345678";

        CheckThisEmail c = new CheckThisEmail();
        //AppReceiveMail appReceiveMail = new AppReceiveMail();

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
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties, null);
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
                if (!mes.getSubject().contains("Re:")) {
                    System.out.println("Subject: " + mes.getSubject());

                    if (mes.isSet(Flags.Flag.FLAGGED)) {
                        System.out.println("ติดดาว");
                    }else{
                        System.out.println("ไม่ติดดาว");
                    }
                    if (mes.isSet(Flags.Flag.ANSWERED)) {
                        System.out.println("ตอบกลับแล้ว");
                    }else{
                        System.out.println("ยังไม่ตอบกลับ");
                    }

                    String s = mes.getFrom()[0].toString();

                    if (s.contains("=?UTF-8?B?") == true) {
                        base64_test b = new base64_test();
                        s = b.decodeText(s);

                    } else {

                    }
                    System.out.println("From: " + s);

                    String sender = c.splitWord2(s);

                    String email = c.splitWord1(s);


                    String CC = "", BCC = "";

                    if (mes.getRecipients(Message.RecipientType.CC) == null) {

                    } else if (mes.getRecipients(Message.RecipientType.CC) != null) {
                        Address cc = mes.getRecipients(Message.RecipientType.CC)[0];
                        System.out.println("CC : " + cc.toString());
                        CC = cc.toString();
                    }
                    if (mes.getRecipients(Message.RecipientType.BCC) == null) {

                    } else if (mes.getRecipients(Message.RecipientType.BCC) != null) {
                        Address bcc = mes.getRecipients(Message.RecipientType.BCC)[0];
                        System.out.println("BCC : " + bcc.toString());
                        BCC = bcc.toString();
                    }


                    System.out.println("Sent date: " + mes.getSentDate());

                    String contentType = mes.getContentType();

                    String attachFiles = "";
                    String result = "";
                    String saveDirectory = "/home/nick/File_mail";


                    if (contentType.contains("multipart")) {// มีไฟล์ ATTACHMENT
                        Multipart multiPart = (Multipart) mes.getContent();
                        int numberOfParts = multiPart.getCount();
                        //System.out.println(numberOfParts);
                        for (int partCount = 0; partCount < numberOfParts; partCount++) {
                            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                            //messageContent = part.getContent().toString();

                            AppReceiveMail r = new AppReceiveMail();
                            result = r.getTextFromMimeMultipart(multiPart, partCount);//

                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                //messageContent = part.getContent().toString();
                                // this part is attachment

                                String fileName = formatter.format(mes.getSentDate()) + "-" + RandomNumber() + "-" + part.getFileName();//rename วันที่ + ชื่อไฟล์
                                attachFiles += fileName + ", ";
                                System.out.println("Attachments: " + attachFiles);
                                part.saveFile(saveDirectory + File.separator + fileName);

                                System.out.println();
                            } else {
                                //messageContent = part.getContent().toString();
                            }
                            if (part.isMimeType("image/jpeg") || part.isMimeType("image/png")) {

                                System.out.print("image");
                                String fileName = formatter.format(mes.getSentDate()) + "-" + RandomNumber() + "-" + part.getFileName();
                                System.out.println("[" + fileName + "]");

                                File f = new File(saveDirectory + File.separator + fileName);

                                boolean check = new File(saveDirectory + File.separator, fileName).exists();
                                // check directory มีไฟล์ชื่อนี้ไหม


                                if (check == false) {//ถ้าไม่มีไฟล์
                                    System.out.println("Download : " + fileName);
                                    part.saveFile(saveDirectory + File.separator + fileName);//ดาวน์โหลดไฟล์
                                    //base64_test base64 = new base64_test();// เข้ารหัส Base64
                                    //System.out.println("BASE64ENCODER : ["+base64.encoder(f.toString())+"]");

                                } else if (check == true) { //มีไฟล์แล้ว
                                    System.out.println("Have this file");//แสดงข้อความ
                                }

                            }
                        }
                        if (attachFiles.length() > 1) {
                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                        }
                    }
                    System.out.println("Content : " + result);
                    boolean checkL = result.contains("Chitsanu");
                    if (checkL == true) {
                        System.out.println("Hello Chitsanu!");
                    }

                    System.out.println(attachFiles);

                    System.out.println("------------------------------------------------------------");

                    LOGGER.info("Message : {}", result);
                    boolean hasText1 = result.contains("ด่วน");
                    boolean hasText2 = result.contains("ด่วนมาก");
                    List<MasterDataDetail> listKeyword = masterDataDetailRepository.findMasterDataDetailsByIdEquals(new Long("200"), "level.list");
                    LOGGER.info("Conten : {}",listKeyword.size());
                    String resultKeyword = "";
                    List<String> keywordSplitList = null;
                    resultKeyword = listKeyword.get(0).getVariable1();
                    keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
                    countlevelMax = keywordSplitList.get(keywordSplitList.size()-1);
                    int num = Integer.parseInt(countlevelMax) - 1;
                    countlevel = Integer.toString(num);
                    LOGGER.info("level-Max : {} : {}", countlevel,countlevelMax);
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

                    if ((hasText1 == true) && (hasText2 == false)) {
                        customerLog.setLevel(countlevel);
                        customerLogRepository.save(customerLog);
                    } else if ((hasText2 == true) && (hasText1 == true)) {
                        customerLog.setLevel(countlevelMax);
                        customerLogRepository.save(customerLog);
                    } else {
                        customerLog.setLevel("0");
                        customerLogRepository.save(customerLog);
                    }

                }
            }
            //appMailDataService.SaveByJsonCus(json);

            folder.close(true);
            properties.clear();
            store.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    public List<CustomerLog> receiveMail() {
        String countlevelMax = "";
        String countlevel = "";
        String email_id = "pingge12345678@gmail.com";
        String password = "E12345678";


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
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties, null);
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
                if (!mes.getSubject().contains("Re:")) {
                    System.out.println("Subject: " + mes.getSubject());

                    if (mes.isSet(Flags.Flag.FLAGGED)) {
                        System.out.println("ติดดาว");
                    }else{
                        System.out.println("ไม่ติดดาว");
                    }
                    if (mes.isSet(Flags.Flag.ANSWERED)) {
                        System.out.println("ตอบกลับแล้ว");
                    }else{
                        System.out.println("ยังไม่ตอบกลับ");
                    }


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
                    if (mes.getRecipients(Message.RecipientType.CC) == null) {
                        //ถ้าไม่มี
                    } else if (mes.getRecipients(Message.RecipientType.CC) != null) {
                        //ถ้ามี
                        Address cc = mes.getRecipients(Message.RecipientType.CC)[0];
                        System.out.println("CC : " + cc.toString());
                        CC = cc.toString();//แปลงเป็น String
                    }
                    if (mes.getRecipients(Message.RecipientType.BCC) == null) {//ตรวจว่ามี BCC หรือไม่
                        //ถ้าไม่มี
                    } else if (mes.getRecipients(Message.RecipientType.BCC) != null) {//ถ้ามี
                        Address bcc = mes.getRecipients(Message.RecipientType.BCC)[0];
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

                            //ใช้ Method getTextFromMimeMultipart
                            result = getTextFromMimeMultipart(multiPart, partCount);//เอาข้อความจาก email ออกมา
                            //part ที่มีไฟล์ attachment
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {

                                String fileNameDate = formatter.format(mes.getSentDate());
                                String fileName = fileNameDate + "-" + RandomNumber() + "-" + part.getFileName();//rename วันที่ + ชื่อไฟล์
                                attachFiles += fileName + ", ";
                                System.out.println("Attachments: " + attachFiles);
                                part.saveFile(saveDirectory + File.separator + fileName);//save file
                                System.out.println();
                            } else {

                            }
                            //part ที่เป็นประเภท jpeg หรือ png
                            if (part.isMimeType("image/jpeg") || part.isMimeType("image/png")) {

                                System.out.print("image");
                                String fileNameDate = formatter.format(mes.getSentDate());
                                String fileName = fileNameDate + "-" + RandomNumber() + "-" + part.getFileName();
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


                    System.out.println(attachFiles);

                    System.out.println("------------------------------------------------------------");

                    LOGGER.info("Message : {}", result);
                    boolean hasText1 = result.contains("ด่วน");
                    boolean hasText2 = result.contains("ด่วนมาก");
                    List<MasterDataDetail> listKeyword = masterDataDetailRepository.findMasterDataDetailsByIdEquals(new Long("200"), "level.list");
                    LOGGER.info("Conten : {}",listKeyword.size());
                    String resultKeyword = "";
                    List<String> keywordSplitList = null;
                    resultKeyword = listKeyword.get(0).getVariable1();
                    keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
                    countlevelMax = keywordSplitList.get(keywordSplitList.size()-1);
                    int num = Integer.parseInt(countlevelMax) - 1;
                    countlevel = Integer.toString(num);
                    LOGGER.info("level-Max : {} : {}", countlevel,countlevelMax);
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

                    if ((hasText1 == true) && (hasText2 == false)) {
                        customerLog.setLevel("2");
                        customerLogRepository.save(customerLog);
                    } else if ((hasText2 == true) && (hasText1 == true)) {
                        customerLog.setLevel("3");
                        customerLogRepository.save(customerLog);
                    } else {
                        customerLog.setLevel("0");
                        customerLogRepository.save(customerLog);
                    }
//                json = "{\"sender\":\"" + sender + "\",\"send_To\":\"" + email_id + "\",\"email\":\"" + email + "\",\"msg\":\"" + result + "\",\"attachments\":\"" + attachFiles + "\",\"responsible\":\"----\",\"sentDate\":\"" + t + "\",\"status\":\"wait..\",\"type\":\"E-MAIL\",\"subject\":\"" + mes.getSubject() + "\",\"CC\":\"" + CC + "\",\"BCC\":\"" + BCC + "\",\"messageNum\":\""+mes.getMessageNumber()+"\"}";
//                JsonList.add(json);
                }//end if
            }//end loop



            inbox.close(true);
            properties.clear();
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //return JsonList;
        return null;
    }

    @Override
    public List<MasterDataDetail> findByVariable1(Long id, String code) {
        return masterDataDetailRepository.findByVariable1(id, code);
    }

    @Override
    public List<MasterDataDetail> masterDatakey(Long id, String code) {
        List<MasterDataDetail> listKeyword = masterDataDetailRepository.findMasterDataDetailsByIdEquals(id, code);
        LOGGER.info("listKeyword : {}", listKeyword.size());
        int countresult = 0;
        int countKeyword = 0;
        String resultKeyword = "";
        List<String> keywordSplitList = null;
        resultKeyword = listKeyword.get(0).getVariable1();
        LOGGER.info("num : {}", resultKeyword);
        switch(code)
        {
            case "program.list" :{
                LIST_PROGRAM = null;
                LIST_PROGRAM =new ArrayList<>();
                keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
                LOGGER.info("program.list : {}", keywordSplitList);
                for (String progran : keywordSplitList){
                    LIST_PROGRAM.add(progran);
                }
                LOGGER.info("program : {}", LIST_PROGRAM);
            }
//            countKeyword = 0;
            break;
            case "keyword.list" :{
                LIST_KEYWORD = null;
                LIST_KEYWORD =new ArrayList<>();
                keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
                LOGGER.info("Keywor.list : {}", keywordSplitList);
                for (String keyword : keywordSplitList){
                    LIST_KEYWORD.add(keyword);
                }
                LOGGER.info("Keywor : {}", LIST_KEYWORD);
            }
//            countKeyword = 0;
            break;
            default :
                LOGGER.info("countresult : {}", countresult++);
                break;
        }
        LOGGER.info("Keywor : {}", LIST_KEYWORD);
        LOGGER.info("program : {}", LIST_PROGRAM);
//        return masterDataDetailRepository.findMasterDataDetailsByIdEquals(id, code);
        return null;
    }

    @Override
    public List<CustomerLog> findByDate(double startTime, double endTime) {

        Criteria criteria = ((Session)em.getDelegate()).createCriteria(CustomerLog.class,"CustomerLog");

        criteria.add(Restrictions.gt("CustomerLog.sentDate",startTime));

        criteria.add(Restrictions.lt("CustomerLog.sentDate",endTime));
        return criteria.list();
    }

    @Override
    public List<CustomerLog> findCustomerLogByCriteriaSize(String sender, String subject, String email, String responsible, String msg, String status, String level, String type, String startTime, String endTime) {
        Criteria criteria = ((Session)em.getDelegate()).createCriteria(CustomerLog.class,"CustomerLog");

        LOGGER.info("####### findCustomerLogByCriteriaSize : {}");

        System.out.println("test : "+startTime);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if((!"".equals(startTime))&&(!"".equals(endTime))){
            try {

                System.out.println("Date test 0011 **** : "+startTime);

                LOGGER.info("Criteria 2 Service : ");

                String[] starttime = startTime.split("%20");
                String[] endtime = endTime.split("%20");

                LOGGER.info("Date : ",startTime.toString());
                Date startdate = formatter.parse(starttime[0]+" "+starttime[1]);
                Date enddate = formatter.parse(endtime[0]+" "+endtime[1]);
                LOGGER.info("Date : ",startdate.toString());
                System.out.println("StartDate : "+startdate);
                System.out.println("endDate : "+enddate);
                criteria.add(Restrictions.gt("CustomerLog.sentDate",startdate));

                criteria.add(Restrictions.lt("CustomerLog.sentDate",enddate));


            }catch (ParseException e){
                LOGGER.info("Error : ",e);
            }
        }

        //where sender like '%_%'
        if(!"".equals(sender) || sender != null){
            criteria.add(Restrictions.like("CustomerLog.subject","%"+sender+"%"));
        }

        if(!"".equals(subject)|| subject !=null){
            criteria.add(Restrictions.like("CustomerLog.sender","%"+subject+"%"));
        }
        //where msg like '%_%'
        if(!"".equals(msg)|| msg !=null){
            criteria.add(Restrictions.like("CustomerLog.msg","%"+msg+"%"));
        }
        //where email like '%_%'
        if(!"".equals(email)|| email !=null){
            criteria.add(Restrictions.like("CustomerLog.email","%"+email+"%"));
        }
        //where type like '%_%'
        if(!"".equals(type)|| type !=null){
            criteria.add(Restrictions.like("CustomerLog.type","%"+type+"%"));
        }
        //where status = 'wait..'
        if(!"".equals(status)|| status !=null){
            criteria.add(Restrictions.like("CustomerLog.status","%"+status+"%"));
        }
        //where level ='0'
        if(!"".equals(level)|| level !=null){
            criteria.add(Restrictions.like("CustomerLog.level","%"+level+"%"));
        }
        //where responsible like '_%'
        if(!"".equals(responsible)|| responsible !=null){
            criteria.add(Restrictions.like("CustomerLog.responsible",responsible+"%"));
        }

        //criteria.add(Restrictions.lt("CustomerLog.responsible",responsible+"%"));
//        List<CustomerLog> list = criteria.list();
//
//        LOGGER.info("list : ",list);
//
//        return list;

        return criteria.list();
    }

    @Override
    public List<CustomerLog> findCustomerLogByCriteria(String sender,
                                                       String subject,
                                                       String email,
                                                       String msg,
                                                       String responsible,
                                                       String status,
                                                       String level,
                                                       String type,
                                                       String startTime,
                                                       String endTime,
                                                       int firstResult,
                                                       int maxResult) {
        LOGGER.info("####### findCustomerLogByCriteria : {}");
        /*...select * from customLog...*/
        Criteria criteria = ((Session)em.getDelegate()).createCriteria(CustomerLog.class,"CustomerLog").setFirstResult(firstResult).setMaxResults(maxResult).addOrder(Order.desc("id"));
        //SELECT * FROM customer_log order by id desc
        /*...where subject like %_%...*/
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if((!"".equals(startTime))&&(!"".equals(endTime))){
            try {

                System.out.println("Date test 0011 **** : "+startTime);

                LOGGER.info("Criteria 2 Service : ");

                Date startdate;
                Date enddate;
                if (startTime.contains("%20")){
                    String[] starttime = startTime.split("%20");
                    String[] endtime = endTime.split("%20");
                    startdate = formatter.parse(starttime[0]+" "+starttime[1]);
                    enddate = formatter.parse(endtime[0]+" "+endtime[1]);
                }else {
                    startdate = formatter.parse(startTime);
                    enddate = formatter.parse(endTime);
                }

                criteria.add(Restrictions.gt("CustomerLog.sentDate",startdate));

                criteria.add(Restrictions.lt("CustomerLog.sentDate",enddate));


            }catch (ParseException e){
                LOGGER.info("Error : ",e);
            }
        }

        if(!"".equals(sender) || sender != null){
            criteria.add(Restrictions.like("CustomerLog.subject","%"+sender+"%"));
        }

        if(!"".equals(subject)|| subject !=null){
            criteria.add(Restrictions.like("CustomerLog.sender","%"+subject+"%"));
        }
        //where msg like '%_%'
        if(!"".equals(msg)|| msg !=null){
            criteria.add(Restrictions.like("CustomerLog.msg","%"+msg+"%"));
        }
        //where email like '%_%'
        if(!"".equals(email)|| email !=null){
            criteria.add(Restrictions.like("CustomerLog.email","%"+email+"%"));
        }
        //where type like '%_%'
        if(!"".equals(type)|| type !=null){
            criteria.add(Restrictions.like("CustomerLog.type","%"+type+"%"));
        }
        //where status = 'wait..'
        if(!"".equals(status)|| status !=null){
            criteria.add(Restrictions.like("CustomerLog.status","%"+status+"%"));
        }
        //where level ='0'
        if(!"".equals(level)|| level !=null){
            criteria.add(Restrictions.like("CustomerLog.level","%"+level+"%"));
        }
        //where responsible like '_%'
        if(!"".equals(responsible)|| responsible !=null){
            criteria.add(Restrictions.like("CustomerLog.responsible",responsible+"%"));
        }

        //criteria.add(Restrictions.lt("CustomerLog.responsible",responsible+"%"));
        List<CustomerLog> list = criteria.list();

        List<CustomerLog> resultList = new ArrayList<>();
        for(CustomerLog customerLog : list){
            String strSubject = customerLog.getSubject();
            strSubject  = strSubject.replaceAll("'", "\\\\'");
            customerLog.setSubject(strSubject);

            String strMsg = customerLog.getMsg();
            strMsg  = strMsg.replaceAll("'", "\\\\'");
            customerLog.setMsg(strMsg);

            resultList.add(customerLog);
        }

        return resultList;
        //return criteria.list();
    }
    @Override
    public List<CustomerLog> findCustomerLogByCriteria2(String startTime,String endTime){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Criteria criteria = ((Session)em.getDelegate()).createCriteria(CustomerLog.class,"CustomerLog");
        LOGGER.info("Date : ");
        /*...select * from customLog...*/
        try {
            LOGGER.info("Criteria 2 Service : ");

            String[] starttime = startTime.split("%20");
            String[] endtime = endTime.split("%20");

            LOGGER.info("Date : ",startTime.toString());
            Date startdate = formatter.parse(starttime[0]+" "+starttime[1]);
            Date enddate = formatter.parse(endtime[0]+" "+endtime[1]);
            LOGGER.info("Date : ",startdate.toString());

            criteria.add(Restrictions.gt("CustomerLog.sentDate",startdate));

            criteria.add(Restrictions.lt("CustomerLog.sentDate",enddate));


        }catch (ParseException e){
            LOGGER.info("Error : ",e);
        }


        /*...where subject like %_%...*/


        //where sender like '%_%'
        //criteria.add(Restrictions.lt("CustomerLog.sentDate",date2));


        return criteria.list();
    }

    @Override
    public List<MasterDataDetail> getAllmaster() {
        return masterDataDetailRepository.findAll();
    }

    public static boolean checkTextMatches(String str) {
        int countKeyword = 0;
        int countProgram = 0;

        for (String keyword : LIST_KEYWORD) {
            if (str.indexOf(keyword) >= 0) {
                countKeyword++;
            }
        }
        for (String keyword : LIST_PROGRAM) {
            if (str.indexOf(keyword) >= 0) {
                countProgram++;
                break;
            }
        }
        if (countKeyword != 0 && countProgram != 0) {
            return true;

        } else {
            return false;
        }
    }

    public String RandomNumber(){
        String randomNumber;
        Random ran = new Random();
        int n = ran.nextInt(99);
        randomNumber = Integer.toString(n);
        return randomNumber;
    }

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

}
