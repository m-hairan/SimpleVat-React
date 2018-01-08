/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.newactivation;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.Mail;
import com.simplevat.entity.MailEnum;
import com.simplevat.integration.MailIntegration;
import com.simplevat.service.ConfigurationService;
import com.simplevat.web.invoice.controller.InvoiceMailController;
import com.simplevat.web.utils.FileUtility;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;

/**
 *
 * @author admin
 */
@Controller
@SpringScopeView
public class NewActivationMailSender implements Serializable {

    private static final long serialVersionUID = -7388960716549948523L;
    private static final String ADMIN_EMAIL = "no-reply@simplevat.com";
    private static final String ADMIN_USERNAME = "Simplevat Admin";
    private static final String ACTIVATION_TOKEN = "ACTIVATION_TOKEN";
    private static final String ACTIVATION_EMAIL_TEMPLATE_FILE = "/WEB-INF/emailtemplate/activation-email-template.html";

    @Autowired
    private MailIntegration mailIntegration;

    @Autowired
    private ConfigurationService configurationService;

    @Getter
    @Setter
    Configuration configuration;

    @PostConstruct
    public void init() {
        configuration = configurationService.getConfigurationByName(ACTIVATION_TOKEN);
    }

    //todo domain+email+correntTime
    public void sendActivationMail() {
        try {
            String token = "1234";
            createTokenConfiguration(token);
            MailEnum mailEnum = MailEnum.INVOICE_PDF;
            String recipientName = "sagar";
            String summary = "Your activation link will be valid for only 2 hour";
            String link = "Activation Link : <a href =\"http://localhost:8084/simplevat-web/pages/public/verifytoken?token=" + token + "\">click here</a>";
            Object[] args = {recipientName, link, summary};
            String pathname = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ACTIVATION_EMAIL_TEMPLATE_FILE);
            MessageFormat msgFormat = new MessageFormat(FileUtility.readFile(pathname));
            MimeMultipart mimeMultipart = FileUtility.getMessageBody(msgFormat.format(args));
            String firstName = "sagar";
            String[] email = {"sagar@gmail.com"};
            sendActivationMail(mailEnum, mimeMultipart, firstName, email);
        } catch (IOException ex) {
            Logger.getLogger(NewActivationMailSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(NewActivationMailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendActivationMail(MailEnum mailEnum, MimeMultipart mimeMultipart, String firstName, String[] senderMailAddress) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Mail mail = new Mail();
                    mail.setFrom(ADMIN_EMAIL);
                    mail.setFromName(ADMIN_USERNAME);
                    mail.setTo(senderMailAddress);
                    mail.setSubject(mailEnum.getSubject());
                    mailIntegration.sendHtmlEmail(mimeMultipart, mail, getJavaMailSender());
                } catch (Exception ex) {
                    Logger.getLogger(InvoiceMailController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

    private void createTokenConfiguration(String token) {
        if (configuration == null) {
            configuration = new Configuration();
            configuration.setName(ACTIVATION_TOKEN);
            configuration.setValue(token);
            configurationService.persist(configuration);
        } else {
            configuration.setValue(token);
            configurationService.update(configuration);
        }
    }

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setProtocol("smtp");
        sender.setHost("smtp.mailtrap.io");//configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_HOST)).findAny().get().getValue());
        sender.setPort(465);//Integer.parseInt(configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_PORT)).findAny().get().getValue()));
        sender.setUsername("5da35e58bdcffa");//configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_USERNAME)).findAny().get().getValue());
        sender.setPassword("44663e9379bcc0");//configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_PASSWORD)).findAny().get().getValue());
        Properties mailProps = new Properties();
        mailProps.put("mail.smtps.auth", "true");//configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)).findAny().get().getValue());
        mailProps.put("mail.smtp.starttls.enable", "true");//configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)).findAny().get().getValue());
        mailProps.put("mail.smtp.debug", "true");

        sender.setJavaMailProperties(mailProps);

        return sender;
    }
}
