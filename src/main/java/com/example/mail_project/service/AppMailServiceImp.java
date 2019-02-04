package com.example.mail_project.service;


import com.example.mail_project.entity.CustomerLog;
import com.example.mail_project.entity.LineDataImg;
import com.example.mail_project.entity.MasterDataDetail;
import com.example.mail_project.repository.CustomerLogRepository;
import com.example.mail_project.repository.LineDataImgRepository;
import com.example.mail_project.repository.MasterDataDetailRepository;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    public static List<String> LIST_LEVEL = new ArrayList<>();
    public static List<String> LIST_STATUS = new ArrayList<>();
    public static List<String> LIST_TYPE = new ArrayList<>();


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
    public List<MasterDataDetail> masterDatakey(Long id, String code) {
        List<MasterDataDetail> listKeyword = masterDataDetailRepository.findMasterDataDetailsByIdEquals(id, code);
        int countresult = 0;
        int countKeyword = 0;
        String[] resultKeyword = new String[100];
        String[] keywordSplitList = new String[100];

        switch(code)
        {
            case "program.list" :{
                LIST_PROGRAM = null;
                LIST_PROGRAM =new ArrayList<>();
                for (String result : resultKeyword) {
                    resultKeyword[countresult] = String.valueOf(listKeyword.get(countresult));
                    keywordSplitList = resultKeyword[countresult].split(",");
                }
                for (String progran : keywordSplitList){
                    LIST_PROGRAM.add(progran);
                }
                LOGGER.info("program : {}", LIST_PROGRAM);
            }
            countKeyword = 0;
            break;
            case "keyword.list" :{
                LIST_KEYWORD = null;
                LIST_KEYWORD =new ArrayList<>();
                for (String result : resultKeyword) {
                    resultKeyword[countresult] = String.valueOf(listKeyword.get(countresult));
                    keywordSplitList = resultKeyword[countresult].split(",");
                }
                for (String keyword : keywordSplitList){
                    LIST_KEYWORD.add(keyword);
                }
                LOGGER.info("Keywor : {}", LIST_KEYWORD);
            }
            countKeyword = 0;
            break;
            /////////////////////////////////////////////////////
//            case "level.list" :{
//                LIST_LEVEL = null;
//                LIST_LEVEL =new ArrayList<>();
//                for (String result : resultKeyword) {
//                    resultKeyword[countresult] = String.valueOf(listKeyword.get(countresult));
//                    keywordSplitList = resultKeyword[countresult].split(",");
//                }
//                for (String keyword : keywordSplitList){
//                    LIST_LEVEL.add(keyword);
//                }
//                LOGGER.info("level : {}", LIST_LEVEL);
//            }
//            countKeyword = 0;
//            break;
//            case "status.list" :{
//                LIST_STATUS = null;
//                LIST_STATUS =new ArrayList<>();
//                for (String result : resultKeyword) {
//                    resultKeyword[countresult] = String.valueOf(listKeyword.get(countresult));
//                    keywordSplitList = resultKeyword[countresult].split(",");
//                }
//                for (String keyword : keywordSplitList){
//                    LIST_STATUS.add(keyword);
//                }
//                LOGGER.info("status : {}", LIST_STATUS);
//            }
//            countKeyword = 0;
//            break;
//            case "type.list" :{
//                LIST_TYPE = null;
//                LIST_TYPE =new ArrayList<>();
//                for (String result : resultKeyword) {
//                    resultKeyword[countresult] = String.valueOf(listKeyword.get(countresult));
//                    keywordSplitList = resultKeyword[countresult].split(",");
//                }
//                for (String keyword : keywordSplitList){
//                    LIST_TYPE.add(keyword);
//                }
//                LOGGER.info("type : {}", LIST_TYPE);
//            }
//            countKeyword = 0;
//            break;
            /////////////////////////////////////////////////////
            default :
                LOGGER.info("countresult : {}", countresult++);
                break;
        }
        LOGGER.info("Keywor : {}", LIST_KEYWORD);
        LOGGER.info("program : {}", LIST_PROGRAM);
        return masterDataDetailRepository.findMasterDataDetailsByIdEquals(id, code);
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

        return criteria.list();
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

}
