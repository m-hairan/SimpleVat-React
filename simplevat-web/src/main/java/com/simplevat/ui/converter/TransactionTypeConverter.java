package com.simplevat.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.bankaccount.TransactionType;

/**
 * @author bhavin.panchani
 *
 */
@FacesConverter("transactionTypeConverter")
public class TransactionTypeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (string != null && !string.isEmpty()) {
			TransactionType transactionType = new TransactionType();
			transactionType.setTransactionTypeCode(Integer.parseInt(string));
			return transactionType;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		
		if (o instanceof TransactionType) {
			TransactionType transactionType = (TransactionType) o;
			return Integer.toString(transactionType.getTransactionTypeCode());
		}
		return null;
	}

}
