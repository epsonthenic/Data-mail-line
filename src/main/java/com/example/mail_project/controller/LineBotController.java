package com.example.mail_project.controller;

import com.example.mail_project.MailProjectApplication;
import com.example.mail_project.entity.CustomerLog;
import com.example.mail_project.entity.MasterDataDetail;
import com.example.mail_project.repository.CustomerLogRepository;
import com.example.mail_project.repository.MasterDataDetailRepository;
import com.example.mail_project.service.AppMailDataService;
import com.example.mail_project.service.AppMailServiceImp;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.common.io.ByteStreams;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import lombok.Value;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@LineMessageHandler
public class LineBotController {

    public String sender_Totext = "";
    public String Subjecttext = "";
    public String msgtext = "";
    public String imgline = "";
    public String countlevelMax = null;
    public String countlevel = null;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private AppMailDataService appMailDataService;

    @Autowired
    private MasterDataDetailRepository masterDataDetailRepository;

    @Autowired
    private AppMailDataController appMailDataController;

    @Autowired
    private CustomerLogRepository customerLogRepository;


    @EventMapping //รับข้อความ
    public void handleTextMessage(MessageEvent<TextMessageContent> event) {
        TextMessageContent message = event.getMessage();
        String replyToken = event.getReplyToken();
        String userId = event.getSource().getUserId();
        String text = message.getText();
        DateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        boolean hasText = text.contains("@");
        boolean hasText1 = text.contains("ด่วน");
        boolean hasText2 = text.contains("ด่วนมาก");
        List<MasterDataDetail> listKeyword = masterDataDetailRepository.findMasterDataDetailsByIdEquals(new Long("200"), "level.list");
        String resultKeyword = "";
        List<String> keywordSplitList = null;
        resultKeyword = listKeyword.get(0).getVariable1();
        keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
        countlevelMax = keywordSplitList.get(keywordSplitList.size()-1);
        int num = Integer.parseInt(countlevelMax) - 1;
        countlevel = Integer.toString(num);
        LOGGER.info("level-Max : {} : {}", countlevel,countlevelMax);
//        int countresult = 0;
//        String[] resultKeyword = new String[100];
//        String[] keywordSplitList = new String[100];
//        for (String result : resultKeyword) {
//            resultKeyword[countresult] = String.valueOf(listKeyword.get(countresult));
//            keywordSplitList = resultKeyword[countresult].split(",");
//        }
//        for (String level : keywordSplitList){
////            LOGGER.info("level : {} : {}", level,num);
//            countlevelMax = level;
//        }
//        int result = Integer.parseInt(countlevelMax) - 1;
//        countlevel = Integer.toString(result);
//        LOGGER.info("level-Max : {} : {}", countlevel,countlevelMax);
        String pattern = "(@*+\\S+)(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if ((AppMailServiceImp.checkTextMatches(text) == true) && (hasText == false)) {
            if (userId != null) {
                if ((hasText1 == true) && (hasText2 == false)) {
                    reply(replyToken, Arrays.asList(
                            new TextMessage("ระบบได้ทำการบันทึกแล้ว")
                    ));
                    CustomerLog customerLog = new CustomerLog();
                    customerLog.setSender("-");
                    customerLog.setSend_To("SS");
                    customerLog.setEmail("-");
                    customerLog.setMsg(text);
                    customerLog.setAttachments("-");
                    customerLog.setResponsible("----");
                    customerLog.setSentDate(now);
                    customerLog.setStatus("wait..");
                    customerLog.setType("LINE");
                    customerLog.setSubject(text);
                    customerLog.setCC("-");
                    customerLog.setBCC("-");
                    customerLog.setLevel(countlevel);
                    customerLog.setIdline(userId);
                    customerLogRepository.save(customerLog);
//                    String json = "{\"sender\" : \"-\", \"send_To\" : \"SS\", \"subject\" : \"" + text + "\" , \"email\" : \"-\", \"msg\" : \"" + text + "\", \"attachments\" : \"-\", \"responsible\" : \"-\", \"sentDate\" : \"" + strDate + "\", \"status\" : \"wait..\", \"type\" : \"LINE\", \"level\" : \"2\", \"CC\" : \"-\", \"BCC\" : \"-\", \"idline\" : \"" + userId + "\"}";
//                    //String json = "{ \"send_To\" : \"SS\",\"msg\" : \"" + text + "\" , \"sentDate\" : \""+strDate+"\",\"idline\":\""+ userId +"\",\"type\":\"LINE\",\"status\":\"wait\"}";
//                    appMailDataService.SaveByJsonCus(json);
                } else if ((hasText2 == true) && (hasText1 == true)) {
                    reply(replyToken, Arrays.asList(
                            new TextMessage("ระบบได้ทำการบันทึกแล้ว")
                    ));
                    CustomerLog customerLog = new CustomerLog();
                    customerLog.setSender("-");
                    customerLog.setSend_To("SS");
                    customerLog.setEmail("-");
                    customerLog.setMsg(text);
                    customerLog.setAttachments("-");
                    customerLog.setResponsible("----");
                    customerLog.setSentDate(now);
                    customerLog.setStatus("wait..");
                    customerLog.setType("LINE");
                    customerLog.setSubject(text);
                    customerLog.setCC("-");
                    customerLog.setBCC("-");
                    customerLog.setLevel(countlevelMax);
                    customerLog.setIdline(userId);
                    customerLogRepository.save(customerLog);
//                    String json = "{\"sender\" : \"-\", \"send_To\" : \"SS\", \"subject\" : \"" + text + "\" , \"email\" : \"-\", \"msg\" : \"" + text + "\", \"attachments\" : \"-\", \"responsible\" : \"-\", \"sentDate\" : \"" + strDate + "\", \"status\" : \"wait..\", \"type\" : \"LINE\", \"level\" : \"3\", \"CC\" : \"-\", \"BCC\" : \"-\", \"idline\" : \"" + userId + "\"}";
//                    //String json = "{ \"send_To\" : \"SS\",\"msg\" : \"" + text + "\" , \"sentDate\" : \""+strDate+"\",\"idline\":\""+ userId +"\",\"type\":\"LINE\",\"status\":\"wait\"}";
//                    appMailDataService.SaveByJsonCus(json);
                } else {
                    reply(replyToken, Arrays.asList(
                            new TextMessage("ระบบได้ทำการบันทึกแล้ว")
                    ));
//                    String json = "{\"sender\" : \"-\", \"send_To\" : \"SS\", \"subject\" : \"" + text + "\" , \"email\" : \"-\", \"msg\" : \"" + text + "\", \"attachments\" : \"-\", \"responsible\" : \"-\", \"sentDate\" : \"" + strDate + "\", \"status\" : \"wait..\", \"type\" : \"LINE\", \"level\" : \"0\", \"CC\" : \"-\", \"BCC\" : \"-\", \"idline\" : \"" + userId + "\"}";
                    CustomerLog customerLog = new CustomerLog();
                    customerLog.setSender("-");
                    customerLog.setSend_To("SS");
                    customerLog.setEmail("-");
                    customerLog.setMsg(text);
                    customerLog.setAttachments("-");
                    customerLog.setResponsible("----");
                    customerLog.setSentDate(now);
                    customerLog.setStatus("wait..");
                    customerLog.setType("LINE");
                    customerLog.setSubject(text);
                    customerLog.setCC("-");
                    customerLog.setBCC("-");
                    customerLog.setLevel("0");
                    customerLog.setIdline(userId);
                    customerLogRepository.save(customerLog);
//                    appMailDataService.SaveByJsonCus(json);
                }
            }
        } else if ((hasText == true) && (AppMailServiceImp.checkTextMatches(text) == true)) {
            if (userId != null) {
                if ((hasText1 == true) && (hasText2 == false)) {
                    if (m.find()) {
                        sender_Totext = m.group(1);
                        msgtext = m.group(2);
                        lineMessagingClient.getProfile(userId)
                                .whenComplete((profile, throwable) -> {
                                    reply(replyToken, Arrays.asList(
                                            new TextMessage("ระบบได้ทำการบันทึกแล้ว")
                                    ));
//                                    String json = "{\"sender\" : \"-\", \"send_To\" : \"" + sender_Totext + "\", \"subject\" : \"" + msgtext + "\" , \"email\" : \"-\", \"msg\" : \"" + msgtext + "\", \"attachments\" : \"-\", \"responsible\" : \"-\", \"sentDate\" : \"" + strDate + "\", \"status\" : \"wait..\", \"type\" : \"LINE\", \"level\" : \"2\", \"CC\" : \"-\", \"BCC\" : \"-\", \"idline\" : \"" + userId + "\"}";
                                    CustomerLog customerLog = new CustomerLog();
                                    customerLog.setSender("-");
                                    customerLog.setSend_To(sender_Totext);
                                    customerLog.setEmail("-");
                                    customerLog.setMsg(msgtext);
                                    customerLog.setAttachments("-");
                                    customerLog.setResponsible("----");
                                    customerLog.setSentDate(now);
                                    customerLog.setStatus("wait..");
                                    customerLog.setType("LINE");
                                    customerLog.setSubject(msgtext);
                                    customerLog.setCC("-");
                                    customerLog.setBCC("-");
                                    customerLog.setLevel(countlevel);
                                    customerLog.setIdline(userId);
                                    customerLogRepository.save(customerLog);
                                    //String json = "{\"sender\" : \" \" , \"send_To\" : \"" + sender_Totext + "\", \"subject\" : \"" + Subjecttext + "\" , \"email\":\"null\", \"msg\" : \"" + msgtext + "\" , \"sentDate\" : \"" + strDate + "\" , \"status\" : \"wait\" , \"type\" : \"LINE\" , \"level\":\"null\", \"idline\" : \"" + userId + "\"}";
//                                    appMailDataService.SaveByJsonCus(json);
                                });
                    }
                } else if ((hasText2 == true) && (hasText1 == true)) {
                    if (m.find()) {
                        sender_Totext = m.group(1);
                        msgtext = m.group(2);
                        lineMessagingClient.getProfile(userId)
                                .whenComplete((profile, throwable) -> {
                                    reply(replyToken, Arrays.asList(
                                            new TextMessage("ระบบได้ทำการบันทึกแล้ว")
                                    ));
                                    CustomerLog customerLog = new CustomerLog();
                                    customerLog.setSender("-");
                                    customerLog.setSend_To(sender_Totext);
                                    customerLog.setEmail("-");
                                    customerLog.setMsg(msgtext);
                                    customerLog.setAttachments("-");
                                    customerLog.setResponsible("----");
                                    customerLog.setSentDate(now);
                                    customerLog.setStatus("wait..");
                                    customerLog.setType("LINE");
                                    customerLog.setSubject(msgtext);
                                    customerLog.setCC("-");
                                    customerLog.setBCC("-");
                                    customerLog.setLevel(countlevelMax);
                                    customerLog.setIdline(userId);
                                    customerLogRepository.save(customerLog);
//                                    String json = "{\"sender\" : \"-\", \"send_To\" : \"" + sender_Totext + "\", \"subject\" : \"" + msgtext + "\" , \"email\" : \"-\", \"msg\" : \"" + msgtext + "\", \"attachments\" : \"-\", \"responsible\" : \"-\", \"sentDate\" : \"" + strDate + "\", \"status\" : \"wait..\", \"type\" : \"LINE\", \"level\" : \"3\", \"CC\" : \"-\", \"BCC\" : \"-\", \"idline\" : \"" + userId + "\"}";
//                                    //String json = "{\"sender\" : \" \" , \"send_To\" : \"" + sender_Totext + "\", \"subject\" : \"" + Subjecttext + "\" , \"email\":\"null\", \"msg\" : \"" + msgtext + "\" , \"sentDate\" : \"" + strDate + "\" , \"status\" : \"wait\" , \"type\" : \"LINE\" , \"level\":\"null\", \"idline\" : \"" + userId + "\"}";
//                                    appMailDataService.SaveByJsonCus(json);
                                });
                    }
                } else {
                    if (m.find()) {
                        sender_Totext = m.group(1);
                        msgtext = m.group(2);
                        lineMessagingClient.getProfile(userId)
                                .whenComplete((profile, throwable) -> {
                                    reply(replyToken, Arrays.asList(
                                            new TextMessage("ระบบได้ทำการบันทึกแล้ว")
                                    ));
                                    CustomerLog customerLog = new CustomerLog();
                                    customerLog.setSender("-");
                                    customerLog.setSend_To(sender_Totext);
                                    customerLog.setEmail("-");
                                    customerLog.setMsg(msgtext);
                                    customerLog.setAttachments("-");
                                    customerLog.setResponsible("----");
                                    customerLog.setSentDate(now);
                                    customerLog.setStatus("wait..");
                                    customerLog.setType("LINE");
                                    customerLog.setSubject(msgtext);
                                    customerLog.setCC("-");
                                    customerLog.setBCC("-");
                                    customerLog.setLevel("0");
                                    customerLog.setIdline(userId);
                                    customerLogRepository.save(customerLog);
//                                    String json = "{\"sender\" : \"-\", \"send_To\" : \"" + sender_Totext + "\", \"subject\" : \"" + msgtext + "\" , \"email\" : \"-\", \"msg\" : \"" + msgtext + "\", \"attachments\" : \"-\", \"responsible\" : \"-\", \"sentDate\" : \"" + strDate + "\", \"status\" : \"wait..\", \"type\" : \"LINE\", \"level\" : \"0\", \"CC\" : \"-\", \"BCC\" : \"-\", \"idline\" : \"" + userId + "\"}";
//                                    //String json = "{\"sender\" : \" \" , \"send_To\" : \"" + sender_Totext + "\", \"subject\" : \"" + Subjecttext + "\" , \"email\":\"null\", \"msg\" : \"" + msgtext + "\" , \"sentDate\" : \"" + strDate + "\" , \"status\" : \"wait\" , \"type\" : \"LINE\" , \"level\":\"null\", \"idline\" : \"" + userId + "\"}";
//                                    appMailDataService.SaveByJsonCus(json);
                                });
                    }
                }
            }
        }
    }

    /////////////////////////////รูปภาพ////////////////////////////////
    @EventMapping // รับส่งรูปภาพ
    public void handleImageMessage(MessageEvent<ImageMessageContent> event) {
        ImageMessageContent message = event.getMessage();
        String replyToken = event.getReplyToken();
        String userId = event.getSource().getUserId();
        DateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        String[] resultKeyword = new String[100];
        List<MasterDataDetail> listKeyword = appMailDataController.masterDatakey(new Long("300"), "onimgline.list");
        resultKeyword[0] = String.valueOf(listKeyword.get(0));
        LOGGER.info("AppMailServiceImp {}", resultKeyword[0]);
        switch (resultKeyword[0]) {
            case "Y": {
                try {
                    MessageContentResponse response = lineMessagingClient.getMessageContent(
                            message.getId()).get();
                    DownloadedContent jpg = saveContent("jpg", response);
                    String pattern = "(.*downloaded/)(.*)";
                    Pattern r = Pattern.compile(pattern);
                    Matcher m = r.matcher(jpg.getUri());
                    if (userId != null) {
                        if (m.find()) {
                            //Subjecttext = m.group();
                            imgline = m.group(2);
                            lineMessagingClient.getProfile(userId)
                                    .whenComplete((profile, throwable) -> {
                                        reply(replyToken, Arrays.asList(
                                                new TextMessage("บันทึกแล้ว")
                                        ));
                                        String jsonn = "{ \"imgg\" : \"" + imgline + "\",\"idline\":\"" + userId + "\",\"sentDate\":\"" + strDate + "\"}";
                                        appMailDataService.SaveLineImg(jsonn);
                                    });
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    reply(replyToken, new TextMessage("กรุณาลองใหม่อีกครั้ง"));
                    throw new RuntimeException(e);
                }
            }
            break;
            default:
                break;
        }
    }


    private static DownloadedContent saveContent(String ext,
                                                 MessageContentResponse response) {
        DownloadedContent tempFile = createTempFile(ext);
        try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
            ByteStreams.copy(response.getStream(), outputStream);
            return tempFile;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static DownloadedContent createTempFile(String ext) {
        String fileName = LocalDateTime.now() + "@"
                + UUID.randomUUID().toString()
                + "." + ext;
        Path tempFile = MailProjectApplication.downloadedContentDir.resolve(fileName);
        tempFile.toFile().deleteOnExit();
        return new DownloadedContent(tempFile, createUri("/downloaded/" + tempFile.getFileName()));
    }

    private static String createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path).toUriString();
    }

    @Value
    public static class DownloadedContent {

        Path path;
        String uri;
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse response = lineMessagingClient.replyMessage(
                    new ReplyMessage(replyToken, messages)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
