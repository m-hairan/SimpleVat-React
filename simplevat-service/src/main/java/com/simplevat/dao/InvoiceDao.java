package com.simplevat.dao;

import com.simplevat.entity.Invoice;
import java.util.List;

/**
 *
 * @author Hiren
 */
public interface InvoiceDao {

    Invoice getInvoice(String invoice);

    List<Invoice> getInvoices();

}
