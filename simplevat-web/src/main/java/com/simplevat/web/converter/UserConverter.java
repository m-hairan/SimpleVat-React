package com.simplevat.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.User;
import com.simplevat.service.UserServiceNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bhavin.panchani
 *
 */
@Service
public class UserConverter implements Converter {

    @Autowired
    private UserServiceNew userServiceNew;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                User user = userServiceNew.findByPK(Integer.parseInt(string));
                return user;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o instanceof User) {
            User user = (User) o;
            return user.getUserId().toString();
        }
        return null;
    }

}
