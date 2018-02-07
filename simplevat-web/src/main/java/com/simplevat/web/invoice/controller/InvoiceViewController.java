/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.invoice.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.invoice.InvoiceService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author h
 */
@Controller
@SpringScopeView
public class InvoiceViewController implements Serializable {

    private static final long serialVersionUID = 6299117288316809011L;
    @Autowired
    private InvoiceService invoiceService;

    @Getter
    @Setter
    private Invoice invoice;

    @Getter
    private boolean renderPDF = false;

    @PostConstruct
    public void init() {
        Integer invoiceId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("invoiceId").toString());
        invoice = invoiceService.findByPK(invoiceId);
    }

    public void printInvoice() {
        renderPDF = true;
        RequestContext.getCurrentInstance().execute("PF('invoicePDFWidget').show(); PF('invoicePDFWidget').content.scrollTop('0')");
    }

    public String redirectToEditInvoice() {
        return "invoice.xhtml?faces-redirect=true&selectedInvoiceModelId=" + invoice.getInvoiceId();
    }

    public String deFreeze() {
        invoice.setFreeze(Boolean.FALSE);
        invoiceService.update(invoice);
        return "list.xhtml?faces-redirect=true";
    }

}
