package com.simplevat.controller.invoice;

import com.simplevat.dao.invoice.InvoiceFilter;
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.service.impl.invoice.InvoiceServiceImpl;
import com.simplevat.service.invoice.InvoiceService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Hiren
 */
@Controller
@RequestScoped
@ManagedBean
public class InvoiceListController implements Serializable {

    private static final long serialVersionUID = 9066359395680732884L;

    @Setter
    private List<Invoice> invoices;

    @Getter
    @Setter
    private Invoice selectedInvoice;

    @Autowired
    private InvoiceService<Integer, Invoice> invoiceService;

    @PostConstruct
    public void initInvoices() {
        invoices = new ArrayList<>();
    }

    @Nonnull
    public List<Invoice> getInvoices() {
        final InvoiceFilter invoiceFilter = new InvoiceFilter();
        return invoiceService.filter(invoiceFilter);
    }

    public String redirectToCreateInvoice() {
        return "/pages/secure/user/invoice.xhtml";
    }

    public String redirectToEditInvoice(int invoiceId) {
        return "/pages/secure/user/invoice.xhtml?invoiceId=" + invoiceId;
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
}
