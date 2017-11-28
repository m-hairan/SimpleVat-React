package com.simplevat.web.login.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.Mail;
import com.simplevat.entity.MailEnum;
import com.simplevat.entity.User;
import com.simplevat.integration.MailIntegration;
import com.simplevat.integration.MailPreparer;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.utils.SessionIdentifierGenerator;
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
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Controller
//@ManagedBean(name = "securityBean")
@SpringScopeView
//@Qualifier("securityBean")
public class SecurityBean implements PhaseListener, Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityBean.class);

    private static final long serialVersionUID = -7388960716549948523L;
    private static final String ADMIN_EMAIL = "no-reply@simplevat.com";
    private static final String ADMIN_USERNAME = "Simplevat Admin";

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;

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
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return "/pages/secure/home.xhtml?faces-redirect=true";
        } catch (final Exception e) {
            LOGGER.error("Error log in " + e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Invalid login", "Bad Credential. Try again"));
        }
        return null;
    }

    public String forgotPassword() throws Exception {
        MailEnum mailEnum = MailEnum.FORGOT_PASSWORD;
        String summary = "Password reset successfully. Please check your mail for further details";
        Optional<User> user = userService.getUserByEmail(username);
        if (user.isPresent()) {
            User userObj = user.get();
            String firstName = userObj.getFirstName();
            String randomPassword = updatedUserPassword(userObj);
            String[] email = {username};
            sendPasswordNotificationMail(mailEnum, summary, randomPassword, firstName, email);
            return "/pages/public/login.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Email address provided."));
        }
        return null;
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
                ADMIN_EMAIL,
                ADMIN_USERNAME,
                senderMailAddress,
                firstName,
                randomPassword,
                mailEnum
        );
        mailIntegration.sendHtmlMail(mail, getJavaMailSender(configurationService.getConfigurationList()));
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

    private JavaMailSender getJavaMailSender(List<Configuration> configurationList) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setProtocol("smtp");
        sender.setHost(configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_HOST)).findAny().get().getValue());
        sender.setPort(Integer.parseInt(configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_PORT)).findAny().get().getValue()));
        sender.setUsername(configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_USERNAME)).findAny().get().getValue());
        sender.setPassword(configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_PASSWORD)).findAny().get().getValue());

        Properties mailProps = new Properties();
        mailProps.put("mail.smtps.auth", configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_SMTP_AUTH)).findAny().get().getValue());
        mailProps.put("mail.smtp.starttls.enable", configurationList.stream().filter(host -> host.getName().equals(ConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE)).findAny().get().getValue());
        mailProps.put("mail.smtp.debug", "true");

        sender.setJavaMailProperties(mailProps);

        return sender;
    }

}
