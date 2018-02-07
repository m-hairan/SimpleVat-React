/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.setting.controller;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Product;
import com.simplevat.entity.Project;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.web.constant.DiscountTypeConstant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author uday
 */
public class InvoicePreviewTemplate implements Serializable {

    public static Invoice getInvoiceObject() {
        Invoice invoice = new Invoice();
        LocalDateTime dateTime = LocalDateTime.now();
        invoice.setInvoiceDate(dateTime);
        Contact contact = new Contact();
        contact.setContactId(1200);
        contact.setFirstName("Mr Mark");
        contact.setLastName("Johnson");
        invoice.setInvoiceContact(contact);
        Currency currency = new Currency();
        currency.setCurrencySymbol("$");
        invoice.setCurrency(currency);
        invoice.setDueAmount(new BigDecimal(2500));
        LocalDateTime dateTimeDueBy = LocalDateTime.now();
        invoice.setInvoiceDueDate(dateTimeDueBy);
        Contact contactShipping = new Contact();
        contactShipping.setContactId(1201);
        contactShipping.setFirstName("MR DAVID");
        contactShipping.setLastName("WEBSTER");
        invoice.setShippingContact(contactShipping);
        InvoiceLineItem invoiceLineItem = new InvoiceLineItem();
        Product product = new Product();
        product.setProductName("Product 1");
        product.setProductDescription("Product 1 Description");
        invoiceLineItem.setInvoiceLineItemProductService(product);
        invoiceLineItem.setInvoiceLineItemQuantity(30);
        invoiceLineItem.setInvoiceLineItemUnitPrice(new BigDecimal(2500));
        VatCategory vatCategory = new VatCategory();
        vatCategory.setVat(new BigDecimal(18));
        invoiceLineItem.setInvoiceLineItemVat(vatCategory);
        invoice.setInvoiceLineItems(new ArrayList<>());
        invoice.getInvoiceLineItems().add(invoiceLineItem);
        invoice.setContractPoNumber("1234");
        invoice.setInvoiceProject(new Project());
        DiscountType discountType = new DiscountType();
        discountType.setDiscountTypeCode(DiscountTypeConstant.ABSOLUTEDISCOUNT);
        invoice.setDiscountType(discountType);
        invoice.setInvoiceDueOn(1);
        invoice.setInvoiceReferenceNumber("1234-1234-1234");
        invoice.setInvoiceNotes("abcd");
        invoice.setFreeze(Boolean.FALSE);
        invoice.setStatus(0);

        return invoice;
    }
}
