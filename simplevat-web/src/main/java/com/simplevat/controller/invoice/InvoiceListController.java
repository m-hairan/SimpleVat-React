package com.simplevat.controller.invoice;

import com.simplevat.entity.invoice.Invoice;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import com.simplevat.service.invoice.InvoiceService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Hiren
 */
@ViewScoped
@ManagedBean
@Controller
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

}
