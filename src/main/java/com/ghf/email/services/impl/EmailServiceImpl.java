package com.ghf.email.services.impl;

import com.ghf.email.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

  private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/email-html";
  private static final String EMAIL_WITHATTACHMENT_TEMPLATE_NAME = "html/email-html-template";

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  @Qualifier("emailTemplateEngine")
  private TemplateEngine htmlTemplateEngine;

  @Override
  public void sendTextMail(final String recipientName, final String recipientEmail, final String messageText, final String subject, final String fromEmail, final Locale locale) throws MessagingException {

    final Context ctx = new Context(locale);
    ctx.setVariable("recipientName", recipientName);
    ctx.setVariable("messageText", messageText);

    final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
    final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    message.setSubject(subject);
    message.setFrom(fromEmail);
    message.setTo(recipientEmail);
    this.mailSender.send(mimeMessage);
  }

  @Override
  public void sendSimpleMail(final String recipientName, final String recipientEmail, final String message, final String subject, final String fromEmail, final Locale locale)throws MessagingException {

    final Context ctx = new Context(locale);
    ctx.setVariable("recipientName", recipientName);
    ctx.setVariable("message", message);
    ctx.setVariable("regardsName", "Gyannendra Kumar");

    final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
    final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    messageHelper.setSubject(subject);
    messageHelper.setFrom(fromEmail);
    messageHelper.setTo(recipientEmail);

    final String htmlContent = this.htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
    messageHelper.setText(htmlContent, true);
    this.mailSender.send(mimeMessage);
    log.debug("Email successfully sent to ({}) and message : {} ", recipientEmail, message);
  }

  @Override
  public void sendMailWithAttachment(final String recipientName, final String recipientEmail, final String message, final String subject, final String fromEmail, final String attachmentFileName,
          final byte[] attachmentBytes, final String attachmentContentType, final Locale locale)throws MessagingException {

    final Context ctx = new Context(locale);
    ctx.setVariable("recipientName", recipientName);
    ctx.setVariable("message", message);
    ctx.setVariable("regardsName", "Gyannendra Kumar");

    final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
    final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    messageHelper.setSubject(subject);
    messageHelper.setFrom(fromEmail);
    messageHelper.setTo(recipientEmail);

    final String htmlContent = this.htmlTemplateEngine.process(EMAIL_WITHATTACHMENT_TEMPLATE_NAME, ctx);
    messageHelper.setText(htmlContent, true);

    final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
    messageHelper.addAttachment(attachmentFileName, attachmentSource, attachmentContentType);
    this.mailSender.send(mimeMessage);
    log.debug("Email successfully sent to ({}) and message : {} ", recipientEmail, message);
  }
}