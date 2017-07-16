package com.simplevat.transactioncategory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.service.TransactionCategoryServiceNew;

@Service
public class TransactionCategoryConverter implements Converter {

	@Autowired
	private TransactionCategoryServiceNew transactionCategoryService;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2 == null || arg2.length() == 0 || arg2.equals("")) {
			return null;
		}
		TransactionCategory category = transactionCategoryService.findByPK(Integer.parseInt(arg2));
		return category;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null && arg2 instanceof TransactionCategory) {
			TransactionCategory transactionCategory = (TransactionCategory)arg2;
			int transactionTypeCode = transactionCategory.getTransactionCategoryCode();
			String strTransactionTypeCode = Integer.toString(transactionTypeCode);
			return strTransactionTypeCode;
		}else {
			return null;
		}
	}

}
