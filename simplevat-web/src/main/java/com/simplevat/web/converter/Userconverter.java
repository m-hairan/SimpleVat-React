package com.simplevat.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.User;

/**
 * @author bhavin.panchani
 *
 */
@FacesConverter("userConverter")
public class Userconverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (string != null && !string.isEmpty()) {
			User user = new User();
			user.setUserId(Integer.parseInt(string));
			return user;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		
		if (o instanceof User) {
			User user = (User) o;
			return Integer.toString(user.getUserId());
		}
		return null;
	}

}
