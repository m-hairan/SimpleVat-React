package com.simplevat.converter;

import com.simplevat.entity.Contact;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author hiren
 */
@FacesConverter("contactConverter")
public class ContactConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            Contact contact = new Contact();
            contact.setContactId(Integer.parseInt(string));
            return contact;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof Contact) {
            Contact contact = (Contact) o;
            return Integer.toString(contact.getContactId());
        }
        return o.toString();
    }

}
