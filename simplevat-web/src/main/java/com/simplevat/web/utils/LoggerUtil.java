/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.utils;

import com.simplevat.web.constant.EmailConstant;
import com.simplevat.web.newactivation.NewActivationMailSender;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author uday
 */
public class LoggerUtil {

    private static final String SUBJECT = "Application Error - " + System.getenv("SIMPLEVAT_SUBDOMAIN");

    public static void logAndSendErrorMsg(Exception ex) {
        try {
            java.util.logging.Logger.getLogger(NewActivationMailSender.class.getName()).log(Level.SEVERE, null, ex);
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(errors.toString(), "text/html");
            multipart.addBodyPart(messageBodyPart);
            MailUtility.triggerEMailOnBackground(SUBJECT, multipart, EmailConstant.ADMIN_SUPPORT_EMAIL, EmailConstant.ADMIN_EMAIL_SENDER_NAME,
                    new String[]{EmailConstant.ADMIN_SUPPORT_EMAIL}, MailUtility.getDefaultJavaMailSender());
        } catch (MessagingException ex1) {
            Logger.getLogger(LoggerUtil.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public static void logError(Exception ex) {
        java.util.logging.Logger.getLogger(NewActivationMailSender.class.getName()).log(Level.SEVERE, null, ex);
    }
}
