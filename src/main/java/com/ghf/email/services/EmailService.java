package com.ghf.email.services;

import javax.mail.MessagingException;
import java.util.Locale;

public interface EmailService {
  public void sendTextMail(final String recipientName, final String recipientEmail, final String messageText, final String subject, final String fromEmail, final Locale locale)throws MessagingException;
  public void sendSimpleMail(final String recipientName, final String recipientEmail, final String messageText, final String subject, final String fromEmail, final Locale locale)throws MessagingException;
  public void sendMailWithAttachment(final String recipientName, final String recipientEmail, final String messageText, final String subject, final String fromEmail, final String attachmentFileName, final byte[] attachmentBytes, final String attachmentContentType, final Locale locale) throws MessagingException;
}
