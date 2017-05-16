package com.simplevat.transactioncategory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.bankaccount.TransactionTypeService;

@Service
public class TransactionTypeConverter implements Converter {

	@Autowired
	TransactionTypeService transactionTypeService;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2 == null || arg2.length() == 0 || arg2.equals("")) {
			return null;
		}
		TransactionType type = (TransactionType)transactionTypeService.getTransactionType(Integer.parseInt(arg2));
		return type;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null && arg2 instanceof TransactionType) {
			TransactionType transactionType = (TransactionType)arg2;
			int transactionTypeCode = transactionType.getTransactionTypeCode();
			String strTransactionTypeCode = Integer.toString(transactionTypeCode);
			return strTransactionTypeCode;
		}else {
			return null;
		}
		
	}

}
