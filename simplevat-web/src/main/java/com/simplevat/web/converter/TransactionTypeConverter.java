package com.simplevat.web.converter;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.bankaccount.TransactionTypeService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bhavin.panchani
 *
 */
@Service
public class TransactionTypeConverter implements Converter {

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                TransactionType transactionType = transactionTypeService.findByPK(Integer.parseInt(string));
                return transactionType;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o instanceof TransactionType) {
            TransactionType transactionType = (TransactionType) o;
            return transactionType.getTransactionTypeCode().toString();
        }
        return null;
    }

}
