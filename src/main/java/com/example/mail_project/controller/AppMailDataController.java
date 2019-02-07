package com.example.mail_project.controller;

import com.example.mail_project.entity.CustomerLog;
import com.example.mail_project.entity.LineDataImg;
import com.example.mail_project.entity.MasterDataDetail;
import com.example.mail_project.service.AppMailDataService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.javafx.collections.MappingChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import flexjson.JSONSerializer;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// ส่วน Controller
@RestController
@RequestMapping("/appMailDataCustom")
public class AppMailDataController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppMailDataService appMailDataService;

    @GetMapping("/getCusByID")
    public CustomerLog findAppByIDByRequestParam(@RequestParam("id") Long id){
        return appMailDataService.findByID(id);
    }
    @GetMapping("/getCustomerData")
    public List<CustomerLog> getAppCusData(){
        return appMailDataService.receiveMail();
    }
    @GetMapping("/getCusNewData")
    public List<CustomerLog> getCusNewData(){
        return appMailDataService.receiveNewMail();
    }
    @GetMapping("/getReply")
    public void replyMessage(@RequestParam ("messageNum")int messageNum){
        replyMessage r = new replyMessage();
        r.reply(messageNum);
    }

    @PostMapping("/savebyjson")
    public void Savebyjson(@RequestBody String json){
        LOGGER.info("Json : {}",json);
        appMailDataService.SaveByJsonCus(json);
    }
    @GetMapping("/getDataAll")
    public List<CustomerLog>  findBySender(@RequestParam ("sender")String sender) throws UnsupportedEncodingException {
        sender = URLDecoder.decode(sender, StandardCharsets.UTF_8.name());
        return appMailDataService.getAllData(sender);
    }
