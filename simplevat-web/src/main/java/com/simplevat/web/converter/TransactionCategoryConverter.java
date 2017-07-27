package com.simplevat.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.bankaccount.TransactionCategory;

/**
 * @author bhavin.panchani
 *
 */
@FacesConverter("transactionCategoryConverter")
public class TransactionCategoryConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (string != null && !string.isEmpty()) {
			TransactionCategory transactionCategory = new TransactionCategory();
			transactionCategory.setTransactionCategoryCode(Integer.parseInt(string));
			return transactionCategory;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		
		if (o instanceof TransactionCategory) {
			TransactionCategory transactionCategory = (TransactionCategory) o;
			return Integer.toString(transactionCategory.getTransactionCategoryCode());
		}
		return null;
	}

}
