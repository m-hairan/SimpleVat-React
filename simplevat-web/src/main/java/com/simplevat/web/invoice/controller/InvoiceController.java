package com.simplevat.web.invoice.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Project;
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.ContactService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.DiscountTypeService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.contact.model.ContactModel;
import com.simplevat.web.invoice.model.InvoiceItemModel;
import com.simplevat.web.invoice.model.InvoiceModel;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.simplevat.entity.Company;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.VatCategoryService;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.utils.FacesUtil;
import java.util.Calendar;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

/**
 * @author Hiren
 */
@Controller
@SpringScopeView
public class InvoiceController extends InvoiceModelHelper implements Serializable {

    private static final long serialVersionUID = 6299117288316809011L;

    @Getter
    private InvoiceModel selectedInvoiceModel;

    @Getter
    private Invoice selectedInvoice;

    @Getter
    private Configuration configuration;

    @Getter
    @Setter
    private ContactModel contactModel;

    @Getter
    @Setter
    private List<Currency> currencies;

    @Getter
    @Setter
    private List<Configuration> configurationList;

    @Getter
    private BigDecimal total;

    @Getter
    @Setter
    private List<VatCategory> vatCategoryList = new ArrayList<>();

    @Getter
    private boolean renderPrintInvoice;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private DiscountTypeService discountTypeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private VatCategoryService vatCategoryService;

    @Getter
    @Setter
    List<SelectItem> vatCategorySelectItemList = new ArrayList<>();

    @PostConstruct
    public void init() {
        Object objSelectedInvoice = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedInvoiceModelId");
        System.out.println("objSelectedInvoice :" + objSelectedInvoice);
        if (objSelectedInvoice != null) {

            selectedInvoice = invoiceService.findByPK(Integer.parseInt(objSelectedInvoice.toString()));
            selectedInvoiceModel = getInvoiceModel(selectedInvoice);
            renderPrintInvoice = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("invoiceId", selectedInvoice.getInvoiceId());
        } else {
            selectedInvoiceModel = new InvoiceModel();
            contactModel = new ContactModel();
            currencies = currencyService.getCurrencies();
            setDefaultCurrency();
            selectedInvoiceModel.setInvoiceDate(new Date());
            selectedInvoiceModel.setInvoiceDueOn(30);
            updateCurrencyLabel();
            selectedInvoiceModel.setInvoiceItems(new ArrayList());
            configurationList = configurationService.getConfigurationList();
            configuration = configurationList.stream().filter(conf -> conf.getName().equals(ConfigurationConstants.INVOICING_REFERENCE_PATTERN)).findFirst().get();
            if (configuration.getValue() != null) {
                selectedInvoiceModel.setInvoiceRefNo(getNextInvoiceRefNumber(configuration.getValue()));
            }
        }
        populateVatCategory();

        calculateTotal();
    }

    private void setDefaultCurrency() {
        Currency defaultCurrency = currencyService.getDefaultCurrency();
        if (defaultCurrency != null) {
            selectedInvoiceModel.setCurrencyCode(defaultCurrency);
        }
    }

    public void addInvoiceItem() {     //---------------
        boolean validated = validateInvoiceLineItems();

        if (validated) {
            updateCurrencyLabel();
            selectedInvoiceModel.addInvoiceItem(new InvoiceItemModel());
        }

    }