//    @GetMapping("/getNewDataAll")
//    public List<CustomerLog>  findBySenderNewMsg(@RequestParam ("sender")String sender) throws UnsupportedEncodingException {
//        sender = URLDecoder.decode(sender, StandardCharsets.UTF_8.name());
//        AppReceiveNewMail app = new AppReceiveNewMail();
//        List<String> JsonList = new LinkedList<String>();
//        JsonList = app.receiveNewMail();
//        for (int i = 0; i < JsonList.size(); i++) {
//            appMailDataService.SaveByJsonCus(JsonList.get(i));
//        }
//        return appMailDataService.getAllData(sender);
//    }
    @PutMapping("/editData/{id}")
    public CustomerLog editData(@RequestBody CustomerLog customerLog,@PathVariable("id") Long id){
        LOGGER.info("edit {}",customerLog.getSentDate());
        return appMailDataService.EditData(customerLog,id);
    }

    @GetMapping("/getDataAll1")
    public List<CustomerLog>  getDataAll1(){
        return appMailDataService.getAllCusData();
    }

    @DeleteMapping("/del/{id}")
    public void Delete(@PathVariable("id")Long id){
        appMailDataService.DeleteByID(id);
    }

    @GetMapping("/getDataBySender")
    public List<CustomerLog>  findBySender1(@RequestParam ("sender")String sender) throws UnsupportedEncodingException {

        return appMailDataService.findBySender(sender);
    }
    @GetMapping("/getDataBySubject")
    public List<CustomerLog>  findBySubject(@RequestParam ("subject")String subject) throws  UnsupportedEncodingException {
        return appMailDataService.findBySubject(subject);
    }
    @GetMapping("/getDataByEmail")
    public List<CustomerLog>  findByEmail(@RequestParam ("email")String email) throws  UnsupportedEncodingException {
        return appMailDataService.findByEmail(email);
    }
    @GetMapping("/getDataByResponsible")
    public List<CustomerLog>  findByResponsible(@RequestParam ("responsible")String responsible) throws  UnsupportedEncodingException {
        return appMailDataService.findByResponsible(responsible);
    }
    @GetMapping("/getDataByType")
    public List<CustomerLog>  findBytype(@RequestParam ("type")String type) throws  UnsupportedEncodingException {
        return appMailDataService.findBytype(type);
    }

    @GetMapping("/getDataByContent")
    public List<CustomerLog>  findByContent(@RequestParam ("content")String content) throws  UnsupportedEncodingException {
        return appMailDataService.findByContent(content);
    }

    @GetMapping("/getDataByLevel")
    public List<CustomerLog>  findByLevel(@RequestParam ("level")String level) throws  UnsupportedEncodingException {
        return appMailDataService.findByLevel(level);
    }

    @GetMapping("/getDataByStatus")
    public List<CustomerLog>  findByStatus(@RequestParam ("status")String status) throws  UnsupportedEncodingException {
        return appMailDataService.findByStatus(status);
    }

    @GetMapping("/getDataByImgg")
    public List<LineDataImg>  findByIdlineAndImgg(@RequestParam ("idline")String idline){
        return appMailDataService.findByIdlineAndImgg(idline);
    }
    @GetMapping("/getDataByAll")
    public String  findAll(@RequestParam ("sender")String sender,@RequestParam ("subject")String subject,@RequestParam ("email")String email,@RequestParam ("responsible")String responsible,@RequestParam ("msg")String msg,@RequestParam ("status")String status,@RequestParam ("level")String level,@RequestParam ("type")String type) throws UnsupportedEncodingException {

        appMailDataService.findbyAll(sender,subject,email,responsible,msg,status,level,type);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(appMailDataService.findbyAll(sender,subject,email,responsible,msg,status,level,type));
    }
    @GetMapping("/getDataBySenderAndType")
    public List<CustomerLog>  findBySenderAndType(@RequestParam ("sender")String sender,@RequestParam ("type")String type) throws  UnsupportedEncodingException {
        return appMailDataService.findBySenderAndType(sender, type);
    }
    @GetMapping("/getDataBySubjectAndType")
    public List<CustomerLog>  findBySubjectAndType(@RequestParam ("subject")String subject,@RequestParam ("type")String type) throws  UnsupportedEncodingException {
        return appMailDataService.findBySubjectAndType(subject, type);
    }
    @GetMapping("/getDataByEmailAndType")
    public List<CustomerLog>  findByEmailAndType(@RequestParam ("email")String email,@RequestParam ("type")String type) throws  UnsupportedEncodingException {
        return appMailDataService.findByEmailAndType(email, type);
    }
    @GetMapping("/getDataByResponsibleAndType")
    public List<CustomerLog>  findByResponsibleAndType(@RequestParam ("responsible")String responsible,@RequestParam ("type")String type) throws  UnsupportedEncodingException {
        return appMailDataService.findByResponsibleAndType(responsible, type);
    }
    @GetMapping("/getDataByMsgAndType")
    public List<CustomerLog>  findByMsgAndType(@RequestParam ("Msg")String msg,@RequestParam ("type")String type) throws  UnsupportedEncodingException {
        return appMailDataService.findByMsgAndType(msg, type);
    }
    @GetMapping("/getkeyword")
    public List<MasterDataDetail>masterDatakey(@RequestParam ("id") Long id,@RequestParam ("code") String code){
        return appMailDataService.masterDatakey(id,code);
    }
    @GetMapping("/getFindCriteria")
    public ResponseEntity<String> findByCriteria(@RequestParam ("sender")String sender,@RequestParam("subject")String subject,
                                            @RequestParam ("email")String email,@RequestParam ("msg")String msg,
                                            @RequestParam ("responsible")String responsible,@RequestParam ("status")String status,
                                            @RequestParam ("level")String level,@RequestParam ("type")String type,
                                            @RequestParam ("startTime")String startTime,@RequestParam ("endTime")String endTime,
                                            @RequestParam ("firstResult")int firstResult,@RequestParam ("maxResult")int maxResult){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json; charset=utf-8");
        LOGGER.info("### getFindCriteria",appMailDataService.findCustomerLogByCriteria(sender,subject,email,msg,responsible,status,level,type,startTime,endTime,firstResult,maxResult));
        System.out.println("### getFindCriteria"+appMailDataService.findCustomerLogByCriteria(sender,subject,email,msg,responsible,status,level,type,startTime,endTime,firstResult,maxResult));
        return new ResponseEntity<String>(new JSONSerializer().deepSerialize(appMailDataService.findCustomerLogByCriteria(sender,subject,email,msg,responsible,status,level,type,startTime,endTime,firstResult,maxResult)),headers,HttpStatus.OK);

    }
    @GetMapping("/getFindCriteriaSize")
    public ResponseEntity<String> findByCriteriaSize(@RequestParam ("sender")String sender,@RequestParam("subject")String subject,
                                            @RequestParam ("email")String email,@RequestParam ("msg")String msg,
                                            @RequestParam ("responsible")String responsible,@RequestParam ("status")String status,
                                            @RequestParam ("level")String level,@RequestParam ("type")String type,
                                            @RequestParam ("startTime")String startTime,@RequestParam ("endTime")String endTime){

        Map<String,Object> map = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json; charset=utf-8");
        map.put("size",appMailDataService.findCustomerLogByCriteriaSize(sender,subject,email,msg,responsible,status,level,type,startTime,endTime).size());
        LOGGER.info("### getFindCriteriaSizeLLL",map);
        System.out.println("### getFindCriterSizeSSS"+map);
        return new ResponseEntity<String>(new JSONSerializer().deepSerialize(map),headers,HttpStatus.OK);
    }
    @GetMapping("/getFindCriteria2")
    public List<CustomerLog> findByCriteria2(@RequestParam ("startTime")String startTime,@RequestParam ("endTime")String endTime){
        LOGGER.info("Criteria Control 2");
        return  appMailDataService.findCustomerLogByCriteria2(startTime,endTime);
    }
    @GetMapping("/getFindDate")
    public List<CustomerLog> findByCriteriaDate(@RequestParam ("startTime")Double startTime,@RequestParam ("endTime")Double endTime){
        return  appMailDataService.findByDate(startTime,endTime);
    }

}
