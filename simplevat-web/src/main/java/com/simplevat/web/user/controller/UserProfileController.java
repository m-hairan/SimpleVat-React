package com.simplevat.web.user.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.User;
import com.simplevat.web.exceptions.UnauthorizedException;
import com.simplevat.security.ContextUtils;
import com.simplevat.security.UserContext;
import com.simplevat.service.UserServiceNew;
import com.simplevat.user.model.UserModel;
import com.simplevat.web.utils.FacesUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Uday
 */
@Controller
@SpringScopeView
public class UserProfileController implements Serializable {

    @Setter
    private UserModel currentUser;
    @Getter
    @Setter
    private User currentUserEntity;

    @Autowired
    private UserServiceNew userService;

    @Getter
    @Setter
    String fileName;

    @Getter
    private boolean renderProfilePic;

    @Getter
    private boolean editMode;

    @Getter
    @Setter
    private String password;

    @PostConstruct
    public void init() {
        editMode = false;
        currentUserEntity = userService.findByPK(FacesUtil.getLoggedInUser().getUserId());
        if (currentUserEntity != null) {
            currentUser = convertToModel(currentUserEntity);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("STREAMED_CONTENT_PROFILE_PIC", currentUser.getProfileImageBinary());
            renderProfilePic = true;
            editMode = true;
        } else {
            currentUser = null;
        }
    }

    public String update() throws UnauthorizedException {
        try {
            if (password != null && !password.trim().isEmpty()) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(password);
                currentUser.setPassword(encodedPassword);
            }
            currentUserEntity = convertToEntity(currentUser);

            if (currentUser.getUserId() > 0) {
                userService.update(currentUserEntity, currentUser.getUserId());
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("", "User Profile updated successfully"));
            return "/pages/secure/account/index.xhtml?faces-redirect=true";
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Nonnull
    private User convertToEntity(@Nonnull final UserModel userModel) {
        User user = new User();
        if (userModel.getDateOfBirth() != null) {
            final LocalDateTime dob = userModel.getDateOfBirth() != null ? LocalDateTime.ofInstant(userModel.getDateOfBirth().toInstant(), ZoneId.systemDefault()) : null;
            user.setDateOfBirth(dob);
        }
        //  User user = userService.getUserByEmail(userModel.getUserEmailId()).get();
        user.setUserId(userModel.getUserId());
        user.setCompany(userModel.getCompany());
        user.setDeleteFlag(userModel.getDeleteFlag());
        user.setUserEmail(userModel.getUserEmailId());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setCreatedBy(userModel.getCreatedBy());
        user.setRole(userModel.getRole());
        user.setIsActive(Boolean.TRUE);
        user.setPassword(userModel.getPassword());
        user.setVersionNumber(userModel.getVersionNumber());
        user.setCreatedDate(userModel.getCreatedDate());
        user.setProfileImageBinary(userModel.getProfileImageBinary());
        return user;
    }

    @Nonnull
    private UserModel convertToModel(@Nonnull final User user) {

        final UserModel userModel = new UserModel();

        userModel.setUserId(user.getUserId());
        userModel.setCompany(user.getCompany());
        // todo: chnage when company module is done.
        userModel.setDateOfBirth(null != user.getDateOfBirth() ? Date.from(user.getDateOfBirth().atZone(ZoneId.systemDefault()).toInstant()) : null);
        userModel.setUserEmailId(user.getUserEmail());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setDeleteFlag(user.getDeleteFlag());
        userModel.setPassword(user.getPassword());
        userModel.setRole(user.getRole());
        userModel.setVersionNumber(user.getVersionNumber());
        userModel.setCreatedBy(user.getCreatedBy());
        userModel.setCreatedDate(user.getCreatedDate());
        userModel.setProfileImageBinary(user.getProfileImageBinary());

        return userModel;
    }

    public void handleFileUpload(FileUploadEvent event) {
        currentUser.setProfileImageBinary(event.getFile().getContents());
        fileName = event.getFile().getFileName();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("STREAMED_CONTENT_PROFILE_PIC", event.getFile().getContents());
        renderProfilePic = true;
        FacesMessage message = new FacesMessage("", "Image Uploaded successfully.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public UserModel getCurrentUser() throws UnauthorizedException {
        if (currentUser == null) {
            final UserContext context = ContextUtils.getUserContext();
            currentUserEntity = userService.findByPK(context.getUserId());
            currentUser = convertToModel(currentUserEntity);
        }
        return currentUser;
    }

    public String prefillData() {
        return "";
    }
}
