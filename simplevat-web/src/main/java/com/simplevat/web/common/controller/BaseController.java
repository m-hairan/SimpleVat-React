/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.common.controller;

import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.exceptions.UnauthorizedException;
import com.simplevat.web.utils.PageAccessControl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author uday
 */
public abstract class BaseController {

    public BaseController(ModuleName moduleName) {
        System.out.println("PageAccess=======" + PageAccessControl.hasAccess(moduleName));
        if (!PageAccessControl.hasAccess(moduleName)) {
            try {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/pages/public/common/unauthorized.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}
