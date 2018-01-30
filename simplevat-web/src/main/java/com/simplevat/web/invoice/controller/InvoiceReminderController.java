/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.invoice.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Mail;
import com.simplevat.entity.MailAttachment;
import com.simplevat.entity.MailEnum;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.integration.MailIntegration;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.ContactService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.constant.EmailConstant;
import com.simplevat.web.utils.MailDefaultConfigurationModel;
import com.simplevat.web.utils.MailUtility;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@SpringScopeView
public class InvoiceReminderController implements Serializable {

//    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(InvoiceMailController.class);
    private static final long serialVersionUID = -7388960716549948523L;

    @Autowired
    private MailIntegration mailIntegration;

    @Autowired
    private InvoiceUtil invoiceUtil;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ContactService contactService;

    @Getter
    @Setter
    private List<MailAttachment> mailAttachmentList;

    @PostConstruct
    public void init() {
        mailAttachmentList = new ArrayList<>();
        List<Invoice> invoices = invoiceService.getInvoiceListByDueDate();
        if (invoices != null && !invoices.isEmpty()) {
            for (Invoice invoice : invoices) {
                sendInvoiceMail(invoice);
            }
        }
    }

    private void sendInvoiceMail(Invoice invoice) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MailEnum mailEnum = MailEnum.INVOICE_PDF;
        String summary = "Please pay your bill your last date is " + invoice.getInvoiceDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + " . Please check the mail attachment for further detail";
        Optional<Contact> contact = contactService.getContactByEmail(invoice.getInvoiceContact().getEmail());
        if (contact.isPresent()) {
            try {
                Contact contactObj = contact.get();
                byte[] byteArray = invoiceUtil.prepareMailReport(outputStream, invoice.getInvoiceId()).toByteArray();
                String[] email = {invoice.getInvoiceContact().getEmail()};
                MailDefaultConfigurationModel mailDefaultConfigurationModel = MailUtility.verifyMailConfigurationList(configurationService.getConfigurationList());
                sendInvoiceMail(mailEnum, summary, mailDefaultConfigurationModel.getMailusername(), email, byteArray);
            } catch (Exception ex) {
                Logger.getLogger(InvoiceReminderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Email address provided."));
        }
    }

    private void sendInvoiceMail(MailEnum mailEnum, String summary, String userName, String[] senderMailAddress, byte[] byteArray) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Mail mail = new Mail();
                    mail.setBody(summary);
                    mail.setFrom(userName);
                    mail.setFromName(EmailConstant.ADMIN_EMAIL_SENDER_NAME);
                    mail.setTo(senderMailAddress);
                    mail.setSubject(mailEnum.getSubject());
                    MailAttachment attachment = new MailAttachment();
                    attachment.setAttachmentName("Invoice");
                    attachment.setAttachmentObject(new ByteArrayResource(byteArray));
                    mailAttachmentList.add(attachment);
                    mailIntegration.sendHtmlMail(mail, mailAttachmentList, MailUtility.getJavaMailSender(configurationService.getConfigurationList()));

                } catch (Exception ex) {
                    Logger.getLogger(InvoiceMailController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }
}
