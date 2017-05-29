package com.simplevat.service.impl.invoice;

import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hiren
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService<Integer, Invoice> {

    @Autowired
    public InvoiceDao dao;

    @Override
    public InvoiceDao getDao() {
        return dao;
    }

}
