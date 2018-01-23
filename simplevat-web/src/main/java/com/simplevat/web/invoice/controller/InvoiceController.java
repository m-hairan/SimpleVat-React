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

import com.simplevat.entity.Company;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.Country;
import com.simplevat.entity.CurrencyConversion;
import com.simplevat.entity.Product;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.CountryService;
import com.simplevat.service.ProductService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.DiscountTypeConstant;
import com.simplevat.web.constant.InvoicePaymentModeConstant;
import com.simplevat.web.constant.InvoicePurchaseStatusConstant;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.utils.FacesUtil;
import com.simplevat.web.utils.RecurringUtility;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Calendar;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hiren
 */
@Controller
@SpringScopeView
public class InvoiceController extends BaseController implements Serializable {

    private static final long serialVersionUID = 6299117288316809011L;
    private final static Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private UserServiceNew userServiceNew;

    @Autowired
    private InvoiceModelHelper invoiceModelHelper;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private DiscountTypeService discountTypeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private VatCategoryService vatCategoryService;

    @Autowired
    private ProductService productService;

    @Getter
    private InvoiceModel selectedInvoiceModel;

    @Getter
    private Company company;

    @Getter
    private Invoice selectedInvoice;

    @Getter
    private Configuration configuration;

    @Getter
    @Setter
    private ContactModel contactModel;

    @Getter
    @Setter
    private RecurringUtility recurringUtility;

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
    private CurrencyConversion currencyConversion;

    @Getter
    @Setter
    private List<VatCategory> vatCategoryList = new ArrayList<>();

    @Getter
    private boolean renderPrintInvoice = false;

    @Getter
    @Setter
    private boolean copyInvoiceAddress = false;

    @Getter
    @Setter
    InvoiceItemModel invoiceItemModel;

    @Getter
    private List<Country> countries = new ArrayList<>();

    @Getter
    @Setter
    private boolean defaultContactByproject = false;

    @Getter
    @Setter
    List<SelectItem> vatCategorySelectItemList = new ArrayList<>();

    @Getter
    BigDecimal discountAmount = new BigDecimal(0);

    @Getter
    BigDecimal totalInvoiceCalculation = new BigDecimal(0);

    @Getter
    BigDecimal totalVat = new BigDecimal(0);

    public InvoiceController() {
        super(ModuleName.INVOICE_MODULE);
    }

