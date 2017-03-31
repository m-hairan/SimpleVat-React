package com.simplevat.controller.invoice;

import com.simplevat.contact.model.ContactModel;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.invoice.model.InvoiceItemModel;
import com.simplevat.invoice.model.InvoiceModel;
import com.simplevat.service.ContactService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.invoice.InvoiceService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Hiren
 */
@Controller
@ManagedBean
@ViewScoped
public class InvoiceController {

    @Getter
    private InvoiceModel invoiceModel;

    @Getter
    private ContactModel contactModel;

    @Getter
    @Setter
    private List<Currency> currencies;

    @Autowired
    private ContactService contactService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private InvoiceModelConverter invoiceConverter;

    @Autowired
    private InvoiceService invoiceService;

    @PostConstruct
    public void initInvoice() {

        invoiceModel = new InvoiceModel();

        currencies = currencyService.getCurrencies();
        addInvoiceItem();
    }

    public void addInvoiceItem() {
        updateCurrencyLabel();
        invoiceModel.addInvoiceItem(new InvoiceItemModel());
    }

    public List<Contact> contacts(final String searchQuery) {
        return contactService.getContacts(searchQuery.trim());
    }

    public List<Currency> completeCurrency(String currencyStr) {
        List<Currency> currencySuggestion = new ArrayList<>();
        Iterator<Currency> currencyIterator = this.currencies.iterator();

        while (currencyIterator.hasNext()) {
            Currency currency = currencyIterator.next();
            if (currency.getCurrencyName() != null
                    && !currency.getCurrencyName().isEmpty()
                    && currency.getCurrencyName().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);
            } else if (currency.getCurrencyDescription() != null
                    && !currency.getCurrencyDescription().isEmpty()
                    && currency.getCurrencyDescription().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);
            } else if (currency.getCurrencyIsoCode() != null
                    && !currency.getCurrencyIsoCode().isEmpty()
                    && currency.getCurrencyIsoCode().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);
            }
        }
        return currencySuggestion;
    }

    public List<DiscountType> discountTypes(final String searchString) {
        final List<DiscountType> types = new ArrayList<>();
        for (DiscountType type : DiscountType.values()) {
            types.add(type);
            if (null != searchString
                    && !type.toString().toLowerCase().contains(searchString.toLowerCase())) {
                types.remove(type);
            }
        }
        return types;
    }

    public void saveInvoice() throws IOException {
        final Invoice invoice = invoiceConverter
                .convertModelToEntity(invoiceModel);
        invoiceService.updateInvoice(invoice);
        initInvoice();
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect("invoices.xhtml?faces-redirect=true");
    }

    public void saveAndAddMoreInvoice() {
        final Invoice invoice = invoiceConverter
                .convertModelToEntity(invoiceModel);
        invoiceService.updateInvoice(invoice);
        initInvoice();
        FacesContext.getCurrentInstance().addMessage("Invoice Added SuccessFully", new FacesMessage("Invoice Added SuccessFully"));
    }

    public void updateCurrency() {
        invoiceModel.setCurrencyCode(null);
        if (null != invoiceModel.getContact()) {
            final Contact contact = contactService
                    .getContact(invoiceModel.getContact().getContactId());
            invoiceModel.setCurrencyCode(contact.getCurrency());
        }
    }

    public void updateCurrencyLabel() {
        if (null != invoiceModel.getCurrencyCode()) {
            invoiceModel.setCurrencyCode(currencyService.getCurrency(invoiceModel.getCurrencyCode().getCurrencyCode()));
        }
    }

    public void updateSubTotal(final InvoiceItemModel invoiceItemModel) {
        final int quantity = invoiceItemModel.getQuatity();
        final BigDecimal unitPrice = invoiceItemModel.getUnitPrice();
        final BigDecimal vatPer = invoiceItemModel.getVatId();
        if (null != unitPrice) {
            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
            invoiceItemModel.setSubTotal(amountWithoutTax);

            if (vatPer != null && vatPer.compareTo(BigDecimal.ZERO) >= 1) {
                final BigDecimal amountWithTax = amountWithoutTax
                        .add(amountWithoutTax.multiply(vatPer).multiply(new BigDecimal(0.01)));
                invoiceItemModel.setSubTotal(amountWithTax);
            }
        }

    }

    public void redirectEditInvoice(int invoiceId) throws IOException {

        final Invoice invoice = invoiceService.getInvoice(invoiceId);
        invoiceModel = invoiceConverter.convertEntityToModel(invoice);
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect("invoice.xhtml?faces-redirect=true");
    }

    public void initCreateContact() {
        contactModel = new ContactModel();
    }

    public void createContact() {
        
        final Contact contact = new Contact();

        contact.setBillingEmail(contactModel.getEmailAddress());
        contact.setDeleteFlag('N');
        contact.setEmail(contactModel.getEmailAddress());
        contact.setFirstName(contactModel.getFirstName());
        contact.setLastName(contactModel.getLastName());
        contact.setOrganization(contactModel.getOrganizationName());
        
        contactModel=new ContactModel();
        
        contactService.createContact(contact);
        
        invoiceModel.setContact(contact);

    }

}