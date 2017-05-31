package com.simplevat.controller.login;

import com.simplevat.entity.Mail;
import com.simplevat.entity.MailEnum;
import com.simplevat.entity.User;
import com.simplevat.integration.MailIntegration;
import com.simplevat.integration.MailPreparer;
import com.simplevat.service.UserService;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.io.Serializable;
import java.util.Optional;


@Controller
@ManagedBean(name = "securityBean")
@RequestScoped
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
    private MailIntegration mailIntegration;

    @Autowired
    private UserService userService;

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
            return "/pages/secure/user/home.xhtml";
        } catch (final Exception e) {
            LOGGER.error("Error log in " + e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Invalid login", "Bad Credential. Try again"));
        }
        return null;
    }


    public String forgotPassword() throws Exception {

        Optional<User> user = userService.getUserByEmail(username);
        if (user.isPresent()) {
            User userObj = user.get();
            String randomPassword = updatedUserPassword(userObj);
            Mail mail = MailPreparer.generateForgotPasswordMail(
                    ADMIN_EMAIL,
                    ADMIN_USERNAME,
                    username,
                    userObj.getFirstName(),
                    randomPassword,
                    MailEnum.FORGOT_PASSWORD
            );
            mailIntegration.sendHtmlMail(mail);
//            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/public/login.xhtml");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password reset successfully. Please check your mail for further details"));
            return "/pages/public/login.xhtml";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Email address provided."));
        }
        return null;
    }

    private String updatedUserPassword(User userObj) {
        String randomPassword = new SessionIdentifierGenerator().randomPassword();
        userObj.setPassword(passwordEncoder.encode(randomPassword));
        userService.updateUser(userObj);
        return randomPassword;
    }


}
