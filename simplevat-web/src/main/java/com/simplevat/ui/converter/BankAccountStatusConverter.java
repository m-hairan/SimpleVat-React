package com.simplevat.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.bankaccount.BankAccountStatus;

/**
 * @author bhavin.panchani
 *
 */
@FacesConverter("bankAccountStatusConverter")
public class BankAccountStatusConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (string != null && !string.isEmpty()) {
			BankAccountStatus bankAccountStatus = new BankAccountStatus();
			bankAccountStatus.setBankAccountStatusCode(Integer.parseInt(string));
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
