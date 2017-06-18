package com.simplevat.integration;

import com.simplevat.entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;

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

    private void sendEmail(final Mail mail, boolean html) throws Exception {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper mimeMessagePreparator = new MimeMessageHelper(mimeMessage, false, UTF_8);
            mimeMessagePreparator.setTo(mail.getTo());
            mimeMessagePreparator.setFrom(new InternetAddress(mail.getFrom(), mail.getFromName()));
            mimeMessagePreparator.setText(mail.getBody(), html);
            mimeMessagePreparator.setSubject(mail.getSubject());
        };
        mailSender.send(preparator);
    }

    public void sendHtmlMail(final Mail mail) throws Exception {
        sendEmail(mail, true);
    }
}
