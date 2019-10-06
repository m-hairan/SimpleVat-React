/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.utils;

import com.simplevat.entity.Configuration;
import com.simplevat.entity.Mail;
import com.simplevat.entity.MailEnum;
import com.simplevat.integration.MailIntegration;
import com.simplevat.constant.ConfigurationConstants;
import com.simplevat.constant.EmailConstant;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.mail.internet.MimeMultipart;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author h
 */
public class MailUtility {

    public static void triggerEMailOnBackground(String subject, MimeMultipart mimeMultipart, String fromEmailId, String fromName, String[] toMailAddress, JavaMailSender javaMailSender) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Mail mail = new Mail();
                    mail.setFrom(fromEmailId);
                    mail.setFromName(fromName);
                    mail.setTo(toMailAddress);
                    mail.setSubject(subject);
                 //  MailIntegration.sendHtmlEmail(mimeMultipart, mail, javaMailSender);
                } catch (Exception ex) {
                   ex.printStackTrace();
                }
            }
        });
        t.start();
    }

    public static JavaMailSender getJavaMailSender(List<Configuration> configurationList) {
        MailConfigurationModel mailDefaultConfigurationModel = getEMailConfigurationList(configurationList);
        return getJavaMailSender(mailDefaultConfigurationModel);
    }

    public static JavaMailSender getDefaultJavaMailSender() {
        return getJavaMailSender(getDefaultEmailConfigurationList());
    }

    public static JavaMailSender getJavaMailSender(MailConfigurationModel mailConfigurationModel) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setProtocol("smtp");
        sender.setHost(mailConfigurationModel.getMailhost());
        sender.setPort(Integer.parseInt(mailConfigurationModel.getMailport()));
        sender.setUsername(mailConfigurationModel.getMailusername());
        sender.setPassword(mailConfigurationModel.getMailpassword());
        Properties mailProps = new Properties();
        mailProps.put("mail.smtps.auth", mailConfigurationModel.getMailsmtpAuth());
        mailProps.put("mail.smtp.starttls.enable", mailConfigurationModel.getMailstmpStartTLSEnable());
        mailProps.put("mail.smtp.debug", "true");
        sender.setJavaMailProperties(mailProps);
        return sender;
    }

    public static MailConfigurationModel getEMailConfigurationList(List<Configuration> configurationList) {
        MailConfigurationModel mailDefaultConfigurationModel = getDefaultEmailConfigurationList();
        int mailConfigCount = 0;
        if (configurationList != null && !configurationList.isEmpty()) {
            for (Configuration configuration : configurationList) {
                if (configuration.getName().equals(ConfigurationConstants.MAIL_HOST)) {
                    if (configuration.getValue() != null && !configuration.getValue().isEmpty()) {
                        mailConfigCount++;
                    }
                } else if (configuration.getName().equals(ConfigurationConstants.MAIL_PORT)) {
                    if (configuration.getValue() != null && !configuration.getValue().isEmpty()) {
                        mailConfigCount++;
                    }
                } else if (configuration.getName().equals(ConfigurationConstants.MAIL_USERNAME)) {
                    if (configuration.getValue() != null && !configuration.getValue().isEmpty()) {
                        mailConfigCount++;
                    }
                } else if (configuration.getName().equals(ConfigurationConstants.MAIL_PASSWORD)) {
                    if (configuration.getValue() != null && !configuration.getValue().isEmpty()) {
                        mailConfigCount++;
                    }
                } else if (configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)) {
                    if (configuration.getValue() != null && !configuration.getValue().isEmpty()) {
                        mailConfigCount++;
                    }
                } else if (configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)) {
                    if (configuration.getValue() != null && !configuration.getValue().isEmpty()) {
                        mailConfigCount++;
                    }
                }
            }
        }
        if (mailConfigCount == 6) {
            mailDefaultConfigurationModel.setMailhost(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_HOST)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailport(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_PORT)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailusername(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_USERNAME)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailpassword(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_PASSWORD)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailsmtpAuth(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailstmpStartTLSEnable(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)).findAny().get().getValue());
        }
        return mailDefaultConfigurationModel;
    }

    public static MailConfigurationModel getDefaultEmailConfigurationList() {
        MailConfigurationModel mailDefaultConfigurationModel = new MailConfigurationModel();
        mailDefaultConfigurationModel.setMailhost(System.getenv("SIMPLEVAT_SMTP_HOST"));
        mailDefaultConfigurationModel.setMailport(System.getenv("SIMPLEVAT_SMTP_PORT"));
        mailDefaultConfigurationModel.setMailusername(System.getenv("SIMPLEVAT_SMTP_USER"));
        mailDefaultConfigurationModel.setMailpassword(System.getenv("SIMPLEVAT_SMTP_PASS"));
        mailDefaultConfigurationModel.setMailsmtpAuth(System.getenv("SIMPLEVAT_SMTP_AUTH"));
        mailDefaultConfigurationModel.setMailstmpStartTLSEnable(System.getenv("SIMPLEVAT_SMTP_STARTTLS_ENABLE"));
        return mailDefaultConfigurationModel;
    }
}
