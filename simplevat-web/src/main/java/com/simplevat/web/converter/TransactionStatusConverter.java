package com.simplevat.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.bankaccount.TransactionStatus;

@FacesConverter("transactionStatusConverter")
public class TransactionStatusConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (string != null && !string.isEmpty()) {
			TransactionStatus transactionStatus = new TransactionStatus();
			transactionStatus.setExplainationStatusCode(Integer.parseInt(string));
			return transactionStatus;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		
		if (o instanceof TransactionStatus) {
			TransactionStatus transactionStatus = (TransactionStatus) o;
			return Integer.toString(transactionStatus.getExplainationStatusCode());
		}
		return null;
	}
}
