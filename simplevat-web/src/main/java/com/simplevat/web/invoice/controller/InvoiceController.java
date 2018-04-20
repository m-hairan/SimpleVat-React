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
import com.simplevat.entity.Title;
import com.simplevat.entity.User;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.CountryService;
import com.simplevat.service.ProductService;
import com.simplevat.service.TitleService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ConfigurationConstants;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.InvoicePaymentModeConstant;
import com.simplevat.web.constant.InvoicePurchaseStatusConstant;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.contact.controller.ContactHelper;
import com.simplevat.web.contact.controller.ContactUtil;
import com.simplevat.web.contact.model.ContactType;
import com.simplevat.web.productservice.model.ProductModel;
import com.simplevat.web.utils.FacesUtil;
import com.simplevat.web.utils.RecurringUtility;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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

    @Autowired
    private TitleService titleService;

    @Getter
    @Setter
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
    private ProductModel productModel;

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

    InvoiceItemModel invoiceItemModelForProductUpdateOnProductAdd;

    @Getter
    private List<Title> titles = new ArrayList<>();

    public InvoiceController() {
        super(ModuleName.INVOICE_MODULE);
    }

    @PostConstruct
    public void init() {
        contactModel = new ContactModel();
        productModel = new ProductModel();
        recurringUtility = new RecurringUtility();
        company = companyService.findByPK(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()).getCompany().getCompanyId());
        Object objSelectedInvoice = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedInvoiceModelId");
        System.out.println("objSelectedInvoice :" + objSelectedInvoice);
        if (objSelectedInvoice != null) {
            renderPrintInvoice = true;
            selectedInvoice = invoiceService.findByPK(Integer.parseInt(objSelectedInvoice.toString()));
            selectedInvoiceModel = invoiceModelHelper.getInvoiceModel(selectedInvoice, true);
            if (selectedInvoiceModel.getShippingContact() != null) {
                if (selectedInvoiceModel.getInvoiceContact().getContactId() == selectedInvoiceModel.getShippingContact().getContactId()) {
                    copyInvoiceAddress = true;
                }
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("invoiceId", selectedInvoice.getInvoiceId());
        } else {
            selectedInvoiceModel = new InvoiceModel();
            currencies = currencyService.getCurrencies();
            setDefaultCurrency();
            Object objSelectedContact = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("contactId");
            if (objSelectedContact != null) {
                selectedInvoiceModel.setInvoiceContact(contactService.findByPK(Integer.parseInt(objSelectedContact.toString())));
            }
            selectedInvoiceModel.setDiscount(new BigDecimal(0));
            selectedInvoiceModel.setInvoiceDate(new Date());
            selectedInvoiceModel.setInvoiceDueOn(30);
            selectedInvoiceModel.setInvoiceReferenceNumber("1");
            selectedInvoiceModel.setInvoiceDueDate(getDueDate(selectedInvoiceModel));
            configuration = configurationService.getConfigurationByName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
            if (configuration != null) {
                if (configuration.getValue() != null) {
                    generateInvoiceRefNumber();
                }
            } else {
                configuration = new Configuration();
                configuration.setValue("1");
                configuration.setName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
            }
        }
        titles = titleService.getTitles();
        countries = countryService.getCountries();
        currencies = currencyService.getCurrencies();
        setDefaultCountry();
        setDefaultContactCurrency();
        countries = countryService.getCountries();
        addLineItem();
        //populateVatCategory();
        updateSubTotalOnDiscountAdded();

    }

    private void generateInvoiceRefNumber() {
        selectedInvoiceModel.setInvoiceReferenceNumber(invoiceModelHelper.getNextInvoiceRefNumber(configuration.getValue(), selectedInvoiceModel));
    }

    public List<Title> completeTitle(String titleStr) {
        List<Title> titleSuggestion = new ArrayList<>();

        Iterator<Title> titleIterator = this.titles.iterator();

        while (titleIterator.hasNext()) {
            Title title = titleIterator.next();
            if (title.getTitleDescription() != null
                    && !title.getTitleDescription().isEmpty()
                    && title.getTitleDescription().toUpperCase().contains(titleStr.toUpperCase())) {
                titleSuggestion.add(title);
            }
        }

        return titleSuggestion;
    }

    public List<ContactType> completeContactType() {
        return ContactUtil.contactTypeList();
    }

    private void setDefaultCountry() {
        Country defaultCountry = company.getCompanyCountryCode();
        if (defaultCountry != null) {
            contactModel.setCountry(defaultCountry);
        }
    }

    private void setDefaultContactCurrency() {
        Currency defaultCurrency = company.getCompanyCountryCode().getCurrencyCode();
        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }
    }

    public void updateContact() {
        setDefaultCurrency();
        if (selectedInvoiceModel.getProject() != null) {
            selectedInvoiceModel.setInvoiceContact(selectedInvoiceModel.getProject().getContact());
            selectedInvoiceModel.setCurrencyCode(selectedInvoiceModel.getProject().getContact().getCurrency());
            defaultContactByproject = true;
        }
        generateInvoiceRefNumber();
        if (copyInvoiceAddress) {
            sameAsInvoicingAddress();
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
            selectedInvoiceModel.setInvoiceLineItems(new ArrayList());
            RequestContext.getCurrentInstance().update("invoice:lineItems");
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
        List<VatCategory> vatCategorys = new ArrayList<>();
        vatCategorys = vatCategoryService.getVatCategorys(searchQuery);
        return vatCategorys;

    }

    public void addInvoiceItemOnProductSelect() {
        System.out.println("==validateInvoiceItem()="+validateInvoiceItem());
        if (validateInvoiceItem()) {
            addLineItem();
        }
    }

    private boolean validateInvoiceItem() { //---------------
        boolean validated = true;
        for (int i = 0; i < selectedInvoiceModel.getInvoiceLineItems().size() - 1; i++) {
            InvoiceItemModel lastItem = selectedInvoiceModel.getInvoiceLineItems().get(i);
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

    private boolean validateInvoiceLineItems() { //---------------
        boolean validated = true;
        for (InvoiceItemModel lastItem : selectedInvoiceModel.getInvoiceLineItems()) {
            StringBuilder validationMessage = new StringBuilder("Please enter ");
            if (lastItem.getProductService() == null) {
                validationMessage.append("Product/Service ");
                validated = false;
            }
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

    public void updateVatPercentage(InvoiceItemModel invoiceItemModel, Integer listSize) {
        if (invoiceItemModel.getProductService() != null) {
            if (invoiceItemModel.getProductService().getVatCategory() != null) {
                VatCategory vatCategory = invoiceItemModel.getProductService().getVatCategory();
                invoiceItemModel.setVatId(vatCategory);

            } else {
                invoiceItemModel.setVatId(vatCategoryService.getDefaultVatCategory());
            }

            if (invoiceItemModel.getProductService().getVatIncluded()) {
                if (invoiceItemModel.getProductService().getVatCategory() != null) {
                    BigDecimal unit = (invoiceItemModel.getProductService().getUnitPrice().divide(invoiceItemModel.getProductService().getVatCategory().getVat().add(new BigDecimal(100)), 5, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
                    invoiceItemModel.setUnitPrice(unit);
                }
            } else {
                invoiceItemModel.setUnitPrice(invoiceItemModel.getProductService().getUnitPrice());
            }
            invoiceItemModel.setDescription(invoiceItemModel.getProductService().getProductDescription());
        }

        addInvoiceItemOnProductSelect();
    }

    public void updateSubTotal(InvoiceItemModel invoiceItem) {
        invoiceModelHelper.processAmountCalculation(selectedInvoiceModel);

    }

    public void updateQuantity(InvoiceItemModel invoiceItem) {
        invoiceItem.setQuatity(0);
    }

    public void reserveProductOnAdd(InvoiceItemModel invoiceItem) {
        invoiceItemModelForProductUpdateOnProductAdd = invoiceItem;
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
        removeEmptyRow();
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            if (selectedInvoiceModel.getInvoiceLineItems().get(selectedInvoiceModel.getInvoiceLineItems().size() - 1).getProductService() != null) {
                addLineItem();
            }
            return "";
        }
        save();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("", "Invoice saved successfully"));
        return "list?faces-redirect=true";

    }

    public String saveAndSendInvoice() throws IOException {
        removeEmptyRow();
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            if (selectedInvoiceModel.getInvoiceLineItems().get(selectedInvoiceModel.getInvoiceLineItems().size() - 1).getProductService() != null) {
                addLineItem();
            }
            return "";
        }
        Integer invoiceId = save();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Successful", "Invoice saved Successfully"));
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("invoiceId", invoiceId);
        return "invoiceView?faces-redirect=true";

    }

    public void saveAndAddMoreInvoice() {
        removeEmptyRow();
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            if (selectedInvoiceModel.getInvoiceLineItems().get(selectedInvoiceModel.getInvoiceLineItems().size() - 1).getProductService() != null) {
                addLineItem();
            }
            return;
        }
        save();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("", "Invoice saved successfully"));
        init();
    }

    private Integer save() {
        if (selectedInvoiceModel.getShippingContact() == null) {
            selectedInvoiceModel.setShippingContact(null);
        }
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
            configuration = configurationService.getConfigurationByName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
            if (configuration == null) {
                configuration = new Configuration();
                configuration.setValue("1");
                configuration.setName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
            }
            String newInvoicePattern = invoiceModelHelper.getNextInvoiceRefNumber(configuration.getValue(), selectedInvoiceModel);
            configuration.setValue(newInvoicePattern);
            selectedInvoice.setInvoiceReferenceNumber(newInvoicePattern);
            System.out.println("newInvoicePattern===" + newInvoicePattern + "===id:==" + configuration.getId());
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
        generateInvoiceRefNumber();
        if (copyInvoiceAddress) {
            sameAsInvoicingAddress();
        }
    }

//    public void updateSubTotal(final InvoiceItemModel invoiceItemModel) {
//        final int quantity = invoiceItemModel.getQuatity();
//        final BigDecimal unitPrice = invoiceItemModel.getUnitPrice();
//        if (null != unitPrice) {
//            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
//            invoiceItemModel.setSubTotal(amountWithoutTax);
//        }
//        calculateNetTotal();
//    }
//    public void calculateNetTotal() {
//        total = new BigDecimal(0);
//        totalVat = new BigDecimal(0);
//        totalInvoiceCalculation = new BigDecimal(0);
//        discountAmount = new BigDecimal(BigInteger.ZERO);
//        List<InvoiceItemModel> invoiceItem = selectedInvoiceModel.getInvoiceLineItems();
//        if (invoiceItem != null) {
//            for (InvoiceItemModel itemModel : invoiceItem) {
//                if (itemModel.getSubTotal() != null) {
//                    BigDecimal vatAmount = new BigDecimal(BigInteger.ZERO);
//                    BigDecimal itemAmount = new BigDecimal(BigInteger.ZERO);
//                    if (itemModel.getVatId() != null) {
//                        vatAmount = (itemModel.getSubTotal().multiply(itemModel.getVatId().getVat())).multiply(new BigDecimal(0.01));
//                    }
//                    if (selectedInvoiceModel.getDiscountType() != null) {
//                        itemAmount = itemModel.getSubTotal().subtract(getDiscountValue(itemModel.getSubTotal()));
//                    } else {
//                        itemAmount = itemModel.getSubTotal();
//                    }
//                    totalInvoiceCalculation = totalInvoiceCalculation.add(itemAmount);
//                    totalVat = totalVat.add(vatAmount);
//                    total = total.add(itemAmount.add(vatAmount));
//                }
//            }
//        }
//    }
//    public BigDecimal totalAmountInHomeCurrency(Currency currency) {
//        if (total != null && currencyConversion != null) {
//            if (currencyConversion != null) {
//                return total.divide(currencyConversion.getExchangeRate(), 9, RoundingMode.HALF_UP);
//            }
//        }
//        return new BigDecimal(0);
//    }
    public void updateSubTotalOnDiscountAdded() {
        invoiceModelHelper.processAmountCalculation(selectedInvoiceModel);
    }

//    public void calculateTotalWithDiscount() {
//        total = new BigDecimal(0);
//        totalVat = new BigDecimal(0);
//        totalInvoiceCalculation = new BigDecimal(0);
//        if (selectedInvoiceModel.getDiscount() != null && total != null) {
//            totalInvoiceCalculation = new BigDecimal(0);
//            List<InvoiceItemModel> invoiceItem = selectedInvoiceModel.getInvoiceLineItems();
//            if (invoiceItem != null) {
//                for (InvoiceItemModel itemModel : invoiceItem) {
//                    if (itemModel.getSubTotal() != null) {
//                        BigDecimal itemAmount = itemModel.getSubTotal().subtract(getDiscountValue(itemModel.getSubTotal()));
//                        BigDecimal vatAmount = (itemModel.getSubTotal().multiply(itemModel.getVatId().getVat())).multiply(new BigDecimal(0.01));
//                        totalInvoiceCalculation = totalInvoiceCalculation.add(itemAmount);
//                        totalVat = totalVat.add(vatAmount);
//                        total = totalInvoiceCalculation.add(vatAmount);
//                    }
//                }
//            }
//            totalInvoiceCalculation = totalInvoiceCalculation.subtract(getDiscountValue(totalInvoiceCalculation));
//            total = totalInvoiceCalculation;
//            System.out.println("total=========3===========" + total);
//        }
//    }
//    private BigDecimal getDiscountValue(BigDecimal totalInvoiceCalculation) {
//        BigDecimal discount = BigDecimal.ZERO;
//        if (selectedInvoiceModel.getDiscountType().getDiscountTypeCode() == DiscountTypeConstant.ABSOLUTEDISCOUNT) {
//            discount = selectedInvoiceModel.getDiscount();
//        } else if (selectedInvoiceModel.getDiscountType().getDiscountTypeCode() == DiscountTypeConstant.PERCENTAGEDISCOUNT) {
//            discount = discount.add(totalInvoiceCalculation.multiply(selectedInvoiceModel.getDiscount()).divide(new BigDecimal(100)));
//        }
//        discountAmount = discountAmount.add(discount);
//        System.out.println("discount :"+discount);
//        System.out.println("discountAmount :"+discountAmount);
//        return discount;
//    }
    public void deleteLineItem(InvoiceItemModel itemModel) {
        selectedInvoiceModel.getInvoiceLineItems().remove(itemModel);
        updateSubTotalOnDiscountAdded();
//        if (itemModel.getSubTotal() != null && discountAmount != null) {
//            total = total.subtract(itemModel.getSubTotal()).add(getDiscountValue(itemModel.getSubTotal()));
//            updateSubTotalOnDiscountAdded();
//        }
    }

    public void initCreateContact() {
        contactModel = new ContactModel();
        setDefaultCountry();
        setDefaultContactCurrency();
    }

    public void initCreateProduct() {
        productModel = new ProductModel();
    }

    public List<Product> completeProducts() {
        List<Product> productList = productService.getProductList();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        return productList;
    }

    public void createContact() {
        Contact contact = new Contact();
        ContactHelper contactHelper = new ContactHelper();
        User loggedInUser = FacesUtil.getLoggedInUser();
        contact = contactHelper.getContact(contactModel);
        contact.setCreatedBy(loggedInUser.getUserId());
        contact.setCreatedDate(LocalDateTime.now());
        contact.setDeleteFlag(Boolean.FALSE);
        contact.setContactType(ContactTypeConstant.CUSTOMER);
        if (contact.getContactId() != null && contact.getContactId() > 0) {
            this.contactService.update(contact);
        } else {

            this.contactService.persist(contact);
        }
        selectedInvoiceModel.setInvoiceContact(contact);
        System.out.println("selectedInvoiceModel" + selectedInvoiceModel.getInvoiceContact());
        RequestContext.getCurrentInstance().execute("PF('add_contact_popup').hide();");
        initCreateContact();

    }

    public void createProduct() {
        final Product product = new Product();
        product.setProductID(productModel.getProductID());
        product.setProductName(productModel.getProductName());
        product.setProductDescription(productModel.getProductDescription());
        product.setProductCode(productModel.getProductCode());
        product.setUnitPrice(productModel.getUnitPrice());
        product.setDeleteFlag(Boolean.FALSE);
        product.setParentProduct(productModel.getParentProduct());
        product.setVatCategory(productModel.getVatCategory());
        product.setProductDescription(productModel.getProductDescription());
        product.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
        product.setCreatedDate(LocalDateTime.now());
        product.setVatIncluded(productModel.getVatIncluded());
        if (product.getProductID() != null) {
            productService.update(product);
        } else {
            productService.persist(product);
        }
        invoiceItemModelForProductUpdateOnProductAdd.setProductService(product);

        if (!product.getVatIncluded()) {
            invoiceItemModelForProductUpdateOnProductAdd.setUnitPrice(product.getUnitPrice());
        } else {
            if (product.getVatCategory() != null) {
                BigDecimal unit = (product.getUnitPrice().divide(product.getVatCategory().getVat().add(new BigDecimal(100)), 5, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
                invoiceItemModel.setUnitPrice(unit);

            }
        }

        if (product.getVatCategory() != null) {
            invoiceItemModelForProductUpdateOnProductAdd.setVatId(product.getVatCategory());
        } else {
            invoiceItemModelForProductUpdateOnProductAdd.setVatId(vatCategoryService.getDefaultVatCategory());
        }
        invoiceItemModelForProductUpdateOnProductAdd.setDescription(product.getProductDescription());
        addLineItem();
        RequestContext.getCurrentInstance().execute("PF('add_product_popup').hide();");
        initCreateProduct();

    }

    public void addLineItem() {
        InvoiceItemModel invoiceItemModel = new InvoiceItemModel();
        VatCategory vatCategory = vatCategoryService.getDefaultVatCategory();
        invoiceItemModel.setVatId(vatCategory);
        invoiceItemModel.setUnitPrice(BigDecimal.ZERO);
        selectedInvoiceModel.addInvoiceItem(invoiceItemModel);
    }

//    private void populateVatCategory() {
//        String name="";
//        vatCategoryList = vatCategoryService.getVatCategoryList("");
//        if (vatCategoryList != null) {
//            for (VatCategory vatCategory : vatCategoryList) {
//                SelectItem item = new SelectItem(vatCategory.getVat(), vatCategory.getName());
//                vatCategorySelectItemList.add(item);
//            }
//        }
//    }
    public void dueDateListener() {
        selectedInvoiceModel.setInvoiceDueDate(getDueDate(selectedInvoiceModel));
    }

    private Date getDueDate(InvoiceModel invoiceModel) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(invoiceModel.getInvoiceDate());
        calendar.add(Calendar.DAY_OF_YEAR, invoiceModel.getInvoiceDueOn());
        return calendar.getTime();
    }

    private void removeEmptyRow() {
        List<InvoiceItemModel> invoiceLineItemList = new ArrayList<>();
        for (InvoiceItemModel invoiceLineItem : selectedInvoiceModel.getInvoiceLineItems()) {
            if (invoiceLineItem.getProductService() == null && (invoiceLineItem.getUnitPrice() == null || invoiceLineItem.getUnitPrice().compareTo(BigDecimal.ZERO) == 0)
                    && invoiceLineItem.getQuatity() == 0 && (invoiceLineItem.getDescription() == null || invoiceLineItem.getDescription().isEmpty())) {
                invoiceLineItemList.add(invoiceLineItem);
            }
        }
        selectedInvoiceModel.getInvoiceLineItems().removeAll(invoiceLineItemList);
        if (selectedInvoiceModel.getInvoiceLineItems().isEmpty()) {
            addLineItem();
        }
    }

}
