package com.simplevat.controller.invoice;

import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.invoice.model.InvoiceItemModel;
import com.simplevat.invoice.model.InvoiceModel;
import com.simplevat.service.invoice.InvoiceService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Hiren
 *
 */
@Component
public class InvoiceModelConverter {

    @Autowired
    private InvoiceService invoiceService;

    @Nonnull
    public Invoice convertModelToEntity(@Nonnull final InvoiceModel invoiceModel) {
        final LocalDateTime invoiceDate = LocalDateTime.ofInstant(invoiceModel.getInvoiceDate().toInstant(), ZoneId.systemDefault());

        Invoice invoice = null;

        if (invoiceModel.getInvoiceId() > 0) {
            invoice = invoiceService.getInvoice(invoiceModel.getInvoiceId());
        } else {
            invoice = new Invoice();
        }

        invoice.setContractPoNumber(invoiceModel.getContractPoNumber());
        invoice.setCurrency(invoiceModel.getCurrencyCode());

        invoice.setInvoiceContact(invoiceModel.getContact());
        invoice.setInvoiceDate(invoiceDate);
        invoice.setInvoiceDiscount(invoiceModel.getDiscount());
        invoice.setInvoiceDiscountType(invoiceModel.getDiscountType());
        invoice.setInvoiceDueOn(invoiceModel.getInvoiceDueOn());
        invoice.setInvoiceReferenceNumber(invoiceModel.getInvoiceRefNo());
        invoice.setInvoiceText(invoiceModel.getInvoiceText());

        final Collection<InvoiceLineItem> items = invoiceModel
                .getInvoiceItems()
                .stream()
                .map(this::convertToLineItem)
                .collect(Collectors.toList());

        invoice.setInvoiceLineItems(items);

        return invoice;
    }

    @Nonnull
    private InvoiceLineItem convertToLineItem(@Nonnull final InvoiceItemModel model) {
        final InvoiceLineItem item = new InvoiceLineItem();

        item.setCreatedDate(Calendar.getInstance());

        item.setInvoiceLineItemDescription(model.getDescription());
        item.setInvoiceLineItemQuantity(model.getQuatity());
        item.setInvoiceLineItemUnitPrice(model.getUnitPrice());
        item.setInvoiceLineItemVat(model.getVatId());

        item.setLastUpdateDate(Calendar.getInstance());

        return item;
    }

    @Nonnull
    public InvoiceModel convertEntityToModel(@Nonnull final Invoice invoice) {

        InvoiceModel invoiceModel = new InvoiceModel();

        invoiceModel.setContractPoNumber(invoice.getContractPoNumber());
        invoiceModel.setCurrencyCode(invoice.getCurrency());
        invoiceModel.setInvoiceId(invoice.getInvoiceId());
        invoiceModel.setContact(invoice.getInvoiceContact());

        invoiceModel.setInvoiceDate(null != invoice.getInvoiceDate() ? Date.from(invoice.getInvoiceDate().atZone(ZoneId.systemDefault()).toInstant()) : null);
        invoiceModel.setDiscount(invoice.getInvoiceDiscount());
        invoiceModel.setDiscountType(invoice.getInvoiceDiscountType());
        invoiceModel.setInvoiceDueOn(invoice.getInvoiceDueOn());
        invoiceModel.setInvoiceRefNo(invoice.getInvoiceReferenceNumber());
        invoiceModel.setInvoiceText(invoice.getInvoiceText());

        final List<InvoiceItemModel> items = invoice
                .getInvoiceLineItems()
                .stream()
                .map((lineItem) -> convertToItemModel(lineItem))
                .collect(Collectors.toList());

        invoiceModel.setInvoiceItems(items);

        return invoiceModel;
    }

    @Nonnull
    public InvoiceItemModel convertToItemModel(@Nonnull final InvoiceLineItem invoiceLineItem) {

        final InvoiceItemModel model = new InvoiceItemModel();

        model.setDescription(invoiceLineItem.getInvoiceLineItemDescription());
        model.setQuatity(invoiceLineItem.getInvoiceLineItemQuantity());
        model.setUnitPrice(invoiceLineItem.getInvoiceLineItemUnitPrice());
        model.setVatId(invoiceLineItem.getInvoiceLineItemVat());

        this.updateSubTotal(model);

        return model;

    }

    private void updateSubTotal(@Nonnull final InvoiceItemModel invoiceItemModel) {
        final int quantity = invoiceItemModel.getQuatity();
        final BigDecimal unitPrice = invoiceItemModel.getUnitPrice();
        final BigDecimal vatPer = invoiceItemModel.getVatId();
        if (null != unitPrice) {
            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
            invoiceItemModel.setSubTotal(amountWithoutTax);

            if (vatPer != null && vatPer.compareTo(BigDecimal.ZERO) >= 1) {
                final BigDecimal amountWithTax = amountWithoutTax
                        .add(amountWithoutTax.multiply(vatPer).multiply(new BigDecimal(0.01)));
                invoiceItemModel.setSubTotal(amountWithTax);
            }
        }

    }
}
