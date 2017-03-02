package org.simplevat.service.invoice.impl;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simplevat.dao.invoice.InvoiceDao;
import org.simplevat.entity.invoice.Invoice;
import org.simplevat.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hiren
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    public InvoiceDao invoiceDao;

    @Override
    @Nonnull
    public List<Invoice> getInvoices() {
        return invoiceDao.getInvoices();
    }

    @Override
    @Nullable
    public Invoice getInvoice(@Nonnull String invoiceUUID) {
        return invoiceDao.getInvoice(invoiceUUID);
    }

}
