package com.simplevat.integration;

import com.simplevat.entity.Mail;
import com.simplevat.entity.MailAttachment;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.activation.FileTypeMap;
import javax.activation.MimetypesFileTypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;

/**
 * Created by Utkarsh Bhavsar on 28/05/17.
 */
@Component
public class MailIntegration {

    private static final String UTF_8 = "UTF-8";

    private final JavaMailSender mailSender;

    @Autowired
    public MailIntegration(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

//    private void sendEmail(final Mail mail, boolean html) throws Exception {
//
//        MimeMessagePreparator preparator = mimeMessage -> {
//            MimeMessageHelper mimeMessagePreparator = new MimeMessageHelper(mimeMessage, true, UTF_8);
//            mimeMessagePreparator.setTo(mail.getTo());
//            mimeMessagePreparator.setFrom(new InternetAddress(mail.getFrom(), mail.getFromName()));
//            mimeMessagePreparator.setText(mail.getBody(), html);
//            mimeMessagePreparator.setSubject(mail.getSubject());
//        };
//        mailSender.send(preparator);
//    }
    
    private void sendEmail(final Mail mail, MailAttachment mailAttachment, JavaMailSender javaMailSender, boolean html) throws Exception {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper mimeMessagePreparator = new MimeMessageHelper(mimeMessage, true, UTF_8);
            mimeMessagePreparator.setTo(mail.getTo());
            mimeMessagePreparator.setFrom(new InternetAddress(mail.getFrom(), mail.getFromName()));
            mimeMessagePreparator.setText(mail.getBody(), html);
            mimeMessagePreparator.setSubject(mail.getSubject());
            if (mailAttachment != null) {
                if (mailAttachment.getAttachmentObject() instanceof InputStreamSource) {
                    mimeMessagePreparator.addAttachment(mailAttachment.getAttachmentName() + ".pdf", (InputStreamSource) mailAttachment.getAttachmentObject());
                }
            }
        };
        javaMailSender.send(preparator);
    }

    private void sendEmail(final Mail mail, JavaMailSender javaMailSender, boolean html) throws Exception {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper mimeMessagePreparator = new MimeMessageHelper(mimeMessage, true, UTF_8);
            mimeMessagePreparator.setTo(mail.getTo());
            mimeMessagePreparator.setFrom(new InternetAddress(mail.getFrom(), mail.getFromName()));
            mimeMessagePreparator.setText(mail.getBody(), html);
            mimeMessagePreparator.setSubject(mail.getSubject());
        };
        javaMailSender.send(preparator);
    }
    
//    public void sendHtmlMail(final Mail mail) throws Exception {
//        sendEmail(mail, true);
//    }
    
    public void sendHtmlMail(final Mail mail, MailAttachment mailAttachment, JavaMailSender javaMailSender) throws Exception {
        sendEmail(mail, mailAttachment, javaMailSender, true);
    }
    
    public void sendHtmlMail(final Mail mail, JavaMailSender javaMailSender) throws Exception {
        sendEmail(mail, javaMailSender, true);
    }
}
