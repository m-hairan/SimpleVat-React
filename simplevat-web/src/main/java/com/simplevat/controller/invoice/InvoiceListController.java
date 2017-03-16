package com.simplevat.controller.invoice;

import com.simplevat.entity.invoice.Invoice;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import com.simplevat.service.invoice.InvoiceService;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Hiren
 */
@Controller
@ViewScoped
@ManagedBean
public class InvoiceListController implements Serializable {

    private static final long serialVersionUID = 9066359395680732884L;

    @Getter
    @Setter
    private List<Invoice> invoices;

    @Getter
    @Setter
    private Invoice selectedInvoice;

    @Autowired
    private InvoiceService invoiceService;

    @PostConstruct
    public void listAllInvoices() {
        this.setInvoices(invoiceService.getInvoices());
    }

    public String redirectToCreateInvoice() {
        return "/pages/secure/user/invoice.xhtml";
    }

}
