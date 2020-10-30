package com.ghf.email.repository;

import com.ghf.email.model.EmailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmailInfoRepository extends JpaRepository<EmailInfo, Integer> {
    @Query(value = "select u from EmailInfo u where u.recipientEmail=:recipientEmail and u.createdDateTime > :createdDateTime ORDER BY u.createdDateTime DESC")
    List<EmailInfo> findAllByEmailIdAndCreatedDateTimeBefore(@Param("recipientEmail") String recipientEmail, @Param("createdDateTime") Date createdDateTime);

    @Query(value = "select u from EmailInfo u where u.createdDateTime BETWEEN :startDateTime and :endDateTime and u.recipientEmail=:recipientEmail and u.subject=:subject ORDER BY u.createdDateTime DESC")
    List<EmailInfo> findAllByEmailIdAndSubjectAndBetweenCreatedDateTime(@Param("recipientEmail") String recipientEmail, @Param("subject") String subject, @Param("startDateTime") Date startDateTime, @Param("endDateTime") Date endDateTime);
}
