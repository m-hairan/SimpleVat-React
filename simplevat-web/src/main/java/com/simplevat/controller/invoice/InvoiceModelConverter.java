package com.simplevat.controller.invoice;

import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.invoice.model.InvoiceItemModel;
import com.simplevat.invoice.model.InvoiceModel;
import java.util.Calendar;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author hiren
 *
 */
@Component
public class InvoiceModelConverter {

    public Invoice convertModelToEntity(InvoiceModel invoiceModel) {
        final Calendar invoiceDate = Calendar.getInstance();
        invoiceDate.setTime(invoiceModel.getInvoiceDate());

        Invoice invoice = new Invoice();

        invoice.setContractPoNumber(invoiceModel.getContractPoNumber());
        invoice.setCreatedDate(Calendar.getInstance());
        invoice.setCurrency(invoiceModel.getCurrencyCode());

        invoice.setInvoiceContact(invoiceModel.getContact());
        invoice.setInvoiceDate(invoiceDate);
        invoice.setInvoiceDiscount(invoiceModel.getDiscount());
        invoice.setInvoiceDiscountType(invoiceModel.getDiscountType());
        invoice.setInvoiceDueOn(invoiceModel.getInvoiceDueOn());
        invoice.setInvoiceReferenceNumber(invoiceModel.getInvoiceRefNo());
        invoice.setInvoiceText(invoiceModel.getInvoiceText());

        invoice.setLastUpdateDate(Calendar.getInstance());

        final Collection<InvoiceLineItem> items = invoiceModel
                .getInvoiceItems()
                .stream()
                .map((lineModel) -> convertToLineItem(lineModel))
                .collect(Collectors.toList());
        
        invoice.setInvoiceLineItems(items);

        return invoice;
    }

    private InvoiceLineItem convertToLineItem(final InvoiceItemModel model) {
        final InvoiceLineItem item = new InvoiceLineItem();

        item.setCreatedDate(Calendar.getInstance());

        item.setInvoiceLineItemDescription(model.getDescription());
        item.setInvoiceLineItemQuantity(model.getQuatity());
        item.setInvoiceLineItemUnitPrice(model.getUnitPrice());
        item.setInvoiceLineItemVat(model.getVatId());

        item.setLastUpdateDate(Calendar.getInstance());

        return item;
    }
}
