package com.simplevat.controller.invoice;

import com.simplevat.entity.invoice.Invoice;
import com.simplevat.invoice.model.InvoiceModel;
import java.util.Calendar;
import org.springframework.stereotype.Service;

/**
 *
 * @author hiren
 *
 */
@Service("invoiceConverter")
public class InvoiceConverter {

    public Invoice convertModelToEntity(InvoiceModel invoiceModel) {
        Invoice invoice = new Invoice();
        invoice.setContractPoNumber(invoiceModel.getContractPoNumber());
        invoice.setCreatedDate(Calendar.getInstance());
//        invoice.set
        return invoice;
    }
}
