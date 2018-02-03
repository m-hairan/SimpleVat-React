package com.simplevat.web.converter;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.financialreport.FinancialPeriod;
import com.simplevat.web.financialreport.FinancialPeriodHolder;
import com.simplevat.web.setting.controller.InvoiceTemplateModel;
import com.simplevat.web.utils.GeneralSettingUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bhavin.panchani
 *
 */
@Service
public class InvoiceTemplateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            InvoiceTemplateModel invoiceTemplateModel = getInvoiceTemplateByValue(string);
            return invoiceTemplateModel;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof InvoiceTemplateModel) {
            InvoiceTemplateModel invoiceTemplateModel = (InvoiceTemplateModel) o;
            return invoiceTemplateModel.getValue();
        }
        return null;
    }

    private InvoiceTemplateModel getInvoiceTemplateByValue(String value) {
        for (InvoiceTemplateModel invoiceTemplateModel : GeneralSettingUtil.invoiceTemplateList()) {
            if (invoiceTemplateModel.getValue().equals(value)) {
                return invoiceTemplateModel;
            }
        }
        return new InvoiceTemplateModel();
    }

}
