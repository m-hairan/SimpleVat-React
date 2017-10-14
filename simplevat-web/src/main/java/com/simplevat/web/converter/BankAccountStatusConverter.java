package com.simplevat.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.bankaccount.BankAccountStatus;
import com.simplevat.service.bankaccount.BankAccountStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bhavin.panchani
 *
 */
@Service
public class BankAccountStatusConverter implements Converter {

    @Autowired
    private BankAccountStatusService bankAccountStatusService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            BankAccountStatus bankAccountStatus = bankAccountStatusService.getBankAccountStatus(Integer.parseInt(string));
            return bankAccountStatus;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o instanceof BankAccountStatus) {
            BankAccountStatus bankAccountStatus = (BankAccountStatus) o;
            return Integer.toString(bankAccountStatus.getBankAccountStatusCode());
        }
        return null;
    }

}
