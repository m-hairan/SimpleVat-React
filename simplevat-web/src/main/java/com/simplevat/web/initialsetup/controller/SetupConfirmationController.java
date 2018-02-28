/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.initialsetup.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 *
 * @author h
 */
@Controller
@SpringScopeView
public class SetupConfirmationController implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(SetupConfirmationController.class);
    private static final long serialVersionUID = -7388960716549948523L;

    @Getter
    @Setter
    String baseUrl;

    @Getter
    @Setter
    String userLoginId;
    
    @Getter
    @Setter
    String name;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        baseUrl = context.getExternalContext().getRequestParameterMap().get("baseUrl");
        name = context.getExternalContext().getRequestParameterMap().get("name");
        userLoginId = context.getExternalContext().getRequestParameterMap().get("userLoginId");
    }

    public void finish() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/public/login.xhtml");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SetupConfirmationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