    private boolean validateInvoiceLineItems() { //---------------
        boolean validated = true;
        for (InvoiceItemModel lastItem : selectedInvoiceModel.getInvoiceItems()) {
            if (lastItem.getQuatity() < 1 || lastItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage message = new FacesMessage("Please enter proper detail for all invoice items.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("validationId", message);
                validated = false;
            }
        }
        return validated;
    }

    private boolean validateAtLeastOneItem() {
        if (selectedInvoiceModel.getInvoiceItems().size() < 1) {
            FacesMessage message = new FacesMessage("Please add atleast one item to create Invoice.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("validationId", message);
            return false;
        }
        return true;
    }

    public List<Contact> contacts(final String searchQuery) {
        return contactService.getContacts(searchQuery);
    }

    public List<Project> projects(final String searchQuery) throws Exception {
        ProjectCriteria criteria = new ProjectCriteria();
        criteria.setActive(Boolean.TRUE);
        if (!isNullOrEmpty(searchQuery)) {
            criteria.setProjectName(searchQuery);
        }
        return projectService.getProjectsByCriteria(criteria);
    }

    public List<Currency> completeCurrency(String currencyStr) {
        if (this.currencies == null) {
            this.currencies = currencyService.getCurrencies();
        }
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
        final List<DiscountType> types = discountTypeService.getDiscountTypes();
        for (DiscountType type : types) {
            if (null != searchString
                    && !type.toString().toLowerCase().contains(searchString.toLowerCase())) {
                types.remove(type);
            }
        }
        return types;
    }

    public String printInvoice() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("invoiceId", selectedInvoice.getInvoiceId());
        return "invoicePDF.xhtml?faces-redirect=true";
    }

    public String saveInvoice() throws IOException {

        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        selectedInvoiceModel.setInvoiceDueDate(getDueDate(selectedInvoiceModel));
        selectedInvoice = getInvoiceEntity(selectedInvoiceModel);
        if (selectedInvoice.getInvoiceId() != null && selectedInvoice.getInvoiceId() > 0) {
            invoiceService.update(selectedInvoice);
        } else {
            configuration.setValue(selectedInvoice.getInvoiceReferenceNumber());
            configurationService.update(configuration);
            invoiceService.persist(selectedInvoice);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        if (selectedInvoiceModel.getInvoiceId() != null && selectedInvoiceModel.getInvoiceId() > 0) {
            context.addMessage(null, new FacesMessage("Invoice Updated SuccessFully"));
        } else {
            context.addMessage(null, new FacesMessage("Invoice Added SuccessFully"));
        }
        init();

        return "list?faces-redirect=true";

    }

    public void saveAndAddMoreInvoice() {
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return;
        }
        Invoice invoice = getInvoiceEntity(selectedInvoiceModel);
        invoiceService.update(invoice, invoice.getInvoiceId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (selectedInvoiceModel.getInvoiceId() > 0) {
            context.addMessage(null, new FacesMessage("Invoice Updated SuccessFully"));
        } else {
            context.addMessage(null, new FacesMessage("Invoice Added SuccessFully"));
        }
        init();

    }

    public void updateCurrency() {
        setDefaultCurrency();
        if (null != selectedInvoiceModel.getContact()) {
            final Contact contact = contactService
                    .getContact(selectedInvoiceModel.getContact().getContactId());
            selectedInvoiceModel.setCurrencyCode(contact.getCurrency());
        }
    }

    public void updateCurrencyLabel() {  //--------------
        if (null != selectedInvoiceModel.getCurrencyCode()) {
            selectedInvoiceModel.setCurrencyCode(currencyService.getCurrency(selectedInvoiceModel.getCurrencyCode().getCurrencyCode()));
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
        calculateTotal();
    }

    private void calculateTotal() {
        total = new BigDecimal(0);
        List<InvoiceItemModel> invoiceItem = selectedInvoiceModel.getInvoiceItems();
        if (invoiceItem != null) {
            for (InvoiceItemModel invoice : invoiceItem) {
                if (invoice.getSubTotal() != null) {
                    total = total.add(invoice.getSubTotal());
                }
            }
        }
    }

    public void initCreateContact() {
        contactModel = new ContactModel();
    }

    public void createContact() {
        Currency defaultCurrency = currencyService.getDefaultCurrency();
        final Contact contact = new Contact();

        contact.setBillingEmail(contactModel.getEmail());
        contact.setDeleteFlag(Boolean.FALSE);
        contact.setEmail(contactModel.getEmail());
        contact.setFirstName(contactModel.getFirstName());
        contact.setLastName(contactModel.getLastName());
        contact.setOrganization(contactModel.getOrganization());
        contact.setCreatedBy(1);
        contact.setCurrency(defaultCurrency);
        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }

        if (contact.getContactId() != null) {
            contactService.update(contact);
        } else {
            contactService.persist(contact);
        }
        selectedInvoiceModel.setContact(contact);

    }

    private void populateVatCategory() {
        vatCategoryList = vatCategoryService.getVatCategoryList();
        if (vatCategoryList != null) {
            for (VatCategory vatCategory : vatCategoryList) {
                SelectItem item = new SelectItem(vatCategory.getVat(), vatCategory.getName());
                vatCategorySelectItemList.add(item);
            }
        }
    }

    private Date getDueDate(InvoiceModel invoiceModel) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(invoiceModel.getInvoiceDate());
        calendar.add(Calendar.DAY_OF_YEAR, invoiceModel.getInvoiceDueOn());
        return calendar.getTime();
    }

}
