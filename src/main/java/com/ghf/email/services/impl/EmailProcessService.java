package com.ghf.email.services.impl;

import com.ghf.email.model.EmailData;
import com.ghf.email.model.Notification;
import com.ghf.email.process.EmailDataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailProcessService {

    @Autowired
    private KieSession session;
    @Autowired
    private EmailDataProcessor emailDataProcessor;

    public void sendEmail(EmailData emailData, Notification notification) {
        session.insert(emailData);
        session.fireAllRules();
        try{
            if(emailData.getStop()){
                emailDataProcessor.sendMail(notification);
            }else{
                log.info("Email sent ({}) times to same customer within 30 minutes and also sent ({}) times with same subject in a day.", emailData.getSizeOfHalfAnHour(), emailData.getSizeOfWithinADay());
            }
        }catch (Exception e){
            log.error("Exception occurred while sending email to person.", e);
        }
    }
}
