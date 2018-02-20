package com.simplevat.web.user.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Company;
import com.simplevat.entity.Contact;
import com.simplevat.entity.User;
import com.simplevat.service.CompanyService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.user.model.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Uday
 */
@Controller
@SpringScopeView
public class ManageUserController {

    @Autowired
    private UserServiceNew userService;
    @Autowired
    private CompanyService companyService;
    @Setter
    @Getter
    private UserDTO selectedUser;

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private Company company;
    @Setter
    @Getter
    private List<UserDTO> userDTOList;

    @PostConstruct
    void initController() {
        List<User> userList = userService.findAll();
        userDTOList = new ArrayList();
        for (User user : userList) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOList.add(userDTO);
        }
    }

    public String openUserPage() {
        return "user?faces-redirect=true";
    }

    public String editUser() {
        return "user?faces-redirect=true&user=" + selectedUser.getUserId();
    }

    public String deleteUser() {

        user = getUserFromUserDTO(selectedUser);
        user.setDeleteFlag(true);
        userService.update(user);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Successful","Contact deleted SuccessFully"));

        return "list?faces-redirect=true";
    }

    private User getUserFromUserDTO(UserDTO selectedUser) {
        User user = new User();
        user.setCompany(selectedUser.getCompany());
        user.setCreatedBy(selectedUser.getCreatedBy());
        user.setCreatedDate(selectedUser.getCreatedDate());
        user.setDateOfBirth(selectedUser.getDateOfBirth());
        user.setDeleteFlag(selectedUser.getDeleteFlag());
        user.setFirstName(selectedUser.getFirstName());
        user.setIsActive(selectedUser.getIsActive());
        user.setLastName(selectedUser.getLastName());
        user.setLastUpdateDate(selectedUser.getLastUpdateDate());
        user.setLastUpdatedBy(selectedUser.getLastUpdatedBy());
        user.setPassword(selectedUser.getPassword());
        user.setRole(selectedUser.getRole());
        user.setUserEmail(selectedUser.getUserEmail());
        user.setUserId(selectedUser.getUserId());
        user.setVersionNumber(selectedUser.getVersionNumber());
        return user;
    }
}
