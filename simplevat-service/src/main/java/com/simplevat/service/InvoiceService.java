package com.simplevat.service;

import com.simplevat.entity.Invoice;
import java.util.List;

/**
 *
 * @author Hiren
 */
public interface InvoiceService {

    List<Invoice> getInvoices();

    Invoice getInvoice(String invoiceUUID);

}
