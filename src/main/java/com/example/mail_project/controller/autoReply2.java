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

public class autoReply2 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateInString = "07/06/2013 00:00:00";
        Date date = formatter.parse(dateInString);

        System.out.println(date);
        System.out.println(formatter.format(date));
    }
    public void autoReply(boolean Seen){
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


            Folder folder = store.getFolder("inbox");
            IMAPFolder pfolder = null;
            if (folder instanceof IMAPFolder){
                pfolder = (IMAPFolder) folder;
            }else{
                System.out.println("NOT AN INSTANCE OF imaps Folder");
            }
            folder.open(Folder.READ_WRITE);

            //email ที่ยังไม่ถูกเห็น
            Flags seen = new Flags(Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flag.SEEN), false);
            Message mes[]= folder.search(unseenFlagTerm);//เก็บ email ที่ยังไม่ได้อ่าน ลง mes

            Message[] message = pfolder.getMessages();
            System.out.println("UNREAD MESSAGES : " + folder.getUnreadMessageCount());
            for (int i = 0; i < mes.length; i++) {
                Message mess = mes[i];
                System.out.println("IS SEEN : " + message[i].isSet(Flag.SEEN));
                System.out.println("------------ Message " + (i + 1) + " ------------");
                System.out.println("SentDate : " + mess.getSentDate());
                System.out.println("From : " + mess.getFrom()[0]);
                System.out.println("Subject : " + mess.getSubject());


                String to = InternetAddress.toString(mess.getRecipients(Message.RecipientType.TO));
                String CC = InternetAddress.toString(mess.getRecipients(Message.RecipientType.CC));

                InputStream stream = message[i].getInputStream();
                while (stream.available() != 0) {
                    System.out.print((char) stream.read());
                }
                //set autoReply
                mes[i].setFlag(Flag.SEEN, Seen);
                Message replyMessage = new MimeMessage(session);
                replyMessage = (MimeMessage) mess.reply(false);
                replyMessage.setText("Thanks you. Test Auto Reply.");//ตั้งค่าข้อความที่จะ reply
                replyMessage.setReplyTo(mess.getReplyTo());
                System.out.println(to);
                System.out.println(CC);

                //ตอบกลับ email ที่ถูก CC มา
                if(mess.getRecipients(Message.RecipientType.CC)!=null) {//ถ้าไม่มี CC
                    System.out.println("CC has been");
                    replyMessage.setFrom(new InternetAddress(to));
                    replyMessage.addRecipient(Message.RecipientType.CC,new InternetAddress(CC));

                }else if (mess.getRecipients(Message.RecipientType.CC)==null){//ถ้ามี CC
                    System.out.println("CC not has been");
                    replyMessage.setFrom(new InternetAddress(to));

                }

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
            }
            folder.close(true);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
