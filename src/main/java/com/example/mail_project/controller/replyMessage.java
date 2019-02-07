package com.example.mail_project.controller;

import com.sun.mail.imap.IMAPFolder;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class replyMessage {
    public void reply(int messageNum){
        try {
            String host = "pop.gmail.com";
            String user = "pingge12345678@gmail.com";
            String password = "E12345678";

            //Properties properties1 = System.getProperties();
            Properties properties1 = new Properties();
            properties1.clear();

            properties1.put("mail.store.protocol", "imaps");
            //Host Address of Your Mail
            properties1.put("mail.imaps.host", "imap.gmail.com");
            //Port number of your Mail Host
            properties1.put("mail.imaps.port", "993");


            properties1.put("mail.smtp.auth", "true");
            properties1.put("mail.smtp.starttls.enable", "true");
            properties1.put("mail.smtp.host", "relay.jangosmtp.net");
            properties1.put("mail.smtp.port", "25");

            Session session = Session.getDefaultInstance(properties1, null);

            Store store = session.getStore("imaps");

            store.connect(host,user, password);


            //Get inbox folder
            Folder inbox = store.getFolder("inbox");
            //SET readonly format (*You can set read and write)
            inbox.open(Folder.READ_WRITE);

            Message mes[] = inbox.getMessages();

            int i = messageNum-1;
                Message mess = mes[i];
                System.out.println("IS SEEN : " + mes[i].isSet(Flag.SEEN));
                System.out.println("------------ Message " + (i + 1) + " ------------");
                System.out.println("SentDate : " + mess.getSentDate());
                System.out.println("From : " + mess.getFrom()[0]);
                System.out.println("Subject : " + mess.getSubject());


                //set autoReply
                mes[i].setFlag(Flag.SEEN, true);
                Message replyMessage = new MimeMessage(session);
                replyMessage = (MimeMessage) mess.reply(false);
                replyMessage.setText("Thanks you. Test Auto Reply.");//ตั้งค่าข้อความที่จะ reply
                replyMessage.setReplyTo(mess.getReplyTo());

                System.out.println("No : "+mess.getMessageNumber());


                //reply
                Transport t = session.getTransport("smtp");
                try {
                    t.connect(host,user, password);
                    System.out.println("connected...");
                    t.sendMessage(replyMessage,replyMessage.getAllRecipients());

                }catch (Exception ex){
                    System.out.println("Error------------>"+ex.getMessage());
                }finally {
                    t.close();
                }
                System.out.println("message replied successfully ....");

            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
