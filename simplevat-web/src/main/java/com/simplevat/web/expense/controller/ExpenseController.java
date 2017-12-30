package com.simplevat.web.expense.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Company;
import com.simplevat.entity.Contact;
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
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ContactService;
import com.simplevat.web.expense.model.ExpenseModel;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.constant.TransactionTypeConstant;
import com.simplevat.web.contact.model.ContactModel;
import com.simplevat.web.expense.model.ExpenseItemModel;
import com.simplevat.web.utils.FacesUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
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

    @Getter
    @Setter
    String fileName;

    @Getter
    @Setter
    private ContactModel contactModel;

    @Getter
    private Company company;

    @Getter
    @Setter
    private CurrencyConversion currencyConversion;

    @Getter
    @Setter
    ExpenseControllerHelper controllerHelper;

    public ExpenseController() {
        super(ModuleName.EXPENSE_MODULE);
    }

    @PostConstruct
    public void init() {
        company = companyService.findByPK(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()).getCompany().getCompanyId());
        contactModel = new ContactModel();
        controllerHelper = new ExpenseControllerHelper();
        Object objSelectedExpenseModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedExpenseModelId");
        if (objSelectedExpenseModel == null) {
            selectedExpenseModel = new ExpenseModel();
//            selectedExpenseModel.setAttachmentFile(new DefaultUploadedFile());
            selectedExpenseModel.setExpenseItem(new ArrayList<>());
            Currency defaultCurrency = company.getCompanyCountryCode().getCurrencyCode();
            if (defaultCurrency != null) {
                selectedExpenseModel.setCurrency(defaultCurrency);
            }
            updateCurrencyLabel();
            transactionTypes = transactionTypeService.findAll();
        } else {
            Expense expense = expenseService.findByPK(Integer.parseInt(objSelectedExpenseModel.toString()));
            selectedExpenseModel = controllerHelper.getExpenseModel(expense);
            if (selectedExpenseModel.getReceiptAttachmentBinary() != null) {
                InputStream stream = new ByteArrayInputStream(selectedExpenseModel.getReceiptAttachmentBinary());
                selectedExpenseModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
            }
        }

        populateVatCategory();

        calculateTotal();
    }

    public String createExpense() {

        return "/pages/secure/expense/expense.xhtml?faces-redirect=true";

    }

    public void updateCurrency() {
        setDefaultCurrency();
        if (selectedExpenseModel.getExpenseContact() != null) {
            final Contact contact = contactService
                    .getContact(selectedExpenseModel.getExpenseContact().getContactId());
            selectedExpenseModel.setCurrency(contact.getCurrency());
        }
    }

    private void setDefaultCurrency() {
        Currency defaultCurrency = company.getCompanyCountryCode().getCurrencyCode();
        if (defaultCurrency != null) {
            selectedExpenseModel.setCurrency(defaultCurrency);
        }
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

    public BigDecimal totalAmountInHomeCurrency(Currency currency) {
        if (total != null) {
            if (currencyConversion != null) {
                return total.divide(currencyConversion.getExchangeRate(), 9, RoundingMode.HALF_UP);
            }
        }
        return new BigDecimal(0);
    }

    public List<TransactionCategory> completeCategory() {
        List<TransactionCategory> transactionCategoryParentList = new ArrayList<>();
        List<TransactionCategory> transactionCategoryList = new ArrayList<>();
        transactionCategoryList = transactionCategoryService.findAllTransactionCategoryByTransactionType(TransactionTypeConstant.TRANSACTION_TYPE_EXPENSE);
        for (TransactionCategory transactionCategory : transactionCategoryList) {
            if (transactionCategory.getParentTransactionCategory() != null) {
                transactionCategoryParentList.add(transactionCategory.getParentTransactionCategory());
            }
        }
        selectedExpenseModel.setTransactionType(transactionCategoryList.get(0).getTransactionType());
        transactionCategoryList.removeAll(transactionCategoryParentList);
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
        if (vatCategoryService.getVatCategoryList() != null) {
            return vatCategoryService.getVatCategoryList();
        }
        return null;
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
                if(!validated){
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
        contact.setContactType(ContactTypeConstant.EMPLOYEE);
        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }

        if (contact.getContactId() != null) {
            contactService.update(contact);
        } else {
            contactService.persist(contact);
        }
        selectedExpenseModel.setExpenseContact(contact);

    }

    public String saveExpense() {
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        save();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense saved successfully"));
        return "/pages/secure/expense/list.xhtml?faces-redirect=true";
    }

    public String saveAndContinueExpense() {
        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        save();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense saved successfully"));
        return "/pages/secure/expense/expense.xhtml?faces-redirect=true";
    }

    private void save() {
        User loggedInUser = FacesUtil.getLoggedInUser();
        selectedExpenseModel.setTransactionType(selectedExpenseModel.getTransactionCategory().getTransactionType());
        Expense expense = controllerHelper.getExpense(selectedExpenseModel);
        expense.setExpenseAmount(total);
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
        selectedExpenseModel.setReceiptAttachmentBinary(e.getFile().getContents());
        if (selectedExpenseModel.getReceiptAttachmentBinary() != null) {
            InputStream stream = new ByteArrayInputStream(selectedExpenseModel.getReceiptAttachmentBinary());
            selectedExpenseModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
        }
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
                FacesMessage message = new FacesMessage("Please enter proper detail for all expence items.");
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
            if (vatPer != null && vatPer.compareTo(BigDecimal.ZERO) >= 1) {
                final BigDecimal amountWithTax = amountWithoutTax
                        .add(amountWithoutTax.multiply(vatPer).multiply(new BigDecimal(0.01)));
                expenseItemModel.setSubTotal(amountWithTax);
            }
        }
        calculateTotal();
    }

    public List<TransactionType> getAllTransactionType(String str) {
        if (str == null) {
            return this.transactionTypes;
        }
        List<TransactionType> filterList = new ArrayList<>();
        transactionTypes = transactionTypeService.executeNamedQuery("findMoneyOutTransactionType");
        for (TransactionType type : transactionTypes) {
            filterList.add(type);
        }
        return filterList;
    }

    private void calculateTotal() {
        total = new BigDecimal(0);
        List<ExpenseItemModel> expenseItem = selectedExpenseModel.getExpenseItem();
        if (expenseItem != null) {
            for (ExpenseItemModel expense : expenseItem) {
                if (expense.getSubTotal() != null) {
                    total = total.add(expense.getSubTotal());
                }
            }
        }
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

}
