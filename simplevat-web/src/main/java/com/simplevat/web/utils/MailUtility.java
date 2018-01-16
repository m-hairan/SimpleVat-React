/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.utils;

import com.simplevat.entity.Configuration;
import com.simplevat.web.constant.ConfigurationConstants;
import java.util.List;
import java.util.Properties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author h
 */
public class MailUtility {

    public static JavaMailSender getJavaMailSender(List<Configuration> configurationList) {
        MailDefaultConfigurationModel mailDefaultConfigurationModel = verifyMailConfigurationList(configurationList);
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setProtocol("smtp");
        sender.setHost(mailDefaultConfigurationModel.getMailhost());
        sender.setPort(Integer.parseInt(mailDefaultConfigurationModel.getMailport()));
        sender.setUsername(mailDefaultConfigurationModel.getMailusername());
        sender.setPassword(mailDefaultConfigurationModel.getMailpassword());
        Properties mailProps = new Properties();
        mailProps.put("mail.smtps.auth", mailDefaultConfigurationModel.getMailsmtpAuth());
        mailProps.put("mail.smtp.starttls.enable", mailDefaultConfigurationModel.getMailstmpStartTLSEnable());
        mailProps.put("mail.smtp.debug", "true");
        sender.setJavaMailProperties(mailProps);
        return sender;
    }

    public static MailDefaultConfigurationModel verifyMailConfigurationList(List<Configuration> configurationList) {
        MailDefaultConfigurationModel mailDefaultConfigurationModel = new MailDefaultConfigurationModel();
        boolean incompleteConfiguration = false;
        if (configurationList != null && !configurationList.isEmpty()) {
            for (Configuration configuration : configurationList) {
                if (configuration.getName().equals(ConfigurationConstants.MAIL_HOST)) {
                    if (configuration.getValue() == null || configuration.getValue().isEmpty()) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_PORT)) {
                    if (configuration.getValue() == null || configuration.getValue().isEmpty()) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_USERNAME)) {
                    if (configuration.getValue() == null || configuration.getValue().isEmpty()) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_PASSWORD)) {
                    if (configuration.getValue() == null || configuration.getValue().isEmpty()) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)) {
                    if (configuration.getValue() == null || configuration.getValue().isEmpty()) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)) {
                    if (configuration.getValue() == null || configuration.getValue().isEmpty()) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
            }
        } else {
            incompleteConfiguration = true;
        }
        if (!incompleteConfiguration) {
            mailDefaultConfigurationModel.setMailhost(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_HOST)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailport(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_PORT)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailusername(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_USERNAME)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailpassword(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_PASSWORD)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailsmtpAuth(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)).findAny().get().getValue());
            mailDefaultConfigurationModel.setMailstmpStartTLSEnable(configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)).findAny().get().getValue());
        }
        return mailDefaultConfigurationModel;
    }
}
