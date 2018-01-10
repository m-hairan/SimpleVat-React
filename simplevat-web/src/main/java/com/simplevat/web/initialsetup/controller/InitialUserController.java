/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.initialsetup.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Company;
import com.simplevat.service.RoleService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.login.controller.SecurityBean;
import com.simplevat.web.user.controller.UserProfileController;
import com.simplevat.web.user.model.UserDTO;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import com.simplevat.entity.Country;
import com.simplevat.entity.IndustryType;
import com.simplevat.entity.Mail;
import com.simplevat.entity.MailEnum;
import com.simplevat.entity.Role;
import com.simplevat.entity.User;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.invoice.controller.InvoiceMailController;
import java.util.Properties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import com.simplevat.entity.Configuration;
import com.simplevat.integration.MailIntegration;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.CountryService;
import com.simplevat.service.IndustryTypeService;
import com.simplevat.web.company.controller.CompanyHelper;
import com.simplevat.web.company.controller.CompanyModel;
import com.simplevat.web.invoice.controller.InvoiceReminderController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author admin
 */
@Controller
@SpringScopeView
public class InitialUserController implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitialCompanyProfileController.class);
    private static final long serialVersionUID = -7388960716549948523L;
    private static final String ADMIN_EMAIL = "no-reply@simplevat.com";
    private static final String ADMIN_USERNAME = "Simplevat Admin";

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserServiceNew userService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private MailIntegration mailIntegration;
    @Autowired
    private CountryService countryService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IndustryTypeService industryTypeService;
    @Setter
    @Getter
    private UserDTO selectedUser;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    public CompanyModel companyModel;
    private CompanyHelper companyHelper;
    @Getter
    private List<Country> countries = new ArrayList<>();
    String userId;
    String mailhost = System.getenv("SIMPLEVAT_MAIL_HOST");
    String mailport = System.getenv("SIMPLEVAT_MAIL_PORT");
    String mailusername = System.getenv("SIMPLEVAT_MAIL_USERNAME");
    String mailpassword = System.getenv("SIMPLEVAT_MAIL_PASSWORD");
    String mailsmtpAuth = System.getenv("SIMPLEVAT_MAIL_SMTP_AUTH");
    String mailstmpStartTLSEnable = System.getenv("SIMPLEVAT_MAIL_SMTP_STARTTLS_ENABLE");

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String token = context.getExternalContext().getRequestParameterMap().get("token");
        String envToken = System.getenv("SIMPLEVAT_TOKEN");
        if (!userService.findAll().isEmpty()) {
            try {
                context.getExternalContext().redirect("/pages/public/login.xhtml");
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(SecurityBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (token == null || !token.equals(envToken)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/public/token-mismatched-error.xhtml");
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(InitialUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        companyModel = new CompanyModel();
        companyModel.setCompanyName(context.getExternalContext().getRequestParameterMap().get("companyname"));
        companyHelper = new CompanyHelper();
        selectedUser = new UserDTO();
        selectedUser.setUserEmail(context.getExternalContext().getRequestParameterMap().get("username"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(context.getExternalContext().getRequestParameterMap().get("password"));
        selectedUser.setPassword(encodedPassword);
        Role role = roleService.getDefaultRole();
        if (role != null) {
            selectedUser.setRole(role);
        }
        countries = countryService.getCountries();
    }

    public String saveUpdate() {
        try {
            Company c = companyHelper.getCompanyFromCompanyModel(companyModel);
            c.setCompanyExpenseBudget(BigDecimal.ZERO);
            c.setCompanyRevenueBudget(BigDecimal.ZERO);
            c.setCreatedBy(0);
            c.setCreatedDate(LocalDateTime.now());
            companyService.persist(c);

            User user = new User();
            BeanUtils.copyProperties(selectedUser, user);
            user.setCreatedBy(0);
            user.setCreatedDate(LocalDateTime.now());
            user.setIsActive(Boolean.TRUE);
            user.setCompany(companyService.findByPK(c.getCompanyId()));
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            userService.persist(user);
            sendNewUserMail(user);

            return "/pages/public/login.xhtml?faces-redirect=true";
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public List<Country> completeCountry(String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();
        Iterator<Country> countryIterator = this.countries.iterator();
        LOGGER.debug(" Size :" + countries.size());
        while (countryIterator.hasNext()) {
            Country country = countryIterator.next();
            if (country.getCountryName() != null
                    && !country.getCountryName().isEmpty()
                    && country.getCountryName().toUpperCase().contains(countryStr.toUpperCase())) {
                countrySuggestion.add(country);
            } else if (country.getIsoAlpha3Code() != null
                    && !country.getIsoAlpha3Code().isEmpty()
                    && country.getIsoAlpha3Code().toUpperCase().contains(countryStr.toUpperCase())) {
                countrySuggestion.add(country);
            }
        }
        LOGGER.debug(" Size :" + countrySuggestion.size());
        return countrySuggestion;
    }

    public List<IndustryType> completeIndustryType() {
        return industryTypeService.getIndustryTypes();
    }

    private void sendNewUserMail(User user) {

        MailEnum mailEnum = MailEnum.NEW_USER_CREATED;
        String summary = "Hello " + user.getFirstName() + " " + user.getLastName()
                + "<br>Your user account created successfully.<br> Your username is \""
                + user.getUserEmail() + "\".<br>Please use the same password you used for signup on simplevat.com.";
        try {
            String[] email = {user.getUserEmail()};
            sendMailToUser(mailEnum, summary, email);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(InvoiceReminderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendMailToUser(MailEnum mailEnum, String summary, String[] senderMailAddress) {
        Thread t = new Thread(() -> {
            try {
                Mail mail = new Mail();
                mail.setBody(summary);
                mail.setFrom(ADMIN_EMAIL);
                mail.setFromName(ADMIN_USERNAME);
                mail.setTo(senderMailAddress);
                mail.setSubject(mailEnum.getSubject());
                mailIntegration.sendHtmlMail(mail, getJavaMailSender(configurationService.getConfigurationList()));
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(InvoiceMailController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
    }

    public JavaMailSender getJavaMailSender(List<Configuration> configurationList) {
        verifyMailConfigurationList(configurationList);
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setProtocol("smtp");
        sender.setHost(mailhost);
        sender.setPort(Integer.parseInt(mailport));
        sender.setUsername(mailusername);
        sender.setPassword(mailpassword);
        Properties mailProps = new Properties();
        mailProps.put("mail.smtps.auth", mailsmtpAuth);
        mailProps.put("mail.smtp.starttls.enable", mailstmpStartTLSEnable);
        mailProps.put("mail.smtp.debug", "true");
        sender.setJavaMailProperties(mailProps);
        return sender;
    }

    public void verifyMailConfigurationList(List<Configuration> configurationList) {
        boolean incompleteConfiguration = false;
        if (configurationList != null && !configurationList.isEmpty()) {
            for (Configuration configuration : configurationList) {
                if (configuration.getName().equals(ConfigurationConstants.MAIL_HOST)) {
                    if (configuration.getValue() == null) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_PORT)) {
                    if (configuration.getValue() == null) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_USERNAME)) {
                    if (configuration.getValue() == null) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_PASSWORD)) {
                    if (configuration.getValue() == null) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)) {
                    if (configuration.getValue() == null) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
                if (configuration.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)) {
                    if (configuration.getValue() == null) {
                        incompleteConfiguration = true;
                        break;
                    }
                }
            }
        } else {
            incompleteConfiguration = true;
        }
        if (!incompleteConfiguration) {
            mailhost = configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_HOST)).findAny().get().getValue();
            mailport = configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_PORT)).findAny().get().getValue();
            mailusername = configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_USERNAME)).findAny().get().getValue();
            mailpassword = configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_PASSWORD)).findAny().get().getValue();
            mailsmtpAuth = configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)).findAny().get().getValue();
            mailstmpStartTLSEnable = configurationList.stream().filter(mailConfiguration -> mailConfiguration.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)).findAny().get().getValue();
        }

    }
}
