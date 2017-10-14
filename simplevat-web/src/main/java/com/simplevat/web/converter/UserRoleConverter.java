/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.converter;

import com.simplevat.entity.Role;
import com.simplevat.service.RoleService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class UserRoleConverter implements Converter {

    @Autowired
    private RoleService roleService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            Role role = roleService.findByPK(Integer.parseInt(string));
            return role;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o instanceof Role) {
            Role role = (Role) o;
            return role.getRoleCode().toString();
        }
        return null;
    }
    
}
