package com.simplevat.web.invoice.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.simplevat.dao.invoice.InvoiceFilter;
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.service.invoice.InvoiceService;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Hiren
 */
@Controller
@SessionScope
public class InvoiceListController implements Serializable {

    private static final long serialVersionUID = 9066359395680732884L;

    @Setter
    @Getter
    private List<Invoice> invoices;

    @Getter
    @Setter
    private Invoice selectedInvoice;

    @Autowired
    private InvoiceService invoiceService;

    @PostConstruct
    public void initInvoices() {
    	System.out.println("inside Post constructor");
        invoices = new ArrayList<>();
        final InvoiceFilter invoiceFilter = new InvoiceFilter();
        invoices = invoiceService.filter(invoiceFilter);
    }

//    @Nonnull
//    public List<Invoice> getInvoices() {
//        return invoices;
//    }

    public String redirectToCreateInvoice() {
        return "invoice.xhtml?faces-redirect=true";
    }

    public String redirectToEditInvoice(int invoiceId) {
        return "invoice.xhtml?faces-redirect=true&invoiceId=" + invoiceId;
    }

    public void deleteInvoice(final Invoice invoice) {
        invoice.setDeleteFlag(Boolean.TRUE);
        invoiceService.update(invoice, invoice.getInvoiceId());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Invoice Deleted SuccessFully"));
    }

    @Nonnull
    public BigDecimal totalAmount(@Nonnull final Invoice invoice) {

        final BigDecimal discount = invoice.getInvoiceDiscount();
        final DiscountType discountType = invoice.getDiscountType();

// remove this after new table
//        final DiscountType discountType = ABSOLUTE;
        BigDecimal finalTotal = BigDecimal.ZERO;

        for (InvoiceLineItem item : invoice.getInvoiceLineItems()) {
            BigDecimal itemAmount = item.getInvoiceLineItemUnitPrice()
                    .multiply(new BigDecimal(item.getInvoiceLineItemQuantity()));
            if (null != item.getInvoiceLineItemVat()) {
                itemAmount = itemAmount.add(itemAmount
                        .multiply(item.getInvoiceLineItemVat())
                        .multiply((new BigDecimal(0.01))));
            }
            finalTotal = finalTotal.add(itemAmount);
        }

        if (null != discountType && null != discount) {
            if (discountType.getDiscountTypeName().equals("Absolute Discount")) {
                finalTotal = finalTotal.subtract(discount);
            } else {
                finalTotal = finalTotal.subtract(finalTotal.multiply(discount)
                        .multiply(new BigDecimal(0.01)));
            }
        }
        return finalTotal;
    }
    

    public String redirectEditInvoice(int invoiceId){
        return "invoice.xhtml?faces-redirect=true&invoiceId"+invoiceId;
    }
}
