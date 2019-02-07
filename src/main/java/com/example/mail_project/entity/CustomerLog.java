package com.example.mail_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

//ตาราง
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
public class CustomerLog {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String sender;//sender
    private String send_To;//sender_To
    private String subject;//subject
    private String email;//email

    @Length(max = 4000)
    private String msg;//msg

    private String attachments;
    private String responsible;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sentDate;//send_dates

    //private  String sentDate;

    private int messageNum;

    private String status;//status
    private String type;//type
    private String level="0";//level
    private String CC;
    private String BCC;
    private String idline;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customerLog")
    private Set<LineDataImg> details = new HashSet<LineDataImg>();

}