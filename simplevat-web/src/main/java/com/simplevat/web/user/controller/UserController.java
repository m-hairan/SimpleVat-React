package com.simplevat.web.user.controller;

import com.github.javaplugs.jsf.SpringScopeView;
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
import com.simplevat.web.constant.EmailConstant;
import com.simplevat.web.user.model.UserDTO;
import com.simplevat.web.utils.FacesUtil;
import com.simplevat.web.utils.FileUtility;
import com.simplevat.web.utils.MailConfigurationModel;
import com.simplevat.web.utils.MailUtility;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String NEW_USER_EMAIL_TEMPLATE_FILE = "/WEB-INF/emailtemplate/new-user-created-template.html";

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
        FacesMessage message = new FacesMessage("Upload successful", "Image Uploaded Successfully.");
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
                context.addMessage(null, new FacesMessage("Successful","User Profile added successfully"));
            } else {
                userService.update(user, user.getUserId());
                context.addMessage(null, new FacesMessage("Successful","User Profile updated successfully"));
            }
            return "list?faces-redirect=true";
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String sendNewUserMail(User user, String passwordToMail) throws Exception {
        try {
            MailEnum mailEnum = MailEnum.NEW_USER_CREATED;
            String recipientName = user.getFirstName();
            String userMail = user.getUserEmail();
            Object[] args = {recipientName, userMail, passwordToMail};
            String pathname = FacesContext.getCurrentInstance().getExternalContext().getRealPath(NEW_USER_EMAIL_TEMPLATE_FILE);
            MessageFormat msgFormat = new MessageFormat(FileUtility.readFile(pathname));
            MimeMultipart mimeMultipart = FileUtility.getMessageBody(msgFormat.format(args));
            String[] email = {userMail};
            MailConfigurationModel mailDefaultConfigurationModel = MailUtility.getEMailConfigurationList(configurationService.getConfigurationList());
            sendActivationMail(mailEnum, mimeMultipart, mailDefaultConfigurationModel.getMailusername(), email);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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
                    java.util.logging.Logger.getLogger(UserController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

}
