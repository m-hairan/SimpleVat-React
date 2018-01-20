/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.invoice.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Mail;
import com.simplevat.entity.MailAttachment;
import com.simplevat.entity.MailEnum;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.integration.MailIntegration;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.ContactService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.utils.MailDefaultConfigurationModel;
import com.simplevat.web.utils.MailUtility;
import static com.simplevat.web.utils.MailUtility.verifyMailConfigurationList;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@SpringScopeView
public class InvoiceMailController implements Serializable {

//    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(InvoiceMailController.class);
    private static final long serialVersionUID = -7388960716549948523L;
    private static final String ADMIN_EMAIL = "no-reply@simplevat.com";
    private static final String ADMIN_USERNAME = "Simplevat Admin";

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
    private ArrayList<String> moreEmails;

    @Getter
    @Setter
    private ArrayList<String> bccList;

    @Getter
    @Setter
    private ArrayList<String> ccList;

    @Getter
    @Setter
    private Invoice invoice;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String messageBody;

    @PostConstruct
    public void init() {
        moreEmails = new ArrayList();
        bccList = new ArrayList();
        ccList = new ArrayList();
        Integer invoiceId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("invoiceId").toString());
        invoice = invoiceService.findByPK(invoiceId);
        moreEmails.add(invoice.getInvoiceContact().getEmail());
    }

    public void sendInvoiceMail() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] byteArray = invoiceUtil.prepareMailReport(outputStream, invoice.getInvoiceId()).toByteArray();
        MailDefaultConfigurationModel mailDefaultConfigurationModel = MailUtility.verifyMailConfigurationList(configurationService.getConfigurationList());
        Mail mail = getMail(messageBody, subject, mailDefaultConfigurationModel.getMailusername());
        if (mail != null) {
            sendInvoiceMail(mail, byteArray);
            moreEmails.clear();
        }
    }

    private Mail getMail(String body, String subject, String fromEmail) {
        if (moreEmails != null && !moreEmails.isEmpty()) {
            Mail mail = new Mail();
            mail.setBody(body == null ? "" : body);
            mail.setFrom(fromEmail);
            mail.setFromName(ADMIN_USERNAME);
            mail.setTo(Arrays.copyOf(moreEmails.toArray(), moreEmails.toArray().length, String[].class));
            mail.setSubject(subject == null ? "" : subject);
            if (bccList != null && !bccList.isEmpty()) {
                String[] bccArray = Arrays.copyOf(bccList.toArray(), bccList.toArray().length, String[].class);
                mail.setBcc(bccArray);
                bccList.clear();
            }
            if (ccList != null && !ccList.isEmpty()) {
                String[] ccArray = Arrays.copyOf(ccList.toArray(), ccList.toArray().length, String[].class);
                mail.setCc(ccArray);
                ccList.clear();
            }
            return mail;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Email Tried To Send"));
        return null;
    }

    private void sendInvoiceMail(Mail mail, byte[] byteArray) throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MailAttachment attachment = new MailAttachment();
                    attachment.setAttachmentName("Invoice");
                    attachment.setAttachmentObject(new ByteArrayResource(byteArray));
                    mailIntegration.sendHtmlMail(mail, attachment, MailUtility.getJavaMailSender(configurationService.getConfigurationList()));
                    updateInvoice();
                } catch (Exception ex) {
                    Logger.getLogger(InvoiceMailController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your Invoice Generated successfully. Please check your mail for further details"));
    }

    private void updateInvoice() {
        if (!invoice.getFreeze()) {
            invoice.setFreeze(Boolean.TRUE);
            invoiceService.update(invoice);
        }
    }

}
