package com.simplevat.web.converter;

import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.contact.controller.ContactUtil;
import com.simplevat.web.contact.model.ContactType;
import com.simplevat.web.financialreport.FinancialPeriod;
import com.simplevat.web.financialreport.FinancialPeriodHolder;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.stereotype.Service;

/**
 * @author hiren
 */
@Service
public class ContactTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                Integer contactTypeId = Integer.parseInt(string);
                ContactType contactType = getContactType(contactTypeId);
                return contactType;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof ContactType) {
            ContactType contactType = (ContactType) o;
            return contactType.getId().toString();
        }
        return null;
    }

    private ContactType getContactType(int id) {
        for (ContactType contactType : ContactUtil.contactTypeList()) {
            if (contactType.getId() == id) {
                return contactType;
            }
        }
        return new ContactType();
    }

}
