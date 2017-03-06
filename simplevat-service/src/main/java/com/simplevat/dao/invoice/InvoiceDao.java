package com.simplevat.dao.invoice;

import com.simplevat.entity.invoice.Invoice;
import java.util.List;

/**
 *
 * @author Hiren
 */
public interface InvoiceDao {

    Invoice getInvoice(String invoice);

    List<Invoice> getInvoices();
    
    void saveInvoice(Invoice invoice);

}
