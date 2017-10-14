/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.converter;

import com.simplevat.entity.bankaccount.BankAccountType;
import com.simplevat.service.BankAccountTypeService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author uday
 */
@Service
public class BankAccountTypeConverter implements Converter {

    @Autowired
    private BankAccountTypeService bankAccountTypeService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            BankAccountType bankAccountType = bankAccountTypeService.findByPK(Integer.parseInt(string));
            return bankAccountType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o instanceof BankAccountType) {
            BankAccountType bankAccountType = (BankAccountType) o;
            return bankAccountType.getId().toString();
        }
        return null;
    }

}
