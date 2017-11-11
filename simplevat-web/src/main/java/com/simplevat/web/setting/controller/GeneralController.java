/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.setting.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Configuration;
import com.simplevat.service.ConfigurationService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
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
    private Configuration configurationMailUserName;

    @Getter
    @Setter
    private Configuration configurationMailPassword;

    @Getter
    @Setter
    private Configuration configurationMailSmtpAuth;

    @Getter
    @Setter
    private Configuration configurationMailSmtpStartTlsEnable;

    public GeneralController() {
        super(ModuleName.SETTING_MODULE);
    }

    @PostConstruct
    public void init() {
        configurationList = configurationService.getConfigurationList();
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
        }
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
            configurationService.updateConfigurationList(configurationList);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Configuration saved successfully."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
