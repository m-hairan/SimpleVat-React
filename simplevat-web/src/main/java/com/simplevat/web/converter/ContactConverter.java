package com.simplevat.web.converter;

import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hiren
 */
@FacesConverter("contactConverter")
public class ContactConverter implements Converter {

    @Autowired
    private ContactService contactService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            Contact contact = contactService.getContact(Integer.parseInt(string));
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
