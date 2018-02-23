/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.setting.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.ConfigurationService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.constant.InvoiceReferenceVariable;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.invoice.controller.InvoicePDFGenerator;
import com.simplevat.web.utils.FacesUtil;
import com.simplevat.web.utils.GeneralSettingUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author admin
 */
@Controller
@SpringScopeView
public class GeneralController extends BaseController implements Serializable {

    @Autowired
    private ConfigurationService configurationService;

    private List<Configuration> configurationList;

    @Getter
    @Setter
    private Configuration configurationInvoiceRefPtrn;

    @Getter
    @Setter
    private Configuration configurationMailHost;

    @Getter
    @Setter
    private Configuration configurationMailPort;

    @Getter
    @Setter
    private Configuration configurationInvoiceTemplate;

    @Getter
    @Setter
    private Configuration configurationMailUserName;

    @Getter
    @Setter
    private InvoiceTemplateModel invoiceTemplateModel;

    @Getter
    @Setter
    private Configuration configurationMailPassword;

    @Getter
    @Setter
    private Configuration configurationMailSmtpAuth;

    @Getter
    @Setter
    private Configuration configurationMailSmtpStartTlsEnable;

    @Getter
    @Setter
    private Configuration configurationInvoiceMailTamplateSubject;

    @Getter
    @Setter
    private Configuration configurationInvoiceMailTamplateBody;

    @Getter
    @Setter
    private List<InvoiceReferenceVariable> invoiceVariableList;

    @Getter
    @Setter
    private InvoicePreviewTemplate invoicePreviewTemplate;

    @Autowired
    private InvoicePDFGenerator invoicePDFGenerator;

    public GeneralController() {
        super(ModuleName.GENERALSETTING_MODULE);
    }

