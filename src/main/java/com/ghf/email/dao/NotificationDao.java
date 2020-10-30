package com.ghf.email.dao;

import com.ghf.email.model.EmailInfo;
import com.ghf.email.repository.EmailInfoRepository;
import com.ghf.email.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class NotificationDao {

    private EmailInfoRepository emailInfoRepository;

    @Autowired
    public NotificationDao(EmailInfoRepository emailInfoRepository){
        this.emailInfoRepository = emailInfoRepository;
    }

    public List<EmailInfo> findByEmailId(String emailId) {
        try {
            Date minusHalfAnHour = Utilities.minusHalfAnHour();
            List<EmailInfo> emailInfoHistories = emailInfoRepository.findAllByEmailIdAndCreatedDateTimeBefore(emailId, minusHalfAnHour);
            return emailInfoHistories;
        } catch (Exception e) {
            log.error("Email Id = {} for Email does not exist. " + e, emailId);
            return new ArrayList<>();
        }
    }

    public List<EmailInfo> findByEmailId(String emailId, String subject) {
        try {
            Date startDateTime = Utilities.startDateTime();
            Date endDateTime = Utilities.endDateTime();
            List<EmailInfo> emailInfoHistories = emailInfoRepository.findAllByEmailIdAndSubjectAndBetweenCreatedDateTime(emailId, subject, startDateTime, endDateTime);
            return emailInfoHistories;
        } catch (Exception e) {
            log.error("Email Id = {} for Email does not exist. " + e, emailId);
        }
        return new ArrayList<>();
    }

    public void save(EmailInfo emailInfo) {
        try {
            emailInfoRepository.save(emailInfo);
        } catch (Exception e) {
            log.error("Unable to save Email into DB. " + e);
        }
    }

    public long count() {
        try {
            return emailInfoRepository.count();
        } catch (Exception e) {
            log.error("Unable to count Email from DB. " + e);
        }
        return 0l;
    }
}
