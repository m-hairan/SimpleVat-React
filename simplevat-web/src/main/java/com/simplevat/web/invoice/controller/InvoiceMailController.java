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
import com.simplevat.web.utils.MailUtility;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void sendInvoiceMail() throws Exception {
        if (moreEmails == null) {
            moreEmails = new ArrayList();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Integer invoiceId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("invoiceId").toString());
        MailEnum mailEnum = MailEnum.INVOICE_PDF;
        String summary = "";
        Invoice invoice = invoiceService.findByPK(invoiceId);
        Optional<Contact> contact = contactService.getContactByEmail(invoice.getInvoiceContact().getEmail());
        if (contact.isPresent()) {
            Contact contactObj = contact.get();
            String firstName = contactObj.getFirstName();
            byte[] byteArray = invoiceUtil.prepareMailReport(outputStream, invoiceId).toByteArray();
            moreEmails.add(invoice.getInvoiceContact().getEmail());
            String[] emailsArray = Arrays.copyOf(moreEmails.toArray(), moreEmails.toArray().length, String[].class);
            sendInvoiceMail(mailEnum, summary, firstName, emailsArray, byteArray);
            moreEmails.clear();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Email address provided."));
        }
    }

    private void sendInvoiceMail(MailEnum mailEnum, String summary, String firstName, String[] senderMailAddress, byte[] byteArray) throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Mail mail = new Mail();
                    mail.setBody(summary);
                    mail.setFrom(ADMIN_EMAIL);
                    mail.setFromName(ADMIN_USERNAME);
                    mail.setTo(senderMailAddress);
                    mail.setSubject(mailEnum.getSubject());
                    MailAttachment attachment = new MailAttachment();
                    attachment.setAttachmentName("Invoice");
                    attachment.setAttachmentObject(new ByteArrayResource(byteArray));
                    mailIntegration.sendHtmlMail(mail, attachment, MailUtility.getJavaMailSender(configurationService.getConfigurationList()));
                } catch (Exception ex) {
                    Logger.getLogger(InvoiceMailController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your Invoice Generated successfully. Please check your mail for further details"));
    }

}
