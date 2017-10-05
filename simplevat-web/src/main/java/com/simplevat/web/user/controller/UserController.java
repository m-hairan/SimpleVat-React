package com.simplevat.web.user.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Role;
import com.simplevat.entity.Title;
import com.simplevat.entity.User;
import com.simplevat.web.exception.UnauthorizedException;
import com.simplevat.security.ContextUtils;
import com.simplevat.security.UserContext;
import com.simplevat.service.RoleService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.user.model.UserDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Uday
 */
@Controller
@SpringScopeView
public class UserController implements Serializable {

    @Autowired
    private UserServiceNew userService;

    @Autowired
    private RoleService roleService;

    @Setter
    @Getter
    private UserDTO selectedUser;

    @Getter
    private List<Title> titles;

    @Value("${file.upload.location}")
    private String fileLocation;

    @Getter
    @Setter
    private UploadedFile newProfilePic;

    @Getter
    private boolean editMode;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String fileName;

    @Getter
    private List<Role> roleList;

    @PostConstruct
    public void init() {
        newProfilePic = null;
        password = null;
        editMode = false;
        selectedUser = new UserDTO();
        selectedUser.setRole(new Role());
        String userId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("user");

        if (userId != null) {
            User user = userService.findByPK(Integer.parseInt(userId));
            BeanUtils.copyProperties(user, selectedUser);
            editMode = true;
        }
        roleList = roleService.getRoles();
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
        return null;
    }

    public void handleFileUpload(FileUploadEvent event) {
        newProfilePic = event.getFile();
        selectedUser.setProfileImageBinary(event.getFile().getContents());
        fileName = event.getFile().getFileName();
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String save() {
        try {
            if (null != newProfilePic) {
                storeUploadedFile(fileLocation, newProfilePic, selectedUser);
            }
            if (password != null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(password);
                selectedUser.setPassword(encodedPassword);
            }
            User user = new User();
            BeanUtils.copyProperties(selectedUser, user);
            UserContext userContext = ContextUtils.getUserContext();
            user.setCreatedBy(userContext.getUserId());
            user.setLastUpdatedBy(userContext.getUserId());
            if (selectedUser.getRoleCode() != null) {
                Role role = roleService.findByPK(selectedUser.getRoleCode());
                user.setRole(role);
            }

            if (!editMode) {
                userService.persist(user);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Profile added successfully"));
            } else {
                userService.update(user, user.getUserId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Profile updated successfully"));
            }
            return "list?faces-redirect=true";
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private void storeUploadedFile(String fileLocation, UploadedFile uploadedFile, User user) throws UnauthorizedException {
        String tomcatHome = System.getProperty("catalina.base");
        String fileUploadAbsolutePath = tomcatHome.concat(fileLocation);
        File filePath = new File(fileUploadAbsolutePath);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        Path dataFolder = Paths.get(fileUploadAbsolutePath);

        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

        try {

            Path file = Files.createTempFile(dataFolder, "Profile_" + selectedUser.getUserId() + filename, "_" + System.currentTimeMillis() + "." + extension);
            InputStream in = uploadedFile.getInputstream();
            Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);

            user.setProfileImagePath(fileLocation + "/" + file.getFileName());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
