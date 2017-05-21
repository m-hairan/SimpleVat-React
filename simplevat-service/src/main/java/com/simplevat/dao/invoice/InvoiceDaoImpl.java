package com.simplevat.dao.invoice;

import com.simplevat.dao.AbstractDao;
import com.simplevat.entity.invoice.Invoice;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hiren
 */
@Repository
public class InvoiceDaoImpl extends AbstractDao<Integer, Invoice> implements InvoiceDao<Integer, Invoice>{

    
}
