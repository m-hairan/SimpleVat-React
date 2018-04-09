package com.simplevat.web.login.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Mail;
import com.simplevat.entity.MailEnum;
import com.simplevat.entity.User;
import com.simplevat.integration.MailIntegration;
import com.simplevat.integration.MailPreparer;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.constant.EmailConstant;
import com.simplevat.web.utils.FileUtility;
import com.simplevat.web.utils.MailConfigurationModel;
import com.simplevat.web.utils.MailUtility;
import com.simplevat.web.utils.SessionIdentifierGenerator;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

@Controller
//@ManagedBean(name = "securityBean")
@SpringScopeView
//@Qualifier("securityBean")
public class SecurityBean implements PhaseListener, Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityBean.class);

    private static final long serialVersionUID = -7388960716549948523L;
    private static final String FORGOT_PASSWORD_EMAIL_TEMPLATE_FILE = "/WEB-INF/emailtemplate/forget-password.html";

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    private String versionNumber;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private MailIntegration mailIntegration;

    @Autowired
    private UserServiceNew userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    private Boolean badcredential=false;

    @PostConstruct
    public void init() {
        versionNumber = System.getenv("SIMPLEVAT_RELEASE");
        if (userService.findAll().isEmpty()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/public/token-mismatched-error.xhtml");
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(SecurityBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (e instanceof BadCredentialsException) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                    .put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials!",
                            "Please check you login details."));
        }

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    public String login() {
        LOGGER.info("Starting login from LoginManagedBean");
        try {
            badcredential=false;
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return "/pages/secure/home.xhtml?faces-redirect=true";
        } catch (final Exception e) {
            e.printStackTrace();
            LOGGER.error("Error log in " + e);
            invalidLoginMessage();
        }
        return null;
    }

    public void invalidLoginMessage() {
        badcredential=true;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Invalid login", "Bad Credential. Try again"));
    }

    public Boolean getBadcredential() {
        return badcredential;
    }

    public void setBadcredential(Boolean badcredential) {
        this.badcredential = badcredential;
    }
    

    public String forgotPassword() throws Exception {
        try {
            MailEnum mailEnum = MailEnum.NEW_USER_CREATED;
            Optional<User> userOptional = userService.getUserByEmail(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String recipientName = user.getFirstName();
                String randomPassword = updatedUserPassword(user);
                Object[] args = {recipientName, randomPassword};
                String pathname = FacesContext.getCurrentInstance().getExternalContext().getRealPath(FORGOT_PASSWORD_EMAIL_TEMPLATE_FILE);
                MessageFormat msgFormat = new MessageFormat(FileUtility.readFile(pathname));
                MimeMultipart mimeMultipart = FileUtility.getMessageBody(msgFormat.format(args));
                String[] email = {user.getUserEmail()};
                MailConfigurationModel mailDefaultConfigurationModel = MailUtility.getEMailConfigurationList(configurationService.getConfigurationList());
                sendActivationMail(mailEnum, mimeMultipart, mailDefaultConfigurationModel.getMailusername(), email);
                return "/pages/public/login.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Email address provided.",""));
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SecurityBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            java.util.logging.Logger.getLogger(SecurityBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
                    java.util.logging.Logger.getLogger(SecurityBean.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

    //TODO Use it for user creation
    // do the appropriate changes to user update or send random password to parameters
    // if the password is provided, user will not get updated
    public void signupPasswordMailNotification(String userName, String randomPassword) throws Exception {
        MailEnum mailEnum = MailEnum.SIGN_UP_VERIFICATION;
        String summary = "User created successfully Please check your mail for further details";
        String[] email = {username};
        sendPasswordNotificationMail(mailEnum, summary, randomPassword, userName, email);
    }

    private void sendPasswordNotificationMail(MailEnum mailEnum, String summary, String randomPassword, String firstName, String[] senderMailAddress) throws Exception {
        Mail mail = MailPreparer.generateForgotPasswordMail(
                EmailConstant.ADMIN_SUPPORT_EMAIL,
                EmailConstant.ADMIN_EMAIL_SENDER_NAME,
                senderMailAddress,
                firstName,
                randomPassword,
                mailEnum
        );
        mailIntegration.sendHtmlMail(mail, MailUtility.getJavaMailSender(configurationService.getConfigurationList()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    private String updatedUserPassword(User userObj) {
        String randomPassword = new SessionIdentifierGenerator().randomPassword();
        try {
            userObj.setPassword(passwordEncoder.encode(randomPassword));
            userObj.setIsActive(true);
            userService.update(userObj);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger(SecurityBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return randomPassword;
    }

}
