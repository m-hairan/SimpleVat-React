package com.simplevat.dao.impl.invoice;

import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.entity.invoice.InvoiceStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
    public Invoice getInvoice(String invoiceId) {

        return entityManager.find(Invoice.class, invoiceId);

    }

    @Override
    public List<Invoice> getInvoices() {
        return entityManager.createQuery("from Invoice i", Invoice.class)
                .getResultList();
    }

    private InvoiceStatus randomStatus() {
        final List<InvoiceStatus> VALUES
                = Collections.unmodifiableList(Arrays
                        .asList(InvoiceStatus.values()));
        final int SIZE = VALUES.size();
        final Random RANDOM = new Random();
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        entityManager.persist(invoice);
    }

}
