package com.simplevat.test.service.invoice;

import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hiren
 */
public class TestInvoiceService extends BaseManagerTest {
    
    @Test
    public void testSaveInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCreatedDate(LocalDateTime.now());
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceDueOn(7);
//        invoice.setContactFullName("Hiren");
        invoice.setContractPoNumber("PO-2342");
        invoice.setInvoiceDiscount(new BigDecimal(50));
//        invoice.setInvoiceDiscountType(DiscountType.ABSOLUTE);
        invoice.setInvoiceReferenceNumber("INV2323");
        invoice.setInvoiceLineItems(getLineItems(invoice.getInvoiceId()));
        invoice.setDeleteFlag(Boolean.FALSE);
        invoiceService.saveInvoice(invoice);
    }
    
    private List<InvoiceLineItem> getLineItems(int invoiceId) {
        List<InvoiceLineItem> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            InvoiceLineItem item = new InvoiceLineItem();
            item.setInvoiceLineItemDescription("desc" + i);
            item.setInvoiceLineItemQuantity(5 + i);
            item.setInvoiceLineItemUnitPrice(new BigDecimal(50 * i));
            item.setInvoiceLineItemVat(new BigDecimal(10));
            items.add(item);
        }
        return items;
    }
    
}
