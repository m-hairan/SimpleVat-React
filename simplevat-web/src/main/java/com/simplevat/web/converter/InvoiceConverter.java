package com.simplevat.web.converter;

import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.invoice.InvoiceService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hiren
 */
@Service
public class InvoiceConverter implements Converter {

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            Invoice invoice = invoiceService.findByPK(Integer.parseInt(string));
            return invoice;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof Invoice) {
            Invoice invoice = (Invoice) o;
            return invoice.getInvoiceId().toString();
        }
        return null;
    }

}
