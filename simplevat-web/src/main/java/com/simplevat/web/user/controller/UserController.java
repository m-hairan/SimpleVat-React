package com.simplevat.web.user.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.Mail;
import com.simplevat.entity.MailEnum;
import com.simplevat.entity.Role;
import com.simplevat.entity.Title;
import com.simplevat.entity.User;
import com.simplevat.integration.MailIntegration;
import com.simplevat.security.ContextUtils;
import com.simplevat.security.UserContext;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.RoleService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.common.controller.StreamedContentSessionController;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.invoice.controller.InvoiceMailController;
import com.simplevat.web.invoice.controller.InvoiceReminderController;
import com.simplevat.web.user.model.UserDTO;
import com.simplevat.web.utils.FacesUtil;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Uday
 */
@Controller
@SpringScopeView
public class UserController implements Serializable {

    private static final long serialVersionUID = -7388960716549948523L;
    private static final String ADMIN_EMAIL = "no-reply@simplevat.com";
    private static final String ADMIN_USERNAME = "Simplevat Admin";

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private MailIntegration mailIntegration;
    @Autowired
    private UserServiceNew userService;

    @Autowired
    private RoleService roleService;

    @Setter
    @Getter
    private UserDTO selectedUser;

    @Getter
    private List<Title> titles;

    @Getter
    @Setter
    private UploadedFile newProfilePic;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String fileName;

    @Getter
    private List<Role> roleList;

    @Getter
    @Setter
    StreamedContentSessionController streamContent;

    @Getter
    private boolean renderProfilePic;

    @PostConstruct
    public void init() {
        newProfilePic = null;
        password = null;
        streamContent = new StreamedContentSessionController();
        String userId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("user");

        if (userId != null) {
            selectedUser = new UserDTO();
            User user = userService.findByPK(Integer.parseInt(userId));
            BeanUtils.copyProperties(user, selectedUser);
            if (selectedUser.getProfileImageBinary() != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("STREAMED_CONTENT_USER_PIC", selectedUser.getProfileImageBinary());
                renderProfilePic = true;
            }
        } else {
            selectedUser = new UserDTO();
            Role role = roleService.getDefaultRole();
            if (role != null) {
                selectedUser.setRole(role);
            }
        }

    }

    public List<Role> comoleteRole() {
        return roleService.getRoles();
    }

    public List<Title> filterTitle(String titleStr) {
        List<Title> titleSuggestion = new ArrayList<>();
        Iterator<Title> titleIterator = this.titles.iterator();

        while (titleIterator.hasNext()) {
            Title title = titleIterator.next();
            if (title.getTitleDescription() != null
                    && !title.getTitleDescription().isEmpty()
                    && title.getTitleDescription().toUpperCase().contains(titleStr.toUpperCase())) {
                titleSuggestion.add(title);
            }
        }
        return titleSuggestion;
    }

    public StreamedContent getProfilePic() {
        if (newProfilePic != null) {
            try {
                return new DefaultStreamedContent(newProfilePic.getInputstream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (selectedUser.getProfileImageBinary() != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(selectedUser.getProfileImageBinary());
            return new DefaultStreamedContent(inputStream);
        }

        return null;
    }

    public void handleFileUpload(FileUploadEvent event) {
        newProfilePic = event.getFile();
        selectedUser.setProfileImageBinary(event.getFile().getContents());
        fileName = event.getFile().getFileName();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("STREAMED_CONTENT_USER_PIC", event.getFile().getContents());
        renderProfilePic = true;
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String save() {
        try {

            if (password != null && !password.trim().isEmpty()) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(password);
                selectedUser.setPassword(encodedPassword);
            }
            User user = new User();
            BeanUtils.copyProperties(selectedUser, user);
            UserContext userContext = ContextUtils.getUserContext();
            user.setCompany(FacesUtil.getLoggedInUser().getCompany());
            user.setCreatedBy(userContext.getUserId());
            user.setLastUpdatedBy(userContext.getUserId());
//            if (selectedUser.getRoleCode() != null) {
//                Role role = roleService.findByPK(selectedUser.getRoleCode());
//                user.setRole(role);
//            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            if (user.getUserId() == null) {
                userService.persist(user);
                sendNewUserMail(user, password);
                context.addMessage(null, new FacesMessage("User Profile added successfully"));
            } else {
                userService.update(user, user.getUserId());
                context.addMessage(null, new FacesMessage("User Profile updated successfully"));
            }
            return "list?faces-redirect=true";
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private void sendNewUserMail(User user, String passwordToMail) {

        MailEnum mailEnum = MailEnum.NEW_USER_CREATED;
        String summary = "Hello " + user.getFirstName() + " " + user.getLastName()
                + "<br>Your user account created successfully.<br> Your username is \""
                + user.getUserEmail() + "\".<br> Your temparory password is \"" + passwordToMail + "\".";
        try {
            sendMailToUser(mailEnum, summary, user.getUserEmail());
        } catch (Exception ex) {
            Logger.getLogger(InvoiceReminderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendMailToUser(MailEnum mailEnum, String summary, String senderMailAddress) {
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
                Logger.getLogger(InvoiceMailController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
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
