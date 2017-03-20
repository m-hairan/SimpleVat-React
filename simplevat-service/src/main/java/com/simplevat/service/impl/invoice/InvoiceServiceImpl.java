package com.simplevat.service.impl.invoice;

import java.util.List;
import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.invoice.InvoiceService;
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
    public List<Invoice> getInvoices() {
        return invoiceDao.getInvoices();
    }

    @Override
    public Invoice getInvoice(int invoiceId) {
        return invoiceDao.getInvoice(invoiceId);
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceDao.saveInvoice(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        return invoiceDao.updateInvoice(invoice);
    }

}
