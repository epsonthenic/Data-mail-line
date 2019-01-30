package com.example.mail_project.repository;

import com.example.mail_project.entity.CustomerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//ส่วนของ Model
//extend Jpa Spring Boot
public interface CustomerLogRepository extends JpaSpecificationExecutor<CustomerLog>,
        JpaRepository<CustomerLog, Long>,
        PagingAndSortingRepository<CustomerLog, Long> {

    //SQL COMMAND
    @Query("SELECT a FROM CustomerLog a where lower(a.sender)like lower(concat('%',concat(:sender,'%')) ) ")
    List<CustomerLog> findByFirstNameContainingIgnoreCase(@Param("sender") String sender);

    @Query("SELECT a FROM CustomerLog a where lower(a.subject)like lower(concat('%',concat(:subject,'%')) ) ")
    List<CustomerLog> findBySubjectContainingIgnoreCase(@Param("subject") String subject);

    @Query("SELECT a FROM CustomerLog a where lower(a.email)like lower(concat('%',concat(:email,'%')) ) ")
    List<CustomerLog> findByEmailContainingIgnoreCase(@Param("email") String email);

    @Query("SELECT a FROM CustomerLog a where lower(a.responsible)like lower(concat('%',concat(:responsible,'%')) ) ")
    List<CustomerLog> findByResponsibleContainingIgnoreCase(@Param("responsible") String responsible);

    @Query("SELECT a FROM CustomerLog a where lower(a.msg)like lower(concat('%',concat(:content,'%') )) ")
    List<CustomerLog> findByMsgContainingIgnoreCase(@Param("content") String content);

    @Query("SELECT a FROM CustomerLog a where lower(a.level)like lower(concat(:level,'%')) ")
    List<CustomerLog> findByLevelContaining(@Param("level") String level);

    @Query("SELECT a FROM CustomerLog a where lower(a.status)like lower(concat(:status,'%')) ")
    List<CustomerLog> findByStatusContaining(@Param("status") String status);

    @Query("SELECT a FROM CustomerLog a where lower(a.type)like lower(concat(:type,'%')) ")
    List<CustomerLog> findBytypeContainingIgnoreCase(@Param("type") String type);

    List<CustomerLog> findBySenderContainingAndSubjectContainingAndEmailContainingAndResponsibleContainingAndMsgContainingAndLevelContainingAndStatusContainingAndTypeContaining(@Param("sender") String sender, @Param("subject") String subject, @Param("email") String email, @Param("responsible") String responsible, @Param("content") String content, @Param("status") String status, @Param("level") String level, @Param("type") String type);

    List<CustomerLog> findBySenderContainingAndTypeContaining(@Param("sender") String sender,@Param("type") String type);

    List<CustomerLog> findBySubjectContainingAndTypeContaining(@Param("subject") String subject,@Param("type") String type);

    List<CustomerLog> findByEmailContainingAndTypeContaining(@Param("email") String email,@Param("type") String type);

    List<CustomerLog> findByResponsibleContainingAndTypeContaining(@Param("responsible") String responsible,@Param("type") String type);

    List<CustomerLog> findByMsgContainingAndTypeContaining(@Param("mgs") String msg,@Param("type") String type);
}