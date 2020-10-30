package com.ghf.email.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@Entity
@Table(name = "email")
public class EmailInfo {
    @Id
    @Column(name = "email_id")
    private Long emailId;
    @Column(name = "recipient_name")
    private String recipientName;
    @Column(name = "recipient_email")
    private String recipientEmail;
    @Column(name = "message")
    private String message;
    @Column(name = "subject")
    private String subject;
    @Column(name = "from_email")
    private String fromEmail;
    @Column(name = "attachment_file_name")
    private String attachmentFileName;
    @Column(name = "attachment_bytes")
    private String attachmentBytes;
    @Column(name = "attachment_document_type")
    private String attachmentDocumentType;
    @Basic(optional = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date_time")
    private Date createdDateTime;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date_time")
    private Date updatedDateTime;
}