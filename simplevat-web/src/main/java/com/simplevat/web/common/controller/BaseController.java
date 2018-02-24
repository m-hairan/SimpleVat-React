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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author uday
 */
public abstract class BaseController {

    public BaseController(ModuleName moduleName) {
        System.out.println("PageAccess=======" + PageAccessControl.hasAccess(moduleName));
        if (!PageAccessControl.hasAccess(moduleName)) {
            try {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                RequestDispatcher dd = request.getRequestDispatcher("/pages/public/common/unauthorized.xhtml");
                dd.forward(request,
                        (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse());
            } catch (Exception ex) {
                Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}
