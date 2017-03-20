package com.simplevat.dao.impl.invoice;

import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.entity.invoice.Invoice;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hiren
 */
@Repository
@Transactional
public class InvoiceDaoImpl implements InvoiceDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Invoice getInvoice(int invoiceId) {

        return entityManager.find(Invoice.class, invoiceId);

    }

    @Override
    public List<Invoice> getInvoices() {
        return entityManager.createNamedQuery("Invoice.searchInvoices", Invoice.class)
                .getResultList();
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        entityManager.persist(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        return entityManager.merge(invoice);
    }

}