    @PostConstruct
    public void init() {
        invoiceVariables();
        configurationList = configurationService.getConfigurationList();
        invoicePreviewTemplate = new InvoicePreviewTemplate();
        if (configurationList != null && !configurationList.isEmpty()) {
            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICING_REFERENCE_PATTERN)).findAny().isPresent()) {
                configurationInvoiceRefPtrn = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICING_REFERENCE_PATTERN)).findFirst().get();
            } else {
                configurationInvoiceRefPtrn = new Configuration();
                configurationInvoiceRefPtrn.setName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_HOST)).findAny().isPresent()) {
                configurationMailHost = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_HOST)).findFirst().get();
            } else {
                configurationMailHost = new Configuration();
                configurationMailHost.setName(ConfigurationConstants.MAIL_HOST);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_PASSWORD)).findAny().isPresent()) {
                configurationMailPassword = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_PASSWORD)).findFirst().get();
            } else {
                configurationMailPassword = new Configuration();
                configurationMailPassword.setName(ConfigurationConstants.MAIL_PASSWORD);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_PORT)).findAny().isPresent()) {
                configurationMailPort = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_PORT)).findFirst().get();
            } else {
                configurationMailPort = new Configuration();
                configurationMailPort.setName(ConfigurationConstants.MAIL_PORT);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)).findAny().isPresent()) {
                configurationMailSmtpAuth = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)).findFirst().get();
            } else {
                configurationMailSmtpAuth = new Configuration();
                configurationMailSmtpAuth.setName(ConfigurationConstants.MAIL_SMTP_AUTH);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_USERNAME)).findAny().isPresent()) {
                configurationMailUserName = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_USERNAME)).findFirst().get();
            } else {
                configurationMailUserName = new Configuration();
                configurationMailUserName.setName(ConfigurationConstants.MAIL_USERNAME);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)).findAny().isPresent()) {
                configurationMailSmtpStartTlsEnable = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)).findFirst().get();
            } else {
                configurationMailSmtpStartTlsEnable = new Configuration();
                configurationMailSmtpStartTlsEnable.setName(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_BODY)).findAny().isPresent()) {
                configurationInvoiceMailTamplateBody = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_BODY)).findFirst().get();
            } else {
                configurationInvoiceMailTamplateBody = new Configuration();
                configurationInvoiceMailTamplateBody.setName(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_BODY);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_SUBJECT)).findAny().isPresent()) {
                configurationInvoiceMailTamplateSubject = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_SUBJECT)).findFirst().get();
            } else {
                configurationInvoiceMailTamplateSubject = new Configuration();
                configurationInvoiceMailTamplateSubject.setName(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_SUBJECT);
            }

            if (configurationList != null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICING_TEMPLATE)).findAny().isPresent()) {
                configurationInvoiceTemplate = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICING_TEMPLATE)).findFirst().get();
                getInvoiceTemplateByValue(configurationInvoiceTemplate.getValue());
            } else {
                configurationInvoiceTemplate = new Configuration();
                configurationInvoiceTemplate.setName(ConfigurationConstants.INVOICING_TEMPLATE);
            }
        } else {
            configurationList = new ArrayList<>();

            configurationInvoiceRefPtrn = new Configuration();
            configurationInvoiceRefPtrn.setName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);

            configurationMailHost = new Configuration();
            configurationMailHost.setName(ConfigurationConstants.MAIL_HOST);

            configurationMailPassword = new Configuration();
            configurationMailPassword.setName(ConfigurationConstants.MAIL_PASSWORD);

            configurationMailPort = new Configuration();
            configurationMailPort.setName(ConfigurationConstants.MAIL_PORT);

            configurationMailSmtpAuth = new Configuration();
            configurationMailSmtpAuth.setName(ConfigurationConstants.MAIL_SMTP_AUTH);

            configurationMailUserName = new Configuration();
            configurationMailUserName.setName(ConfigurationConstants.MAIL_USERNAME);

            configurationMailSmtpStartTlsEnable = new Configuration();
            configurationMailSmtpStartTlsEnable.setName(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE);

            configurationInvoiceMailTamplateBody = new Configuration();
            configurationInvoiceMailTamplateBody.setName(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_BODY);

            configurationInvoiceMailTamplateSubject = new Configuration();
            configurationInvoiceMailTamplateSubject.setName(ConfigurationConstants.INVOICE_MAIL_TAMPLATE_SUBJECT);

            configurationInvoiceTemplate = new Configuration();
            configurationInvoiceTemplate.setName(ConfigurationConstants.INVOICING_TEMPLATE);
        }
        ByteArrayOutputStream invoicepdfOutputStream = invoicePDFGenerator.generatePDF(InvoicePreviewTemplate.getInvoiceObject(), GeneralSettingUtil.invoiceTemplateList().get(0));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(DefaultStreamContentInvoicePdf.STREAMED_CONTENT_PDF, invoicepdfOutputStream);
    }

    public void saveUpdate() {
        try {
            configurationList.clear();
            if (configurationInvoiceRefPtrn != null) {
                configurationInvoiceRefPtrn.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationInvoiceRefPtrn.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationInvoiceRefPtrn);
            }
            if (configurationMailHost != null) {
                configurationMailHost.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationMailHost.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailHost);
            }
            if (configurationMailPassword != null) {
                configurationMailPassword.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationMailPassword.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailPassword);
            }
            if (configurationMailPort != null) {
                configurationMailPort.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationMailPort.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailPort);
            }
            if (configurationMailSmtpAuth != null) {
                configurationMailSmtpAuth.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationMailSmtpAuth.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailSmtpAuth);
            }
            if (configurationMailSmtpStartTlsEnable != null) {
                configurationMailSmtpStartTlsEnable.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationMailSmtpStartTlsEnable.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailSmtpStartTlsEnable);
            }
            if (configurationMailUserName != null) {
                configurationMailUserName.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationMailUserName.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailUserName);
            }
            if (configurationInvoiceMailTamplateBody != null) {
                configurationInvoiceMailTamplateBody.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationInvoiceMailTamplateBody.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationInvoiceMailTamplateBody);
            }
            if (configurationInvoiceMailTamplateSubject != null) {
                configurationInvoiceMailTamplateSubject.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationInvoiceMailTamplateSubject.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationInvoiceMailTamplateSubject);
            }
            if (invoiceTemplateModel != null) {
                configurationInvoiceTemplate.setValue(invoiceTemplateModel.getValue());
                configurationInvoiceTemplate.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                configurationInvoiceTemplate.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationInvoiceTemplate);
            }
            configurationService.updateConfigurationList(configurationList);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("", "Configuration saved successfully"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//     public DefaultStreamedContent getInvoicepdf() {
//        return new DefaultStreamedContent(new ByteArrayInputStream(.toByteArray()), "application/pdf");
//    }
    public void invoiceTemplateValue() {
        System.out.println("===invoiceTemplateModel======" + invoiceTemplateModel);
        ByteArrayOutputStream invoicepdfOutputStream = invoicePDFGenerator.generatePDF(InvoicePreviewTemplate.getInvoiceObject(), invoiceTemplateModel);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().replace(DefaultStreamContentInvoicePdf.STREAMED_CONTENT_PDF, invoicepdfOutputStream);
    }

    private void getInvoiceTemplateByValue(String value) {
        for (InvoiceTemplateModel invoiceTemplateModel : GeneralSettingUtil.invoiceTemplateList()) {
            if (invoiceTemplateModel.getValue().equals(value)) {
                this.invoiceTemplateModel = invoiceTemplateModel;
            }
        }
    }

    public void invoiceVariables() {
        invoiceVariableList = InvoiceReferenceVariable.getInvoiceReferenceVariables();
    }

    public List<InvoiceTemplateModel> completeInvoiceTemplate() {
        return GeneralSettingUtil.invoiceTemplateList();
    }
}
