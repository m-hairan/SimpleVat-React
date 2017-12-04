package com.simplevat.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bhavin.panchani
 *
 */
@Service
public class TransactionCategoryConverter implements Converter {

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                TransactionCategory transactionCategory = transactionCategoryService.findByPK(Integer.parseInt(string));
                return transactionCategory;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o instanceof TransactionCategory) {
            TransactionCategory transactionCategory = (TransactionCategory) o;
            return Integer.toString(transactionCategory.getTransactionCategoryId());
        }
        return null;
    }

}
