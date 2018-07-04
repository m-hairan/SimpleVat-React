package com.simplevat.web.expense.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Company;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Country;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.simplevat.entity.Currency;
import com.simplevat.entity.CurrencyConversion;
import com.simplevat.entity.Expense;
import com.simplevat.entity.Product;
import com.simplevat.entity.Project;
import com.simplevat.entity.Title;
import com.simplevat.entity.User;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ContactService;
import com.simplevat.service.CountryService;
import com.simplevat.web.expense.model.ExpenseModel;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.ProductService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.TitleService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.constant.TransactionTypeConstant;
import com.simplevat.web.contact.controller.ContactHelper;
import com.simplevat.web.contact.controller.ContactUtil;
import com.simplevat.web.contact.model.ContactModel;
import com.simplevat.web.contact.model.ContactType;
import com.simplevat.web.expense.model.ExpenseItemModel;
import com.simplevat.web.invoice.model.InvoiceItemModel;
import com.simplevat.web.productservice.model.ProductModel;
import com.simplevat.web.utils.FacesUtil;
import com.simplevat.web.utils.RecurringUtility;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

@Controller
@SpringScopeView
public class ExpenseController extends BaseController implements Serializable {

    private static final long serialVersionUID = 5366159429842989755L;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserServiceNew userServiceNew;

    @Getter
    @Setter
    private ExpenseModel selectedExpenseModel;

    @Getter
    @Setter
    private List<TransactionType> transactionTypes;

    @Getter
    @Setter
    private List<TransactionCategory> transactionCategorys = new ArrayList<>();

    @Getter
    @Setter
    private List<VatCategory> vatCategoryList = new ArrayList<>();

    @Getter
    private BigDecimal total;

    @Autowired
    private VatCategoryService vatCategoryService;

    @Getter
    @Setter
    List<SelectItem> vatCategorySelectItemList = new ArrayList<>();

    @Autowired
    private ContactService contactService;
    @Autowired
    private CountryService countryService;

    @Getter
    @Setter
    String fileName;

    @Getter
    @Setter
    private ContactModel contactModel;

    @Getter
    @Setter
    private RecurringUtility recurringUtility;

    @Getter
    private Company company;

    @Getter
    @Setter
    private CurrencyConversion currencyConversion;

    @Getter
    @Setter
    ExpenseControllerHelper controllerHelper;

    @Getter
    private List<Title> titles = new ArrayList<>();

    @Getter
    private List<Country> countries = new ArrayList<>();
    @Getter
    private List<Currency> currencies = new ArrayList<>();

    @Autowired
    private ProductService productService;

    @Getter
    @Setter
    private ProductModel productModel;

    private ExpenseItemModel expenseItemModelForProductUpdateOnProductAdd;

    @Getter
    private BigDecimal vatTotal;

    @Getter
    private BigDecimal totalAmount;
    
    @Getter
    private String expenseView;

    public ExpenseController() {
        super(ModuleName.EXPENSE_MODULE);
    }

