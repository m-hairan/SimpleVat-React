package com.simplevat.web.converter;

import com.simplevat.entity.Contact;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.bankaccount.BankAccountService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hiren
 */
@Service
public class BankAccountConverter implements Converter {

    @Autowired
    private BankAccountService bankAccountService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            BankAccount bankAccount = bankAccountService.getBankAccountById(Integer.parseInt(string));
            return bankAccount;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof BankAccount) {
            BankAccount bankAccount = (BankAccount) o;
            return bankAccount.getBankAccountId().toString();
        }
        return null;
    }

}
