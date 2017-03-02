package org.simplevat.service.invoice;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simplevat.entity.invoice.Invoice;

/**
 *
 * @author Hiren
 */
public interface InvoiceService {

    @Nonnull
    List<Invoice> getInvoices();

    @Nullable
    Invoice getInvoice(@Nonnull String invoiceUUID);

}
