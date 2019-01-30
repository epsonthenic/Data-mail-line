package com.example.mail_project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;


public class base64_test {

    public String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }
    public String decodeText(String str){

        String[] s = str.split(" ");
        //System.out.println(s[0]);
        //System.out.println(s[1]);
        //System.out.println(str);
        s[0]=s[0].replace("=?UTF-8?B?","");
        String str2=s[0].replace("==?=","");
        //System.out.println(str);
        String encodedString = Base64.getEncoder().encodeToString(str.getBytes());

        byte[] decodedBytes = Base64.getDecoder().decode(str2);
        String decodedString = new String(decodedBytes);

        //System.out.println(encodedString);
        //System.out.println(decodedString);

        return decodedString+" "+s[1];

    }
}

