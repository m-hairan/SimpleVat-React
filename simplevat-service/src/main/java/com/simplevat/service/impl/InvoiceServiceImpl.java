package com.simplevat.service.impl;

import java.util.List;
import com.simplevat.dao.InvoiceDao;
import com.simplevat.entity.Invoice;
import com.simplevat.service.InvoiceService;
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
    public Invoice getInvoice(String invoiceUUID) {
        return invoiceDao.getInvoice(invoiceUUID);
    }

}
