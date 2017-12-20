/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.initialsetup.controller;

import com.simplevat.web.setting.controller.*;
import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.User;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.impl.UserServiceNewImpl;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.login.controller.SecurityBean;
import com.simplevat.web.utils.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
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
public class MailConfigurationController implements Serializable {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private UserServiceNew userService;
    private List<Configuration> configurationList;

    @Getter
    @Setter
    private Configuration configurationInvoiceRefPtrn;
    @Getter
    @Setter
    private User user;

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

    @PostConstruct
    public void init() {
//        if (!userService.findAll().isEmpty()) {
//            try {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("pages/public/login.xhtml");
//            } catch (IOException ex) {
//                java.util.logging.Logger.getLogger(SecurityBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        Object objSelectedExpenseModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId");
        Integer userId = Integer.parseInt(objSelectedExpenseModel.toString());
        user = userService.findByPK(userId);
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

    public String saveUpdate() {
        try {
            configurationList.clear();
            if (configurationInvoiceRefPtrn != null) {
                configurationInvoiceRefPtrn.setLastUpdateBy(user.getUserId());
                configurationInvoiceRefPtrn.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationInvoiceRefPtrn);
            }
            if (configurationMailHost != null) {
                configurationMailHost.setLastUpdateBy(user.getUserId());
                configurationMailHost.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailHost);
            }
            if (configurationMailPassword != null) {
                configurationMailPassword.setLastUpdateBy(user.getUserId());
                configurationMailPassword.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailPassword);
            }
            if (configurationMailPort != null) {
                configurationMailPort.setLastUpdateBy(user.getUserId());
                configurationMailPort.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailPort);
            }
            if (configurationMailSmtpAuth != null) {
                configurationMailSmtpAuth.setLastUpdateBy(user.getUserId());
                configurationMailSmtpAuth.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailSmtpAuth);
            }
            if (configurationMailSmtpStartTlsEnable != null) {
                configurationMailSmtpStartTlsEnable.setLastUpdateBy(user.getUserId());
                configurationMailSmtpStartTlsEnable.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailSmtpStartTlsEnable);
            }
            if (configurationMailUserName != null) {
                configurationMailUserName.setLastUpdateBy(user.getUserId());
                configurationMailUserName.setLastUpdateDate(Calendar.getInstance().getTime());
                configurationList.add(configurationMailUserName);
            }
            configurationService.updateConfigurationList(configurationList);
            return "/pages/public/login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
