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
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Hiren
 */
@ViewScoped
@ManagedBean
@Component
public class InvoiceController {

    @Getter
    private InvoiceModel invoiceModel;

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
        invoiceModel.addInvoiceItem(new InvoiceItemModel());
    }

    public List<Contact> contacts(final String searchQuery) {
        return contactService.getContacts(searchQuery);
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
            if (null == searchString || searchString.isEmpty()) {
                return types;
            }
            if (type.toString().toLowerCase().contains(searchString.toLowerCase())) {
                types.add(type);
            }
        }
        return types;
    }

    public void saveInvoice() {
        final Invoice invoice = invoiceConverter
                .convertModelToEntity(invoiceModel);
        invoiceService.saveInvoice(invoice);
    }

    public void updateCurrency() {

        if (null != invoiceModel.getContact()) {
            final Contact contact = contactService.getContact(invoiceModel.getContact().getContactId());
            invoiceModel.setCurrencyCode(contact.getCurrency());
        }
    }

}
