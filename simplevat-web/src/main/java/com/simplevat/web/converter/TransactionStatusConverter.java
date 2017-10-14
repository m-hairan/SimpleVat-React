package com.simplevat.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.service.bankaccount.TransactionStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatusConverter implements Converter {

    @Autowired
    private TransactionStatusService transactionStatusService;
    
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (string != null && !string.isEmpty()) {
			TransactionStatus transactionStatus = transactionStatusService.findByPK(Integer.parseInt(string));
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
