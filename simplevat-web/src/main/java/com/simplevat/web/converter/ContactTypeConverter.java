package com.simplevat.web.converter;

import com.simplevat.web.constant.ContactTypeConstant;
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
            ContactType contactType = getContactType(Integer.parseInt(string));
            return contactType;
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
        for (ContactType contactType : getContactTypeList()) {
            if (contactType.getId() == id) {
                return contactType;
            }
        }
        return new ContactType();
    }

    private List<ContactType> getContactTypeList() {
        List<ContactType> contactTypeList = new ArrayList<>();
        contactTypeList.add(new ContactType(ContactTypeConstant.VENDOR, "Vendor"));
        contactTypeList.add(new ContactType(ContactTypeConstant.CUSTOMER, "Customer"));
        contactTypeList.add(new ContactType(ContactTypeConstant.EMPLOYEE, "Employee"));
        return contactTypeList;
    }

}