    @PostConstruct
    public void init() {
        recurringUtility = new RecurringUtility();
        productModel = new ProductModel();
        company = companyService.findByPK(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()).getCompany().getCompanyId());
        contactModel = new ContactModel();
        controllerHelper = new ExpenseControllerHelper();
        System.out.println("expenseView"+expenseView);
        Object objSelectedExpenseModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedExpenseModelId");
        if (objSelectedExpenseModel == null) {
            selectedExpenseModel = new ExpenseModel();
            Object objSelectedContact = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("contactId");
            if (objSelectedContact != null) {
                selectedExpenseModel.setExpenseContact(contactService.findByPK(Integer.parseInt(objSelectedContact.toString())));
            }
//            selectedExpenseModel.setAttachmentFile(new DefaultUploadedFile());
            selectedExpenseModel.setExpenseItem(new ArrayList<>());
            Currency defaultCurrency = company.getCurrencyCode();
            if (defaultCurrency != null) {
                selectedExpenseModel.setCurrency(defaultCurrency);
            }
            updateCurrencyLabel();
//            transactionTypes = transactionTypeService.findAll();
        } else {
            Expense expense = expenseService.findByPK(Integer.parseInt(objSelectedExpenseModel.toString()));
            selectedExpenseModel = controllerHelper.getExpenseModel(expense);
            if (selectedExpenseModel.getReceiptAttachmentName() != null) {
                if (selectedExpenseModel.getReceiptAttachmentName().length() > 27) {
                    selectedExpenseModel.setReceiptAttachmentName(selectedExpenseModel.getReceiptAttachmentName().substring(0, 27).concat("..."));
                }
            }
            if (selectedExpenseModel.getReceiptAttachmentBinary() != null) {
                InputStream stream = new ByteArrayInputStream(selectedExpenseModel.getReceiptAttachmentBinary());
                selectedExpenseModel.setAttachmentFileContent(new DefaultStreamedContent(stream, selectedExpenseModel.getReceiptAttachmentContentType(), selectedExpenseModel.getReceiptAttachmentName()));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("STREAMED_CONTENT_PREVIEW_PIC", selectedExpenseModel.getReceiptAttachmentBinary());
            }
        }
        titles = titleService.getTitles();
        countries = countryService.getCountries();
        currencies = currencyService.getCurrencies();
        selectedExpenseModel.setUser(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()));
        setDefaultCountry();
        setDefaultContactCurrency();
        calculateTotal();
        addLineItem();
    }

    public String createExpense() {
        return "/pages/secure/expense/expense.xhtml?faces-redirect=true";
    }

    public void createExpenses() {
       selectedExpenseModel.getExpenseItem().remove(selectedExpenseModel.getExpenseItem().size()-1);
    }
    private boolean validateInvoiceItem() { //---------------
        boolean validated = true;
        for (int i = 0; i < selectedExpenseModel.getExpenseItem().size() - 1; i++) {
            ExpenseItemModel lastItem = selectedExpenseModel.getExpenseItem().get(i);
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
                validationMessage.append("in expense items.");
                FacesMessage message = new FacesMessage(validationMessage.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("validationId", message);
            }
        }
        return validated;
    }

    public void updateVatPercentage(ExpenseItemModel expenseItemModel) {
        if (expenseItemModel != null) {
            expenseItemModel.setIsProductSelected(Boolean.FALSE);
            if (expenseItemModel.getExpenseLineItemProductService() != null) {
                if (expenseItemModel.getExpenseLineItemProductService().getVatCategory() != null) {
                    VatCategory vatCategory = expenseItemModel.getExpenseLineItemProductService().getVatCategory();
                    expenseItemModel.setVatId(vatCategory);
                } else {
                    expenseItemModel.setVatId(vatCategoryService.getDefaultVatCategory());
                }
                if (expenseItemModel.getExpenseLineItemProductService().getVatIncluded()) {
                    if (expenseItemModel.getExpenseLineItemProductService().getVatCategory() != null) {
                        BigDecimal unit = (expenseItemModel.getExpenseLineItemProductService().getUnitPrice().divide(expenseItemModel.getExpenseLineItemProductService().getVatCategory().getVat().add(new BigDecimal(100)), 5, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
                        expenseItemModel.setUnitPrice(unit);
                    }
                } else {
                    expenseItemModel.setUnitPrice(expenseItemModel.getExpenseLineItemProductService().getUnitPrice());
                }
                expenseItemModel.setDescription(expenseItemModel.getExpenseLineItemProductService().getProductDescription());
            }
            updateSubTotal(expenseItemModel);
            addInvoiceItemOnProductSelect();
        }
    }

    public void addInvoiceItemOnProductSelect() {
        if (validateInvoiceItem()) {
            addLineItem();
        }
    }

    public void addLineItem() {
        ExpenseItemModel expenseItemModel = new ExpenseItemModel();
        VatCategory vatCategory = vatCategoryService.getDefaultVatCategory();
        expenseItemModel.setVatId(vatCategory);
        expenseItemModel.setUnitPrice(BigDecimal.ZERO);
        selectedExpenseModel.addExpenseItem(expenseItemModel);
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

    public List<Country> completeCountry(String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();

        Iterator<Country> countryIterator = this.countries.iterator();

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

        return countrySuggestion;
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

    private void setDefaultCountry() {
        Country defaultCountry = company.getCompanyCountryCode();
        if (defaultCountry != null) {
            contactModel.setCountry(defaultCountry);
        }
    }

    private void setDefaultContactCurrency() {
        Currency defaultCurrency = company.getCurrencyCode();
        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }
    }

    public void updateCurrency() {
        setDefaultCurrency();
        if (selectedExpenseModel.getExpenseContact() != null) {
            final Contact contact = contactService
                    .getContact(selectedExpenseModel.getExpenseContact().getContactId());
            selectedExpenseModel.setCurrency(contact.getCurrency());
        }
    }

    public List<Product> products(final String searchQuery) throws Exception {
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

    public void updateContact() {
        if (selectedExpenseModel.getProject() != null) {
            selectedExpenseModel.setExpenseContact(selectedExpenseModel.getProject().getContact());
        }
    }

    private void setDefaultCurrency() {
        Currency defaultCurrency = company.getCurrencyCode();
        if (defaultCurrency != null) {
            selectedExpenseModel.setCurrency(defaultCurrency);
        }
    }

    // TODO compare companycurrency and selected Currency
    public String exchangeRate(Currency currency) {
        String exchangeRateString = "";
        currencyConversion = currencyService.getCurrencyRateFromCurrencyConversion(currency.getCurrencyCode());
        if (currencyConversion != null) {
            exchangeRateString = currencyConversion.getExchangeRate() + "";
        }
        return exchangeRateString;
    }

    public BigDecimal totalAmountInHomeCurrency(Currency currency) {
        if (totalAmount != null) {
            if (currencyConversion != null) {
                return totalAmount.divide(currencyConversion.getExchangeRate(), 9, RoundingMode.HALF_UP);
            }
        }
        return new BigDecimal(0);
    }

    public List<TransactionCategory> completeCategory(String name) {

        System.out.println("name==" + name);
        List<TransactionCategory> transactionCategoryParentList = new ArrayList<>();
        List<TransactionCategory> transactionCategoryList = new ArrayList<>();
        transactionCategoryList = transactionCategoryService.findAllTransactionCategoryByTransactionType(TransactionTypeConstant.TRANSACTION_TYPE_EXPENSE, name);
        if (transactionCategoryList != null && !transactionCategoryList.isEmpty()) {
            for (TransactionCategory transactionCategory : transactionCategoryList) {
                if (transactionCategory.getParentTransactionCategory() != null) {
                    transactionCategoryParentList.add(transactionCategory.getParentTransactionCategory());
                }
            }

            selectedExpenseModel.setTransactionType(transactionCategoryList.get(0).getTransactionType());
            transactionCategoryList.removeAll(transactionCategoryParentList);
        }
        return transactionCategoryList;

    }

    public List<TransactionCategory> getAllTranscationCategories(String categories) {
        return transactionCategorys;
    }

    public void deleteLineItem(ExpenseItemModel expenseItemModel) {
        selectedExpenseModel.getExpenseItem().remove(expenseItemModel);
        if (expenseItemModel.getSubTotal() != null) {
            total = total.subtract(expenseItemModel.getSubTotal());
        }
    }

    public List<Contact> contacts(final String searchQuery) {
        return contactService.getContacts(searchQuery, ContactTypeConstant.EMPLOYEE);
    }

    public List<VatCategory> vatCategorys(final String searchQuery) throws Exception {
//        if (vatCategoryService.getVatCategoryList(searchQuery) != null) {
//            return vatCategoryService.getVatCategoryList();
//        }
//        return null;
        return vatCategoryService.getVatCategoryList();
    }

    private boolean validateInvoiceLineItems() { //---------------
        boolean validated = true;
        for (ExpenseItemModel lastItem : selectedExpenseModel.getExpenseItem()) {
            StringBuilder validationMessage = new StringBuilder("Please Enter ");
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
                validationMessage.append("in Expense items");
                FacesMessage message = new FacesMessage(validationMessage.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("validationId", message);
                validated = false;
            }

        }
        return validated;
    }

    private boolean validateAtLeastOneItem() {
        if (selectedExpenseModel.getExpenseItem().size() < 1) {
            FacesMessage message = new FacesMessage("Please add atleast one item to create Expense.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("validationId", message);
            return false;
        }
        return true;
    }

    public void initCreateContact() {
        contactModel = new ContactModel();
    }

    public void createContact() {
        Contact contact = new Contact();
        ContactHelper contactHelper = new ContactHelper();
        User loggedInUser = FacesUtil.getLoggedInUser();
        contact = contactHelper.getContact(contactModel);
        contact.setCreatedBy(loggedInUser.getUserId());
        contact.setCreatedDate(LocalDateTime.now());
        contact.setDeleteFlag(Boolean.FALSE);
        contact.setContactType(ContactTypeConstant.EMPLOYEE);
        if (contact.getContactId() != null && contact.getContactId() > 0) {
            this.contactService.update(contact);
        } else {

            this.contactService.persist(contact);
        }
        selectedExpenseModel.setExpenseContact(contact);
        RequestContext.getCurrentInstance().execute("PF('add_contact_popup').hide();");
        initCreateContact();
    }

    public void reserveProductOnAdd(ExpenseItemModel expenseItemModel) {
        expenseItemModelForProductUpdateOnProductAdd = expenseItemModel;
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
        expenseItemModelForProductUpdateOnProductAdd.setExpenseLineItemProductService(product);
        if (!product.getVatIncluded()) {
            expenseItemModelForProductUpdateOnProductAdd.setUnitPrice(product.getUnitPrice());
        } else {
            if (product.getVatCategory() != null) {

                BigDecimal unit = (product.getUnitPrice().divide(product.getVatCategory().getVat().add(new BigDecimal(100)), 5, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
                expenseItemModelForProductUpdateOnProductAdd.setUnitPrice(unit);
            }
        }

        if (product.getVatCategory() != null) {
            expenseItemModelForProductUpdateOnProductAdd.setVatId(product.getVatCategory());
        } else {
            expenseItemModelForProductUpdateOnProductAdd.setVatId(vatCategoryService.getDefaultVatCategory());
        }
        expenseItemModelForProductUpdateOnProductAdd.setDescription(product.getProductDescription());
        if (expenseItemModelForProductUpdateOnProductAdd.getIsProductSelected()) {
            addInvoiceItemOnProductSelect();
        }
        RequestContext.getCurrentInstance().execute("PF('add_product_popup').hide();");
        initCreateProduct();
    }

    public List<Product> completeProducts() {
        List<Product> productList = productService.getProductList();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        return productList;
    }

    public void initCreateProduct() {
        productModel = new ProductModel();
    }

    public String saveExpense() {
        removeEmptyRow();
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        save();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("", "Expense saved successfully"));
        return "/pages/secure/expense/list.xhtml?faces-redirect=true";
    }

    public String saveAndContinueExpense() {
        removeEmptyRow();
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        save();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("", "Expense saved successfully"));
        return "/pages/secure/expense/expense.xhtml?faces-redirect=true";
    }

    private void save() {
        User loggedInUser = FacesUtil.getLoggedInUser();
        selectedExpenseModel.setTransactionType(selectedExpenseModel.getTransactionCategory().getTransactionType());
        Expense expense = controllerHelper.getExpense(selectedExpenseModel);
        expense.setExpenseAmount(totalAmount);
        if (currencyConversion != null) {
            expense.setExpencyAmountCompanyCurrency(totalAmount.divide(currencyConversion.getExchangeRate(), 9, RoundingMode.HALF_UP));
        } else {
            expense.setExpencyAmountCompanyCurrency(totalAmount);
        }
        if (selectedExpenseModel.getReceiptAttachmentBinary() != null) {
            expense.setReceiptAttachmentBinary(selectedExpenseModel.getReceiptAttachmentBinary());
        }
        if (expense.getExpenseId() == null || expense.getExpenseId() == 0) {
            expense.setDeleteFlag(false);
            expense.setCreatedBy(loggedInUser.getUserId());
            expense.setCreatedDate(LocalDateTime.now());
            if (expense.getProject() != null) {
                projectService.updateProjectExpenseBudget(expense.getExpenseAmount(), expense.getProject());
            }
            companyService.updateCompanyExpenseBudget(expense.getExpenseAmount(), company);
            expenseService.persist(expense);
        } else {
            expense.setLastUpdateDate(LocalDateTime.now());
            expense.setLastUpdateBy(loggedInUser.getUserId());
            Expense prevExpense = expenseService.findByPK(expense.getExpenseId());
            BigDecimal defferenceAmount = expense.getExpenseAmount().subtract(prevExpense.getExpenseAmount());
            if (expense.getProject() != null) {
                projectService.updateProjectExpenseBudget(defferenceAmount, expense.getProject());
            }
            companyService.updateCompanyExpenseBudget(defferenceAmount, company);
            expenseService.update(expense);
        }
    }

    public void fileUploadListener(FileUploadEvent e) {
        fileName = e.getFile().getFileName();
        selectedExpenseModel.setReceiptAttachmentName(fileName);
        selectedExpenseModel.setReceiptAttachmentContentType(e.getFile().getContentType());
        selectedExpenseModel.setReceiptAttachmentBinary(e.getFile().getContents());
        if (selectedExpenseModel.getReceiptAttachmentBinary() != null) {
            InputStream stream = new ByteArrayInputStream(selectedExpenseModel.getReceiptAttachmentBinary());
            selectedExpenseModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
        }
    }

    public void removeFile() {
        fileName = null;
        selectedExpenseModel.setReceiptAttachmentName(null);
        selectedExpenseModel.setReceiptAttachmentContentType(null);
        selectedExpenseModel.setReceiptAttachmentBinary(null);
        selectedExpenseModel.setAttachmentFileContent(null);
    }

    public List<User> users(final String searchQuery) throws Exception {
        return userServiceNew.executeNamedQuery("findAllUsers");

    }

    public void addExpenseItem() {     //---------------
        boolean validated = validateExpenceLineItems();
        if (validated) {
            updateCurrencyLabel();
            selectedExpenseModel.addExpenseItem(new ExpenseItemModel());
        }
    }

    private boolean validateExpenceLineItems() { //---------------
        boolean validated = true;
        for (ExpenseItemModel lastItem : selectedExpenseModel.getExpenseItem()) {
            if (lastItem.getUnitPrice() == null || lastItem.getQuatity() < 1 || lastItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage message = new FacesMessage("Please enter proper detail for all expense items.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("validationId", message);
                validated = false;
            }
        }
        return validated;
    }

    public void updateCurrencyLabel() {
        if (null != selectedExpenseModel.getCurrency()) {
            selectedExpenseModel.setCurrency(currencyService.getCurrency(selectedExpenseModel.getCurrency().getCurrencyCode()));
        }
    }

    public void updateSubTotal(final ExpenseItemModel expenseItemModel) {
        final int quantity = expenseItemModel.getQuatity();
        final BigDecimal unitPrice = expenseItemModel.getUnitPrice();
        BigDecimal vatPer = new BigDecimal(BigInteger.ZERO);
        if (expenseItemModel.getVatId() != null) {
            vatPer = expenseItemModel.getVatId().getVat();
        }
        if (null != unitPrice) {
            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
            expenseItemModel.setSubTotal(amountWithoutTax);
//            if (vatPer != null && vatPer.compareTo(BigDecimal.ZERO) >= 1) {
//                final BigDecimal amountWithTax = amountWithoutTax
//                        .add(amountWithoutTax.multiply(vatPer).multiply(new BigDecimal(0.01)));
//                expenseItemModel.setSubTotal(amountWithTax);
//            }
        }
        calculateTotal();
    }

//    public List<TransactionType> getAllTransactionType(String str) {
//        if (str == null) {
//            return this.transactionTypes;
//        }
//        List<TransactionType> filterList = new ArrayList<>();
//        transactionTypes = transactionTypeService.executeNamedQuery("findMoneyOutTransactionType");
//        for (TransactionType type : transactionTypes) {
//            filterList.add(type);
//        }
//        return filterList;
//    }
    private void calculateTotal() {
        total = new BigDecimal(0);
        vatTotal = new BigDecimal(0);
        List<ExpenseItemModel> expenseItem = selectedExpenseModel.getExpenseItem();
        if (expenseItem != null) {
            for (ExpenseItemModel expense : expenseItem) {
                if (expense.getSubTotal() != null) {
                    total = total.add(expense.getSubTotal());
                    if (expense.getUnitPrice() != null) {
                        BigDecimal totalAmount = expense.getUnitPrice().multiply(new BigDecimal(expense.getQuatity()));
                        if (expense.getVatId() != null) {
                            vatTotal = vatTotal.add((totalAmount.multiply(expense.getVatId().getVat())).divide(new BigDecimal(100)));
                        } else {
                            vatTotal = vatTotal.add(totalAmount);
                        }
                    }
                }
            }
        }
        selectedExpenseModel.setExpenseSubtotal(total);
        selectedExpenseModel.setExpenseVATAmount(vatTotal);
        totalAmount = total.add(vatTotal);
    }

    private void removeEmptyRow() {
        if (selectedExpenseModel.getExpenseItem().size() > 1) {
            List<ExpenseItemModel> expenseItemModels = new ArrayList<>();
            for (ExpenseItemModel expenseItemModel : selectedExpenseModel.getExpenseItem()) {
                if ((expenseItemModel.getProductName() == null || expenseItemModel.getProductName().isEmpty()) || expenseItemModel.getQuatity() == 0) {
                    expenseItemModels.add(expenseItemModel);
                }
            }
            selectedExpenseModel.getExpenseItem().removeAll(expenseItemModels);
        }
    }

}
