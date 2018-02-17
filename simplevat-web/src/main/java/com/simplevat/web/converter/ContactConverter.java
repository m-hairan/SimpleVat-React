package com.simplevat.web.converter;

import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hiren
 */
@Service
public class ContactConverter implements Converter {

    @Autowired
    private ContactService contactService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                Integer contactId = Integer.parseInt(string);
                Contact contact = contactService.getContact(contactId);
                return contact;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof Contact) {
            Contact contact = (Contact) o;
            System.out.println("contact==================" + contact);
            return Integer.toString(contact.getContactId());
        }
        return null;
    }

}
