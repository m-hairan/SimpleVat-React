package com.simplevat.web.view.user;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.simplevat.entity.User;
import com.simplevat.service.UserService;
import com.simplevat.web.model.user.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author Uday
 */
@Controller
@RequestScope
public class ManageUserController {

    @Autowired
    private UserService userService;
    
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
        userService.deleteUser(selectedUser);
    }
}
