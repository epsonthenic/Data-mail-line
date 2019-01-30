package com.example.mail_project.controller;

import com.example.mail_project.repository.CustomerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckThisEmail {

    @Autowired
    private CustomerLogRepository customerLogRepository;
    public  static  void main(String[] args){




        CheckThisEmail checkThisEmail = new CheckThisEmail();

        checkThisEmail.checkEmail("jkj@outlook.co.th");

        //AppReceiveNewMail an = new AppReceiveNewMail();
        //an.receiveNewMail();

            //autoReply2 a = new autoReply2();
            //a.autoReply();

        AppReceiveMail app = new AppReceiveMail();
        app.receiveMail().get(1);


        //String json = "{\"sender\":\"Chisanu\",\"send_To\":\"20\"}";

        //String str1 = "ชิษณุ  บุญคุณ <jkj@outlook.co.th>";

        //System.out.println(checkThisEmail.splitWord2(str1));

       // System.out.println(checkThisEmail.splitWord1(str1));



    }
    public void checkEmail(String email){

        boolean str1 = email.contains("@");

        boolean str2 = email.contains(".");

        if ((str1==true)&&(str2==true)){

            String[] s= email.split("@");

            int sI = s[1].indexOf(".");

            //System.out.println(sI+" "+s[1].length());

            if((sI!=0)&&(sI!=s[1].length()-1)){
                System.out.println(email+" this is e-mail");
            }else {
                System.out.println("Not E-mail");
            }

        }else if ((str1==false)||(str2==false)){
            System.out.println("Not E-mail");
        }


    }
    public String splitWord1(String str) {
        boolean str1 = str.contains("<");

        String str2 ;

        if (str1 == true) {
            String[] s = str.split("<");
            str = s[1].replace(">", "");

        }

        return str;
    }
    public String splitWord2(String str) {
        boolean str1 = str.contains("<");

        String str2 ;

        if (str1 == true) {
            String[] s = str.split("<");
            str = s[0];

        }
        return str;
    }
}
