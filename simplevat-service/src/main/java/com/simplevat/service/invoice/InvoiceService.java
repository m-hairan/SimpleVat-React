package com.simplevat.service.invoice;

import com.simplevat.entity.invoice.Invoice;
import java.util.List;

/**
 *
 * @author Hiren
 */
public interface InvoiceService {
    
    void saveInvoice(Invoice invoice);

    List<Invoice> getInvoices();

    Invoice getInvoice(String invoiceUUID);

}