    @PostConstruct
    public void init() {

        recurringUtility = new RecurringUtility();
        company = companyService.findByPK(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()).getCompany().getCompanyId());
        Object objSelectedInvoice = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedInvoiceModelId");
        System.out.println("objSelectedInvoice :" + objSelectedInvoice);
        if (objSelectedInvoice != null) {
            renderPrintInvoice = true;
            selectedInvoice = invoiceService.findByPK(Integer.parseInt(objSelectedInvoice.toString()));
            selectedInvoiceModel = invoiceModelHelper.getInvoiceModel(selectedInvoice);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("invoiceId", selectedInvoice.getInvoiceId());
        } else {
            selectedInvoiceModel = new InvoiceModel();
            contactModel = new ContactModel();
            currencies = currencyService.getCurrencies();
            setDefaultCurrency();
            Object objSelectedContact = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("contactId");
            if (objSelectedContact != null) {
                selectedInvoiceModel.setInvoiceContact(contactService.findByPK(Integer.parseInt(objSelectedContact.toString())));
            }
            selectedInvoiceModel.setDiscount(new BigDecimal(0));
            selectedInvoiceModel.setInvoiceDate(new Date());
            selectedInvoiceModel.setInvoiceDueOn(30);
            selectedInvoiceModel.setInvoiceLineItems(new ArrayList());
            selectedInvoiceModel.setInvoiceReferenceNumber("1");
            selectedInvoiceModel.setInvoiceDueDate(getDueDate(selectedInvoiceModel));
            configurationList = configurationService.getConfigurationList();
            if (configurationList != null) {
                configuration = configurationList.stream().filter(conf -> conf.getName().equals(ConfigurationConstants.INVOICING_REFERENCE_PATTERN)).findFirst().get();
                if (configuration.getValue() != null) {
                    selectedInvoiceModel.setInvoiceReferenceNumber(invoiceModelHelper.getNextInvoiceRefNumber(configuration.getValue()));
                }
            }
            if (configuration == null) {
                configuration = new Configuration();
                configuration.setName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
            }
        }
        countries = countryService.getCountries();
        populateVatCategory();
        calculateTotal();
    }

    public void updateContact() {
        setDefaultCurrency();
        if (selectedInvoiceModel.getProject() != null) {
            selectedInvoiceModel.setInvoiceContact(selectedInvoiceModel.getProject().getContact());
            selectedInvoiceModel.setCurrencyCode(selectedInvoiceModel.getProject().getContact().getCurrency());
            defaultContactByproject = true;
        }
    }

    public void sameAsInvoicingAddress() {
        if (copyInvoiceAddress) {
            selectedInvoiceModel.setShippingContact(selectedInvoiceModel.getInvoiceContact());
        } else {
            selectedInvoiceModel.setShippingContact(null);
        }
    }

    private void setDefaultCurrency() {
        Currency defaultCurrency = company.getCompanyCountryCode().getCurrencyCode();
        if (defaultCurrency != null) {
            selectedInvoiceModel.setCurrencyCode(defaultCurrency);
        }
    }

    public void addInvoiceItem() {     //---------------
        boolean validated = validateInvoiceLineItems();

        if (validated) {
            selectedInvoiceModel.addInvoiceItem(new InvoiceItemModel());
        }
    }

    private boolean validateInvoiceLineItems() { //---------------
        boolean validated = true;
        for (InvoiceItemModel lastItem : selectedInvoiceModel.getInvoiceLineItems()) {
            StringBuilder validationMessage = new StringBuilder("Please enter ");
            if (lastItem.getUnitPrice() == null) {
                validationMessage.append("Unit Price ");
                validated = false;
            }
            if (validated && lastItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                validationMessage = new StringBuilder("Unit price should be greater than 0 ");
                validated = false;
            }
            if (lastItem.getQuatity() < 1) {
                if (!validated) {
                    validationMessage.append("and ");
                }
                validationMessage.append("Quantity should be greater than 0 ");
                validated = false;
            }
            if (!validated) {
                validationMessage.append("in invoice items.");
                FacesMessage message = new FacesMessage(validationMessage.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("validationId", message);
            }

        }
        return validated;
    }

    // TODO compare companycurrency and selected Currency
    public String exchangeRate(Currency currency) {
        String exchangeRateString = "";
        currencyConversion = currencyService.getCurrencyRateFromCurrencyConversion(currency.getCurrencyCode());
        if (currencyConversion != null) {
            exchangeRateString = "1 " + currency.getCurrencyIsoCode() + " = " + new BigDecimal(BigInteger.ONE).divide(currencyConversion.getExchangeRate(), 9, RoundingMode.HALF_UP) + " " + company.getCompanyCountryCode().getCurrencyCode().getCurrencyIsoCode();
        }
        return exchangeRateString;
    }

    private boolean validateAtLeastOneItem() {
        if (selectedInvoiceModel.getInvoiceLineItems().size() < 1) {
            FacesMessage message = new FacesMessage("Please add atleast one item to create Invoice.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("validationId", message);
            return false;
        }
        return true;
    }

    public List<Contact> contacts(final String searchQuery) {
        if (selectedInvoiceModel.getProject() != null) {
            List<Contact> contactList = new ArrayList<>();
            contactList.add(selectedInvoiceModel.getProject().getContact());
            return contactList;
        }
        return contactService.getContacts(searchQuery, ContactTypeConstant.CUSTOMER);
    }

    public List<Product> products(final String searchQuery) throws Exception {
        if (invoiceItemModel != null) {
            invoiceItemModel.setProductService(null);
        }
        List<Product> productList = productService.getProductList();
        if (productList != null) {
            List<Product> parentProductList = new ArrayList<>();
            for (Product product : productList) {
                if (product.getParentProduct() != null) {
                    parentProductList.add(product.getParentProduct());
                }
            }
            productList.removeAll(parentProductList);
            return productList;
        }
        return null;
    }

    public void refreshProductService(InvoiceItemModel itemModel) {
        itemModel.setProductService(null);
    }

    public List<VatCategory> vatCategorys(final String searchQuery) throws Exception {
        if (vatCategoryService.getVatCategoryList() != null) {
            return vatCategoryService.getVatCategoryList();
        }
        return null;
    }

    public void updateVatPercentage(InvoiceItemModel invoiceItemModel) {
        if (invoiceItemModel.getProductService() != null) {
            if (invoiceItemModel.getProductService().getVatCategory() != null) {
                VatCategory vatCategory = vatCategoryService.findByPK(invoiceItemModel.getProductService().getVatCategory().getId());
                invoiceItemModel.setVatId(vatCategory);
            } else {
                invoiceItemModel.setVatId(null);
            }
        }
        updateSubTotal(invoiceItemModel);
    }

    public List<Project> projects(final String searchQuery) throws Exception {
        ProjectCriteria criteria = new ProjectCriteria();
        criteria.setActive(Boolean.TRUE);
        if (searchQuery != null && !searchQuery.isEmpty()) {
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

    public List<Country> completeCountry(String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();
        Iterator<Country> countryIterator = this.countries.iterator();
        LOGGER.debug(" Size :" + countries.size());
        while (countryIterator.hasNext()) {
            Country country = countryIterator.next();
            if (country.getCountryName() != null
                    && !country.getCountryName().isEmpty()
                    && country.getCountryName().toUpperCase().contains(countryStr.toUpperCase())) {
                countrySuggestion.add(country);
            } else if (country.getIsoAlpha3Code() != null
                    && !country.getIsoAlpha3Code().isEmpty()
                    && country.getIsoAlpha3Code().toUpperCase().contains(countryStr.toUpperCase())) {
                countrySuggestion.add(country);
            }
        }
        LOGGER.debug(" Size :" + countrySuggestion.size());
        return countrySuggestion;
    }

    public String saveInvoice() throws IOException {
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        save();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Invoice Saved SuccessFully"));
        return "list?faces-redirect=true";

    }

    public String saveAndSendInvoice() throws IOException {

        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        Integer invoiceId = save();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Invoice Saved SuccessFully"));
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("invoiceId", invoiceId);
        return "invoiceView?faces-redirect=true";

    }

    public void saveAndAddMoreInvoice() {
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return;
        }
        save();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Invoice Saved SuccessFully"));
        init();
    }

    private Integer save() {
        selectedInvoiceModel.setInvoiceAmount(total);
        selectedInvoice = invoiceModelHelper.getInvoiceEntity(selectedInvoiceModel);

        if (selectedInvoice.getInvoiceId() != null && selectedInvoice.getInvoiceId() > 0) {
            selectedInvoice.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
            Invoice prevInvoice = invoiceService.findByPK(selectedInvoice.getInvoiceId());
            BigDecimal defferenceAmount = selectedInvoice.getInvoiceAmount().subtract(prevInvoice.getInvoiceAmount());
            if (selectedInvoice.getPaymentMode() == null) {
                selectedInvoice.setDueAmount(selectedInvoice.getDueAmount().add(defferenceAmount));
                updateDueAmountAndStatus();
            } else if (selectedInvoice.getPaymentMode() == InvoicePaymentModeConstant.CASH) {
                selectedInvoice.setDueAmount(new BigDecimal(0));
                selectedInvoice.setStatus(InvoicePurchaseStatusConstant.PAID);
                selectedInvoice.setFreeze(Boolean.TRUE);
            }
            if (selectedInvoice.getInvoiceProject() != null) {
                projectService.updateProjectRevenueBudget(defferenceAmount, selectedInvoice.getInvoiceProject());
            }
            companyService.updateCompanyRevenueBudget(defferenceAmount, company);
            invoiceService.update(selectedInvoice);
            return selectedInvoice.getInvoiceId();
        } else {
            configuration.setValue(selectedInvoice.getInvoiceReferenceNumber());
            if (configuration.getId() != null) {
                configurationService.update(configuration);
            } else {
                configurationService.persist(configuration);
            }
            selectedInvoice.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
            if (selectedInvoice.getPaymentMode() == null) {
                selectedInvoice.setDueAmount(selectedInvoice.getInvoiceAmount());
                updateDueAmountAndStatus();
            } else if (selectedInvoice.getPaymentMode() == InvoicePaymentModeConstant.CASH) {
                selectedInvoice.setDueAmount(new BigDecimal(0));
                selectedInvoice.setStatus(InvoicePurchaseStatusConstant.PAID);
                selectedInvoice.setFreeze(Boolean.TRUE);
            }
            if (selectedInvoice.getInvoiceProject() != null) {
                projectService.updateProjectRevenueBudget(selectedInvoice.getInvoiceAmount(), selectedInvoice.getInvoiceProject());
            }
            companyService.updateCompanyRevenueBudget(selectedInvoice.getInvoiceAmount(), company);
            invoiceService.persist(selectedInvoice);
            return selectedInvoice.getInvoiceId();
        }
    }

    public void updateDueAmountAndStatus() {
        if (selectedInvoice.getDueAmount().doubleValue() == selectedInvoice.getInvoiceAmount().doubleValue()) {
            selectedInvoice.setStatus(InvoicePurchaseStatusConstant.UNPAID);
        } else if (selectedInvoice.getDueAmount().doubleValue() < selectedInvoice.getInvoiceAmount().doubleValue()) {
            if (selectedInvoice.getDueAmount().doubleValue() == 0) {
                selectedInvoice.setStatus(InvoicePurchaseStatusConstant.PAID);
                selectedInvoice.setFreeze(Boolean.TRUE);
            } else {
                selectedInvoice.setStatus(InvoicePurchaseStatusConstant.PARTIALPAID);
            }
        }
    }

    public void updateCurrency() {
        setDefaultCurrency();
        if (null != selectedInvoiceModel.getInvoiceContact()) {
            final Contact contact = contactService
                    .getContact(selectedInvoiceModel.getInvoiceContact().getContactId());
            selectedInvoiceModel.setCurrencyCode(contact.getCurrency());
        }
    }

    public void updateSubTotal(final InvoiceItemModel invoiceItemModel) {
        final int quantity = invoiceItemModel.getQuatity();
        final BigDecimal unitPrice = invoiceItemModel.getUnitPrice();
        if (null != unitPrice) {
            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
            invoiceItemModel.setSubTotal(amountWithoutTax);

        }
        calculateTotal();
    }

    private void calculateTotal() {
        total = new BigDecimal(0);
        totalVat = new BigDecimal(0);
        List<InvoiceItemModel> invoiceItem = selectedInvoiceModel.getInvoiceLineItems();
        if (invoiceItem != null) {
            for (InvoiceItemModel invoice : invoiceItem) {
                if (invoice.getSubTotal() != null) {
                    total = total.add(invoice.getSubTotal());
                    if (invoice.getVatId() != null) {
                        totalVat = totalVat.add((invoice.getSubTotal().multiply(invoice.getVatId().getVat())).multiply(new BigDecimal(0.01)));
                        System.out.println("totalVat=========1===========" + totalVat);
                    }
                }
                System.out.println("totalVat=========2===========" + totalVat);
            }
        }
        System.out.println("totalVat=========3===========" + totalVat);
        if (selectedInvoiceModel.getDiscount() != null) {
            updateSubTotalOnDiscountAdded();

        }
    }

    public BigDecimal totalAmountInHomeCurrency(Currency currency) {
        if (total != null && currencyConversion != null) {
            if (currencyConversion != null) {
                return total.divide(currencyConversion.getExchangeRate(), 9, RoundingMode.HALF_UP);
            }
        }
        return new BigDecimal(0);
    }

    public void updateSubTotalOnDiscountAdded() {
        if (selectedInvoiceModel.getDiscount() == null) {
            calculateTotal();
        }
        calculateDiscount();
    }

    public void calculateDiscount() {
        if (selectedInvoiceModel.getDiscount() != null && total != null) {
            totalInvoiceCalculation = new BigDecimal(0);
            List<InvoiceItemModel> invoiceItem = selectedInvoiceModel.getInvoiceLineItems();
            if (invoiceItem != null) {
                for (InvoiceItemModel invoice : invoiceItem) {
                    if (invoice.getSubTotal() != null) {
                        totalInvoiceCalculation = totalInvoiceCalculation.add(invoice.getSubTotal());
                    }
                }
            }
            total = totalVat.add(totalInvoiceCalculation.subtract(getDiscountValue(totalInvoiceCalculation)));
            System.out.println("total=========3===========" + total);
        }
    }

    private BigDecimal getDiscountValue(BigDecimal totalInvoiceCalculation) {
        if (selectedInvoiceModel.getDiscountType() != null) {
            if (selectedInvoiceModel.getDiscountType().getDiscountTypeCode() == DiscountTypeConstant.ABSOLUTEDISCOUNT) {
                System.out.println("selectedInvoiceModel.getDiscount()=========1===========" + selectedInvoiceModel.getDiscount());
                discountAmount = selectedInvoiceModel.getDiscount();
            } else if (selectedInvoiceModel.getDiscountType().getDiscountTypeCode() == DiscountTypeConstant.PERCENTAGEDISCOUNT) {
                System.out.println("selectedInvoiceModel.getDiscount()=========1===========" + selectedInvoiceModel.getDiscount());
                discountAmount = totalInvoiceCalculation.multiply(selectedInvoiceModel.getDiscount().divide(new BigDecimal(100)));
            }
        }
        System.out.println("total=========1===========" + discountAmount);
        return discountAmount;
    }

    public void deleteLineItem(InvoiceItemModel itemModel) {
        selectedInvoiceModel.getInvoiceLineItems().remove(itemModel);
        if (itemModel.getSubTotal() != null && discountAmount != null) {
            total = total.subtract(itemModel.getSubTotal()).add(discountAmount);
            updateSubTotalOnDiscountAdded();
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
        contact.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
        contact.setCurrency(defaultCurrency);
        contact.setContactType(ContactTypeConstant.CUSTOMER);
        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }

        if (contact.getContactId() != null) {
            contactService.update(contact);
        } else {
            contactService.persist(contact);
        }
        selectedInvoiceModel.setInvoiceContact(contact);
        RequestContext.getCurrentInstance().execute("PF('add_contact_popup').hide();");
        initCreateContact();

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

    public void dueDateListener() {
        selectedInvoiceModel.setInvoiceDueDate(getDueDate(selectedInvoiceModel));
    }

    private Date getDueDate(InvoiceModel invoiceModel) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(invoiceModel.getInvoiceDate());
        calendar.add(Calendar.DAY_OF_YEAR, invoiceModel.getInvoiceDueOn());
        return calendar.getTime();
    }

}
