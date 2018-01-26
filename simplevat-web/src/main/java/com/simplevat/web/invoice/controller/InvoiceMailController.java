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
import static com.simplevat.web.utils.MailUtility.verifyMailConfigurationList;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private InvoicePDFGenerator pDFGenerator;

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
    private String from;

    @Getter
    @Setter
    private String messageBody;

    @PostConstruct
    public void init() {
        moreEmails = new ArrayList();
        bccList = new ArrayList();
        ccList = new ArrayList();
        MailDefaultConfigurationModel mailDefaultConfigurationModel = MailUtility.verifyMailConfigurationList(configurationService.getConfigurationList());
        from = mailDefaultConfigurationModel.getMailusername();
        updateSubjectAndMessageBody();
        Integer invoiceId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("invoiceId").toString());
        invoice = invoiceService.findByPK(invoiceId);
        moreEmails.add(invoice.getInvoiceContact().getEmail());
        generateSubject();
        generateMessageBody();
    }

    public void updateSubjectAndMessageBody() {
        Configuration subjectConfiguration = configurationService.getConfigurationByName(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_SUBJECT);
        if (subjectConfiguration != null) {
            subject = subjectConfiguration.getValue();
        }
        Configuration messageBodyConfiguration = configurationService.getConfigurationByName(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_SUBJECT);
        if (messageBodyConfiguration != null) {
            messageBody = messageBodyConfiguration.getValue();
        }
    }

    public void sendInvoiceMail() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("invoiceId", invoice.getInvoiceId());
        pDFGenerator.init();
        byte[] byteArray = pDFGenerator.getInvoicepdfOutputStream().toByteArray();
        Mail mail = getMail(messageBody, subject, from);
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
            mail.setFromName(EmailConstant.ADMIN_EMAIL_SENDER_NAME);
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

    public void generateSubject() {
        if (subject != null) {
            if (subject.contains("{invoiceReferenceNumber}")) {
                subject = subject.replace("{invoiceReferenceNumber}", invoice.getInvoiceReferenceNumber());
            }
            if (subject.contains("{invoiceDate}")) {
                subject = subject.replace("{invoiceDate}", new SimpleDateFormat("MM/dd/YYYY").format(Date.from(invoice.getInvoiceDate().atZone(ZoneId.systemDefault()).toInstant())));
            }
            if (subject.contains("{invoiceDueDate}")) {
                subject = subject.replace("{invoiceDueDate}", new SimpleDateFormat("MM/dd/YYYY").format(Date.from(invoice.getInvoiceDueDate().atZone(ZoneId.systemDefault()).toInstant())));
            }
            if (subject.contains("{invoiceDiscount}")) {
                subject = subject.replace("{invoiceDiscount}", invoice.getInvoiceDiscount() != null ? invoice.getInvoiceDiscount().toString() : "");
            }
            if (subject.contains("{contactName}")) {
                subject = subject.replace("{contactName}", invoice.getInvoiceContact() != null ? invoice.getInvoiceContact().getFirstName() : "");
            }
            if (subject.contains("{projectName}")) {
                subject = subject.replace("{projectName}", invoice.getInvoiceProject() != null ? invoice.getInvoiceProject().getProjectName() : "");
            }
            if (subject.contains("{invoiceAmount}")) {
                subject = subject.replace("{invoiceAmount}", invoice.getInvoiceAmount() != null ? invoice.getInvoiceAmount().toString() : "");
            }
            if (subject.contains("{dueAmount}")) {
                subject = subject.replace("{dueAmount}", invoice.getDueAmount() != null ? invoice.getDueAmount().toString() : "");
            }
            if (subject.contains("{contractPoNumber}")) {
                subject = subject.replace("{contractPoNumber}", invoice.getContractPoNumber() != null ? invoice.getContractPoNumber() : "");
            }
        }
    }

    public void generateMessageBody() {
        if (messageBody != null) {
            if (messageBody.contains("{invoiceReferenceNumber}")) {
                messageBody = messageBody.replace("{invoiceReferenceNumber}", invoice.getInvoiceReferenceNumber());
            }
            if (messageBody.contains("{invoiceDate}")) {
                messageBody = messageBody.replace("{invoiceDate}", new SimpleDateFormat("MM/dd/YYYY").format(Date.from(invoice.getInvoiceDate().atZone(ZoneId.systemDefault()).toInstant())));
            }
            if (messageBody.contains("{invoiceDueDate}")) {
                messageBody = messageBody.replace("{invoiceDueDate}", new SimpleDateFormat("MM/dd/YYYY").format(Date.from(invoice.getInvoiceDueDate().atZone(ZoneId.systemDefault()).toInstant())));
            }
            if (messageBody.contains("{invoiceDiscount}")) {
                messageBody = messageBody.replace("{invoiceDiscount}", invoice.getInvoiceDiscount() != null ? invoice.getInvoiceDiscount().toString() : "");
            }
            if (messageBody.contains("{contactName}")) {
                messageBody = messageBody.replace("{contactName}", invoice.getInvoiceContact() != null ? invoice.getInvoiceContact().getFirstName() : "");
            }
            if (messageBody.contains("{projectName}")) {
                messageBody = messageBody.replace("{projectName}", invoice.getInvoiceProject() != null ? invoice.getInvoiceProject().getProjectName() : "");
            }
            if (messageBody.contains("{contractPoNumber}")) {
                messageBody = messageBody.replace("{contractPoNumber}", invoice.getContractPoNumber() != null ? invoice.getContractPoNumber() : "");
            }

            if (messageBody.contains("{invoiceAmount}")) {
                messageBody = messageBody.replace("{invoiceAmount}", invoice.getInvoiceAmount() != null ? invoice.getInvoiceAmount().toString() : "");
            }
            if (messageBody.contains("{dueAmount}")) {
                messageBody = messageBody.replace("{dueAmount}", invoice.getDueAmount() != null ? invoice.getDueAmount().toString() : "");
            }
        }
    }

}
