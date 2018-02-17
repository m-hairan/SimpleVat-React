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
import com.simplevat.integration.MailIntegration;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.CountryService;
import com.simplevat.service.IndustryTypeService;
import com.simplevat.web.company.controller.CompanyHelper;
import com.simplevat.web.company.controller.CompanyModel;
import com.simplevat.web.constant.EmailConstant;
import com.simplevat.web.newactivation.NewActivationMailSender;
import com.simplevat.web.utils.FileUtility;
import com.simplevat.web.utils.LoggerUtil;
import com.simplevat.web.utils.MailConfigurationModel;
import com.simplevat.web.utils.MailUtility;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author admin
 */
@Controller
@SpringScopeView
public class InitialUserController implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitialUserController.class);
    private static final long serialVersionUID = -7388960716549948523L;
    private static final String ACTIVATION_EMAIL_TEMPLATE_FILE = "/WEB-INF/emailtemplate/setup-confirmation.html";

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
    private String baseUrl;
    @Getter
    @Setter
    public CompanyModel companyModel;
    private CompanyHelper companyHelper;
    @Getter
    private List<Country> countries = new ArrayList<>();
    private Base64.Decoder decoder = Base64.getDecoder();
    String userId;

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
        baseUrl = context.getExternalContext().getRequestParameterMap().get("baseUrl");
        password = new String(decoder.decode(context.getExternalContext().getRequestParameterMap().get("password"))).replace(token, "");
        companyModel = new CompanyModel();
        companyModel.setCompanyName(context.getExternalContext().getRequestParameterMap().get("companyname"));
        companyHelper = new CompanyHelper();
        selectedUser = new UserDTO();
        selectedUser.setFirstName(context.getExternalContext().getRequestParameterMap().get("userFirstName"));
        String userLastName = context.getExternalContext().getRequestParameterMap().get("userLastName");
        if (userLastName != null) {
            selectedUser.setLastName(userLastName);
        }
        selectedUser.setUserEmail(context.getExternalContext().getRequestParameterMap().get("username"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        selectedUser.setPassword(encodedPassword);
        Role role = roleService.getDefaultRole();
        if (role != null) {
            selectedUser.setRole(role);
        }
        countries = countryService.getCountries();
    }

    public String saveUpdate() {
        if (!userService.getUserByEmail(selectedUser.getUserEmail()).isPresent()) {
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
                sendActivationMail(user);

                return "/pages/public/setupConfirmation.xhtml?faces-redirect=true&baseUrl=" + baseUrl + "&userLoginId=" + user.getUserEmail();
            } catch (IllegalArgumentException ex) {
                java.util.logging.Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public void sendActivationMail(User user) {
        try {
            MailEnum mailEnum = MailEnum.NEW_USER_CREATED;
            String recipientName = user.getFirstName();
            String accountAddress = baseUrl;
            String userLoginId = user.getUserEmail();
            Object[] args = {recipientName, accountAddress, userLoginId};
            String pathname = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ACTIVATION_EMAIL_TEMPLATE_FILE);
            MessageFormat msgFormat = new MessageFormat(FileUtility.readFile(pathname));
            MimeMultipart mimeMultipart = FileUtility.getMessageBody(msgFormat.format(args));
            String[] email = {userLoginId};
            MailConfigurationModel mailDefaultConfigurationModel = MailUtility.getEMailConfigurationList(configurationService.getConfigurationList());
            sendActivationMail(mailEnum, mimeMultipart, mailDefaultConfigurationModel.getMailusername(), email);
        } catch (IOException | MessagingException ex) {
            LoggerUtil.logAndSendErrorMsg(ex);
        }
    }

    private void sendActivationMail(MailEnum mailEnum, MimeMultipart mimeMultipart, String userName, String[] senderMailAddress) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Mail mail = new Mail();
                    mail.setFrom(userName);
                    mail.setFromName(EmailConstant.ADMIN_EMAIL_SENDER_NAME);
                    mail.setTo(senderMailAddress);
                    mail.setSubject(mailEnum.getSubject());
                    mailIntegration.sendHtmlEmail(mimeMultipart, mail, MailUtility.getJavaMailSender(configurationService.getConfigurationList()));
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(InitialUserController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }
}
