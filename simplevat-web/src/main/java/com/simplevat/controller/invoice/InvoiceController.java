package com.simplevat.controller.invoice;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.invoice.model.InvoiceItemModel;
import com.simplevat.invoice.model.InvoiceModel;
import com.simplevat.service.ContactService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.invoice.InvoiceService;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Hiren
 */
@ViewScoped
@ManagedBean
public class InvoiceController {

    @Getter
    private InvoiceModel invoiceModel;

    @Setter
    @ManagedProperty("#{contactService}")
    private ContactService contactService;

    @Setter
    @ManagedProperty("#{currencyService}")
    private CurrencyService currencyService;

    @Setter
    @ManagedProperty("#{invoiceConverter}")
    private InvoiceConverter invoiceConverter;

    @Setter
    @ManagedProperty("#{invoiceService}")
    private InvoiceService invoiceService;

    @PostConstruct
    public void initInvoice() {
        invoiceModel = new InvoiceModel();
        addInvoiceItem(new InvoiceItemModel());
    }

    public void addInvoiceItem(@Nonnull final InvoiceItemModel invoiceItemModel) {
        invoiceModel.addInvoiceItem(invoiceItemModel);
    }

    public List<Contact> getContacts() {
        return contactService.getContacts();
    }

    public List<Currency> getCurrencies() {
        return currencyService.getCurrencies();
    }

    public DiscountType[] getDiscountTypes() {
        return DiscountType.values();
    }

    public void saveInvoice() {
        Invoice invoice = invoiceConverter.convertModelToEntity(invoiceModel);
        invoiceService.saveInvoice(invoice);
    }

}
