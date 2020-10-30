package com.ghf.email.process;

import com.ghf.email.dao.NotificationDao;
import com.ghf.email.model.EmailInfo;
import com.ghf.email.model.Notification;
import com.ghf.email.services.EmailService;
import com.ghf.email.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Slf4j
@Component
public class EmailDataProcessor {

    @Autowired
    private EmailService emailService;
    @Autowired
    private NotificationDao notificationDao;

    public void sendMail(Notification notification) {
        Optional<String> recipientNameOptional = Optional.ofNullable(notification.getRecipientName());
        Optional<String> recipientEmailOptional = Optional.ofNullable(notification.getRecipientEmail());
        Optional<String> messageTextOptional = Optional.ofNullable(notification.getMessage());
        Optional<String> subjectOptional = Optional.ofNullable(notification.getSubject());
        Optional<String> fromEmailOptional = Optional.ofNullable(notification.getFromEmail());
        Optional<String> attachmentFileNameOptional = Optional.ofNullable(notification.getAttachmentFileName());
        Optional<byte[]> attachmentBytesOptional = Optional.ofNullable(notification.getAttachmentBytes());
        Optional<String> attachmentDocumentTypeOptional = Optional.ofNullable(notification.getAttachmentDocumentType());
        String recipientName = StringUtils.EMPTY;
        String recipientEmail = StringUtils.EMPTY;
        String messageText = StringUtils.EMPTY;
        String subject = StringUtils.EMPTY;
        String fromEmail = StringUtils.EMPTY;
        String attachmentFileName = StringUtils.EMPTY;
        byte[] attachmentBytes = null;
        String attachmentDocumentType = StringUtils.EMPTY;
        if(recipientNameOptional.isPresent()){
            recipientName = recipientNameOptional.get();
        }
        if(recipientEmailOptional.isPresent()){
            recipientEmail = recipientEmailOptional.get();
        }
        if(messageTextOptional.isPresent()){
            messageText = messageTextOptional.get();
        }
        if(subjectOptional.isPresent()){
            subject = subjectOptional.get();
        }
        if(fromEmailOptional.isPresent()){
            fromEmail = fromEmailOptional.get();
        }
        if(attachmentFileNameOptional.isPresent()){
            attachmentFileName = attachmentFileNameOptional.get();
        }
        if(attachmentBytesOptional.isPresent()){
            attachmentBytes = attachmentBytesOptional.get();
        }
        if(attachmentDocumentTypeOptional.isPresent()){
            attachmentDocumentType = attachmentDocumentTypeOptional.get();
        }
        try {
            EmailInfo emailInfo = Utilities.mapEmailInfo(notification);
            Utilities.mapEmailData(emailInfo, notificationDao);
            notificationDao.save(emailInfo);
            log.info("Sending....");
            fromEmail = "care@godrejhf.com<mailto:care@godrejhf.com";
            if(attachmentBytesOptional.isPresent() && attachmentBytes != null && (attachmentBytes.length > 0)){
                emailService.sendMailWithAttachment(recipientName, recipientEmail, messageText, subject, fromEmail, attachmentFileName, attachmentBytes, attachmentDocumentType, new Locale("UTF-8"));
                emailInfo.setAttachmentBytes(new String(attachmentBytes));
            }else{
                emailService.sendSimpleMail(recipientName, recipientEmail, messageText, subject, fromEmail, new Locale("UTF-8"));
            }
        }catch (Exception e){
            log.error("Failed to process email data sending request: {}, {}", recipientEmail, Utilities.convertObjectToRequestString(notification), e);
        }
    }
}
