package com.simplevat.dao.invoice;

import com.simplevat.entity.invoice.Invoice;
import java.util.List;

/**
 *
 * @author Hiren
 */
public interface InvoiceDao {

    Invoice getInvoice(int invoiceId);

    List<Invoice> getInvoices();

    void saveInvoice(Invoice invoice);

    Invoice updateInvoice(Invoice invoice);

}
