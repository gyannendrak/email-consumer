package com.ghf.email.model;

import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    private String recipientName;
    private String recipientEmail;
    private String message;
    private String subject;
    private String fromEmail;
    private String attachmentFileName;
    private byte[] attachmentBytes;
    private String attachmentDocumentType;
}