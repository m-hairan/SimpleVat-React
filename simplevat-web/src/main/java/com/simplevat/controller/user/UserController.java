package com.simplevat.controller.user;

import com.simplevat.entity.User;
import com.simplevat.exception.UnauthorizedException;
import com.simplevat.security.ContextUtils;
import com.simplevat.security.UserContext;
import com.simplevat.service.UserService;
import com.simplevat.user.model.UserModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Hiren
 */
@RequestScoped
@ManagedBean
@Component
public class UserController {

    private UserModel currentUser;

    private User currentUserEntity;

    @Autowired
    private UserService userService;

    @Value("${file.upload.location}")
    private String fileLocation;

    @PostConstruct
    void initController() {

        try {
            final UserContext context = ContextUtils.getUserContext();
            currentUserEntity = userService.getUser(context.getUserId());
        } catch (UnauthorizedException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (currentUserEntity != null) {
            currentUser = convertToModel(currentUserEntity);
        } else {
            currentUser = null;
        }

    }

    public void prefillData() throws IOException {
        initController();
    }

    public void update() throws UnauthorizedException {
        currentUser = getCurrentUser();
        currentUserEntity = convertToEntity(currentUser);
        if (null != currentUser.getProfileImage()
                && currentUser.getProfileImage().getSize() > 0) {
            storeUploadedFile(fileLocation);
        }
        userService.updateUser(currentUserEntity);
    }

    private void storeUploadedFile(String fileLocation) throws UnauthorizedException {
        String tomcatHome = System.getProperty("catalina.base");
        currentUser = getCurrentUser();
        String fileUploadAbsolutePath = tomcatHome.concat(fileLocation);
        File filePath = new File(fileUploadAbsolutePath);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        Path dataFolder = Paths.get(fileUploadAbsolutePath);

        UploadedFile uploadedFile = currentUser.getProfileImage();

        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

        try {

            Path file = Files.createTempFile(dataFolder, "Profile_" + currentUser.getUserId() + filename, "_" + System.currentTimeMillis() + "." + extension);
            InputStream in = uploadedFile.getInputstream();
            Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);

            currentUserEntity.setProfileImagePath(fileLocation + "/" + file.getFileName());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Nonnull
    private User convertToEntity(@Nonnull final UserModel userModel) {
        final LocalDateTime dob = LocalDateTime.ofInstant(userModel.getDateOfBirth().toInstant(), ZoneId.systemDefault());
        final User user = userService.getUser(userModel.getUserId());

        // todo: change when company module is done.
        user.setCompanyId(1);
        user.setCreatedBy(0);
        user.setDateOfBirth(dob);
        user.setDeleteFlag(Boolean.FALSE);
        user.setUserEmail(userModel.getUserEmailId());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setLastUpdatedBy(0);
        return user;
    }

    @Nonnull
    private UserModel convertToModel(@Nonnull final User user) {
        final UserModel userModel = new UserModel();

        userModel.setUserId(user.getUserId());
        // todo: chnage when company module is done.
        userModel.setDateOfBirth(null != user.getDateOfBirth() ? Date.from(user.getDateOfBirth().atZone(ZoneId.systemDefault()).toInstant()) : null);
        userModel.setUserEmailId(user.getUserEmail());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());

        return userModel;
    }

    public StreamedContent getProfilePic() throws UnauthorizedException {
        if (currentUserEntity == null) {
            final UserContext context = ContextUtils.getUserContext();
            currentUserEntity = userService.getUser(context.getUserId());
        }
        final String attachmentPath = currentUserEntity.getProfileImagePath();
        if (attachmentPath != null && !attachmentPath.isEmpty()) {
            String tomcatHome = System.getProperty("catalina.base");
            File profilePic = new File(tomcatHome.concat(attachmentPath));
            try {
                final InputStream inputStream = new FileInputStream(profilePic);
                return new DefaultStreamedContent(inputStream,
                        new MimetypesFileTypeMap().getContentType(profilePic),
                        profilePic.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public UserModel getCurrentUser() throws UnauthorizedException {
        if (currentUserEntity != null) {
            currentUser = convertToModel(currentUserEntity);
        } else {
            final UserContext context = ContextUtils.getUserContext();
            currentUserEntity = userService.getUser(context.getUserId());
            currentUser = convertToModel(currentUserEntity);
        }
        return currentUser;
    }

}
