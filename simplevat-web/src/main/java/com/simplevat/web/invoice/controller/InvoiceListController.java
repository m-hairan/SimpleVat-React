package com.simplevat.web.invoice.controller;

import com.github.javaplugs.jsf.SpringScopeView;
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
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.InvoicePurchaseStatusConstant;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.invoice.model.InvoiceModel;

/**
 *
 * @author Hiren
 */
@Controller
@SpringScopeView
public class InvoiceListController extends BaseController implements Serializable {

    private static final long serialVersionUID = 9066359395680732884L;

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceModelHelper invoiceModelHelper;

    @Setter
    @Getter
    private List<Invoice> invoices;

    @Setter
    @Getter
    private List<InvoiceModel> invoiceList;

    @Getter
    @Setter
    private InvoiceModel selectedInvoiceModel;

    @Getter
    @Setter
    private int totalInvoices;

    @Getter
    @Setter
    private int totalPaid;

    @Getter
    @Setter
    private int totalUnPaid;

    @Getter
    @Setter
    private int totalPartiallyPaid;

    @Getter
    @Setter
    private Invoice selectedInvoice;

    public InvoiceListController() {
        super(ModuleName.INVOICE_MODULE);
    }

    @PostConstruct
    public void initInvoices() {
        System.out.println("inside Post constructor");
        final InvoiceFilter invoiceFilter = new InvoiceFilter();
        invoices = invoiceService.filter(invoiceFilter);
        populateList();
    }

    public void populateList() {
        invoiceList = new ArrayList<>();
        if (invoices != null) {
            for (Invoice invoice : invoices) {
                if (invoice.getStatus() == InvoicePurchaseStatusConstant.PAID) {
                    totalPaid++;
                } else if (invoice.getStatus() == InvoicePurchaseStatusConstant.PARTIALPAID) {
                    totalPartiallyPaid++;
                } else if (invoice.getStatus() == InvoicePurchaseStatusConstant.UNPAID) {
                    totalUnPaid++;
                }
                totalInvoices++;
                invoiceList.add(invoiceModelHelper.getInvoiceModel(invoice));
            }
        }
    }

    public void allPaidInvoice() {
        invoiceList.clear();
        for (Invoice invoice : invoices) {
            if (invoice.getStatus() == InvoicePurchaseStatusConstant.PAID) {
                invoiceList.add(invoiceModelHelper.getInvoiceModel(invoice));
            }
        }
    }

    public void allUnPaidInvoice() {
        invoiceList.clear();
        for (Invoice invoice : invoices) {
            if (invoice.getStatus() == InvoicePurchaseStatusConstant.UNPAID) {
                invoiceList.add(invoiceModelHelper.getInvoiceModel(invoice));
            }
        }
    }

    public void allPartialPaidInvoice() {
        invoiceList.clear();
        for (Invoice invoice : invoices) {
            if (invoice.getStatus() == InvoicePurchaseStatusConstant.PARTIALPAID) {
                invoiceList.add(invoiceModelHelper.getInvoiceModel(invoice));
            }
        }
    }

    public String redirectToCreateInvoice() {
        String selectedInvoiceId = "";
        if (selectedInvoiceModel != null) {
            selectedInvoiceId = "&selectedInvoiceModelId=" + selectedInvoiceModel.getInvoiceId();
        }
        return "invoice.xhtml?faces-redirect=true" + selectedInvoiceId;
    }

    public void deleteInvoice(final Invoice invoice) {
        invoice.setDeleteFlag(Boolean.TRUE);
        invoiceService.update(invoice, invoice.getInvoiceId());
        initInvoices();
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
            if (item.getInvoiceLineItemVat() != null) {
                itemAmount = itemAmount.add(itemAmount
                        .multiply(item.getInvoiceLineItemVat().getVat())
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
