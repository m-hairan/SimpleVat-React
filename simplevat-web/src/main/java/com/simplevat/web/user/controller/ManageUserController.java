package com.simplevat.web.user.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.User;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.user.model.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
    
    @Setter
    @Getter
    private UserDTO selectedUser;
    
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
    public String openUserPage(){
       return "user?faces-redirect=true"; 
    }
    public String editUser(){
        return "user?faces-redirect=true&user="+selectedUser.getUserId();
    }
    
    public void deleteUser(){
        userService.delete(selectedUser, selectedUser.getUserId());
    }
}
