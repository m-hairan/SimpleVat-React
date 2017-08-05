package com.simplevat.web.invoice.controller;

import com.simplevat.criteria.InvoiceCriteria;
import com.simplevat.criteria.ProjectCriteria;
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
import org.springframework.stereotype.Controller;

import com.simplevat.dao.invoice.InvoiceFilter;
import com.simplevat.entity.Project;
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.invoice.model.InvoiceModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Hiren
 */
@Controller
@Scope("view")
public class InvoiceListController implements Serializable {

    private static final long serialVersionUID = 9066359395680732884L;

    @Setter
    @Getter
    private List<Invoice> invoices;

    @Getter
    @Setter
    private InvoiceModel selectedInvoice;

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
        if(selectedInvoice != null){
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("invoiceId", selectedInvoice.getInvoiceId());
        }
        return "invoice.xhtml?faces-redirect=true?faces-redirect=true";
    }

//    public String redirectToEditInvoice(Invoice invoice) throws Exception {
////  
//        InvoiceCriteria invoiceCriteria = new InvoiceCriteria();
//        invoiceCriteria.setInvoiceId(invoice.getInvoiceId());
//        List<Invoice> invoices = invoiceService.getInvoiceByCriteria(invoiceCriteria);
//        if(CollectionUtils.isEmpty(invoices)){
//            throw new Exception("Invalid Invoice Id");
//        }
//        this.selectedInvoice = invoices.get(0);
//        return "/pages/secure/invoice/invoice.xhtml?faces-redirect=true";
////        return "invoice.xhtml?faces-redirect=true&invoiceId=" + invoiceId;
//    }

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
    

    public String redirectEditInvoice(){
        return "invoice.xhtml?faces-redirect=true&invoiceId"+selectedInvoice.getInvoiceId();
    }
}
