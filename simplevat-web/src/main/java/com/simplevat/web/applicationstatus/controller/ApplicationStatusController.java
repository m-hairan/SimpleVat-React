/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.applicationstatus.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.User;
import com.simplevat.service.UserServiceNew;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author h
 */
@Controller
@SpringScopeView
public class ApplicationStatusController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private UserServiceNew userService;

    @Getter
    private Integer userCount = 0;

    @PostConstruct
    public void init() {

        List<User> userList = userService.findAll();
        if (userList != null && !userList.isEmpty()) {
            userCount = userList.size();
        }

    }

}
