package com.simplevat.controller.invoice;

import com.simplevat.entity.invoice.Invoice;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import com.simplevat.service.invoice.InvoiceService;
import java.util.ArrayList;
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
    private InvoiceService invoiceService;

    @PostConstruct
    public void initInvoices() {
        invoices = new ArrayList<>();
    }

    public List<Invoice> getInvoices() {
        return invoiceService.getInvoices();
    }

    public String redirectToCreateInvoice() {
        return "/pages/secure/user/invoice.xhtml";
    }

    public String redirectToEditInvoice(int invoiceId) {
        return "/pages/secure/user/invoice.xhtml?invoiceId=" + invoiceId;
    }

    public void deleteInvoice(final Invoice invoice) {
        invoice.setDeleteFlag('Y');
        invoiceService.updateInvoice(invoice);
        FacesContext.getCurrentInstance().addMessage("Invoice Deleted SuccessFully",
                new FacesMessage("Invoice Deleted SuccessFully"));
    }

}
