package com.simplevat.web.user.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.User;
import com.simplevat.web.exception.UnauthorizedException;
import com.simplevat.security.ContextUtils;
import com.simplevat.security.UserContext;
import com.simplevat.service.UserServiceNew;
import com.simplevat.user.model.UserModel;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.PhaseId;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Uday
 */
@Controller
//@SpringScopeView
@SessionScope
public class UserProfileController implements Serializable{

    @Getter
    @Setter
    private UserModel currentUser;
    @Getter
    @Setter
    private User currentUserEntity;

    @Autowired
    private UserServiceNew userService;

    @Autowired
    private UserServiceNew userServiceNew;

    @Value("${file.upload.location}")
    private String fileLocation;

    @PostConstruct
    public void init() {
        try {
            final UserContext context = ContextUtils.getUserContext();
            currentUserEntity = userService.findByPK(context.getUserId());
        } catch (UnauthorizedException ex) {
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (currentUserEntity != null) {
            currentUser = convertToModel(currentUserEntity);
        } else {
            currentUser = null;
        }
    }

    public void update() throws UnauthorizedException {
        try {
            try {

                byte[] bytes = IOUtils.toByteArray(currentUser.getProfileImage().getInputstream());
                currentUser.setProfileImageBinary(bytes);

            } catch (IOException ex) {
                Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }

            currentUserEntity = convertToEntity(currentUser);

            if (currentUser.getUserId() > 0) {
                userService.update(currentUserEntity, currentUser.getUserId());
            }
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Profile updated successfully"));
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Nonnull
    private User convertToEntity(@Nonnull final UserModel userModel) {
        final LocalDateTime dob = LocalDateTime
                .ofInstant(userModel.getDateOfBirth().toInstant(), ZoneId.systemDefault());
        //  User user = userService.getUserByEmail(userModel.getUserEmailId()).get();

        User user = new User();
        user.setUserId(userModel.getUserId());
        user.setCompanyId(userModel.getCompanyId());
        user.setDateOfBirth(dob);
        user.setDeleteFlag(userModel.getDeleteFlag());
        user.setUserEmail(userModel.getUserEmailId());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setCreatedBy(userModel.getCreatedBy());
        user.setIsActive(Boolean.TRUE);
        user.setPassword(userModel.getPassword());
        user.setProfileImagePath(userModel.getProfileImagePath());
        user.setVersionNumber(userModel.getVersionNumber());
        user.setCreatedDate(userModel.getCreatedDate());
        user.setRoleCode(userModel.getRoleCode());
        user.setProfileImageBinary(userModel.getProfileImageBinary());
        return user;
    }

    @Nonnull
    private UserModel convertToModel(@Nonnull final User user) {

        final UserModel userModel = new UserModel();

        userModel.setUserId(user.getUserId());
        userModel.setCompanyId(user.getCompanyId());
        // todo: chnage when company module is done.
        userModel.setDateOfBirth(null != user.getDateOfBirth() ? Date.from(user.getDateOfBirth().atZone(ZoneId.systemDefault()).toInstant()) : null);
        userModel.setUserEmailId(user.getUserEmail());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setDeleteFlag(user.getDeleteFlag());
        userModel.setPassword(user.getPassword());
        userModel.setVersionNumber(user.getVersionNumber());
        userModel.setCreatedBy(user.getCreatedBy());
        userModel.setProfileImagePath(user.getProfileImagePath());
        userModel.setCreatedDate(user.getCreatedDate());
        userModel.setRoleCode(user.getRoleCode());
        userModel.setProfileImageBinary(user.getProfileImageBinary());

        return userModel;
    }

    public StreamedContent getProfilePic(){
        if (currentUser.getProfileImageBinary() != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(currentUser.getProfileImageBinary());
            return new DefaultStreamedContent(inputStream);
        }
        return null;
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
