package com.example.mail_project.repository;

import com.example.mail_project.entity.CustomerLog;
import com.example.mail_project.entity.LineDataImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LineDataImgRepository extends JpaSpecificationExecutor<LineDataImg>,
        JpaRepository<LineDataImg, Long>,
        PagingAndSortingRepository<LineDataImg, Long> {

   // @Query SELECT *  FROM CUSTOMER_LOG LEFT JOIN LINE_DATA_IMG ON CUSTOMER_LOG.IDLINE    =  LINE_DATA_IMG.IDLINE

    @Query("SELECT a FROM LineDataImg a where lower(a.idline)like lower(concat('%',concat(:idline,'%')) ) ")
    List<LineDataImg> findByIdlineAndImgg(@Param("idline") String idline);

}
