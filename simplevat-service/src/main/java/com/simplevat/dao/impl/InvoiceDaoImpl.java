package com.simplevat.dao.impl;

import com.simplevat.dao.InvoiceDao;
import com.simplevat.entity.Invoice;
import com.simplevat.entity.InvoiceLineItem;
import com.simplevat.util.InvoiceStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hiren
 */
@Repository
public class InvoiceDaoImpl implements InvoiceDao {

    @Override
    public Invoice getInvoice(String invoiceId) {

        List<InvoiceLineItem> invoiceItems = new ArrayList();

        invoiceItems.add(new InvoiceLineItem());

        return new Invoice();
    }

    @Override
    public List<Invoice> getInvoices() {
        List<Invoice> invoices = new ArrayList();

        for (int i = 0; i < 8; i++) {

            List<InvoiceLineItem> invoiceItems = new ArrayList();
            for (int ii = 0; ii < 2; ii++) {
                invoiceItems.add(new InvoiceLineItem());
            }
            final Calendar dueDate = Calendar.getInstance();
            dueDate.add(Calendar.DATE, i);
            invoices.add(new Invoice());
        }

        return invoices;

    }

    private InvoiceStatus randomStatus() {
        final List<InvoiceStatus> VALUES
                = Collections.unmodifiableList(Arrays
                        .asList(InvoiceStatus.values()));
        final int SIZE = VALUES.size();
        final Random RANDOM = new Random();
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
