/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simplevat.dao.invoice;

import java.util.List;
import javax.annotation.Nonnull;
import org.simplevat.entity.invoice.Invoice;

/**
 *
 * @author Hiren
 */
public interface InvoiceDao {

    Invoice getInvoice(@Nonnull String invoice);

    List<Invoice> getInvoices();

}
