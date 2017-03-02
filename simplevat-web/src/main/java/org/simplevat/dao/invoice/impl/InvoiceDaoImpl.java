package org.simplevat.dao.invoice.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simplevat.dao.invoice.InvoiceDao;
import org.simplevat.entity.invoice.Invoice;
import org.simplevat.entity.invoice.InvoiceItem;
import org.simplevat.entity.invoice.InvoiceStatus;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hiren
 */
@Repository
public class InvoiceDaoImpl implements InvoiceDao {

    @Override
    @Nullable
    public Invoice getInvoice(@Nonnull String invoiceId) {

        List<InvoiceItem> invoiceItems = new ArrayList();

        invoiceItems.add(new InvoiceItem(
                new BigDecimal(100),
                new BigDecimal(10)));

        return new Invoice("INV0001",
                Calendar.getInstance(),
                Calendar.getInstance(),
                "Company",
                "Customer",
                invoiceItems,
                InvoiceStatus.SAVED);
    }

    @Override
    @Nonnull
    public List<Invoice> getInvoices() {
        List<Invoice> invoices = new ArrayList();

        for (int i = 0; i < 8; i++) {

            List<InvoiceItem> invoiceItems = new ArrayList();
            for (int ii = 0; ii < 2; ii++) {
                invoiceItems.add(new InvoiceItem(
                        new BigDecimal(100).multiply(new BigDecimal(ii)),
                        new BigDecimal(10)));
            }
            final Calendar dueDate = Calendar.getInstance();
            dueDate.add(Calendar.DATE, i);
            invoices.add(new Invoice("INV000" + i,
                    Calendar.getInstance(),
                    dueDate,
                    "Company " + i,
                    "Customer " + i,
                    invoiceItems,
                    randomStatus()));
        }

        return invoices;

    }

    @Nonnull
    private InvoiceStatus randomStatus() {
        final List<InvoiceStatus> VALUES
                = Collections.unmodifiableList(Arrays
                        .asList(InvoiceStatus.values()));
        final int SIZE = VALUES.size();
        final Random RANDOM = new Random();
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
