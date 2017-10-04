package com.simplevat.web.expense.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Currency;
import com.simplevat.entity.Expense;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.web.expense.model.ExpenseModel;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.expense.model.ExpenseItemModel;
import com.simplevat.web.utils.FacesUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;

@Controller
@SpringScopeView
public class ExpenseController extends ExpenseControllerHelper implements Serializable {

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
    private UserServiceNew userServiceNew;

    @Value("${file.upload.location}")
    private String fileLocation;

    @Getter
    @Setter
    private ExpenseModel selectedExpenseModel;

    @Getter
    @Setter
    private List<TransactionType> transactionTypes;
    
    @Getter
    @Setter
    private List<VatCategory> vatCategoryList = new ArrayList<>();

    @Getter
    private BigDecimal total;

    
      @Autowired
     private VatCategoryService vatCategoryService;

    @PostConstruct
    public void init() {
        Object objSelectedExpenseModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedExpenseModelId");
        if (objSelectedExpenseModel == null) {
            selectedExpenseModel = new ExpenseModel();
//            selectedExpenseModel.setAttachmentFile(new DefaultUploadedFile());
            selectedExpenseModel.addExpenseItem(new ExpenseItemModel());
            Currency defaultCurrency = currencyService.getDefaultCurrency();
            if (defaultCurrency != null) {
                selectedExpenseModel.setCurrency(defaultCurrency);
            }
            TransactionType transactionType = transactionTypeService.getDefaultTransactionType();
            if (transactionType != null) {
                selectedExpenseModel.setTransactionType(transactionType);
            }
            TransactionCategory transactionCategory = (TransactionCategory) transactionCategoryService.getDefaultTransactionCategory();
            if (transactionCategory != null) {
                selectedExpenseModel.setTransactionCategory(transactionCategory);
            }
            updateCurrencyLabel();
            transactionTypes = transactionTypeService.findAll();
        } else {

            Expense expense = expenseService.findByPK(Integer.parseInt(objSelectedExpenseModel.toString()));
            selectedExpenseModel = getExpenseModel(expense);
        }
        
        if(vatCategoryService.getVatCategoryList()!=null)
      {
        vatCategoryList=vatCategoryService.getVatCategoryList();
      } 
      
        calculateTotal();
    }

    public String createExpense() {
//        ExpenseModel expenseModel = new ExpenseModel();
//        expenseModel.setExpenseId(0);
//
//        Currency defaultCurrency = currencyService.getDefaultCurrency();
//        if (defaultCurrency != null) {
//            expenseModel.setCurrency(defaultCurrency);
//        }
//        TransactionType transactionType = transactionTypeService.getDefaultTransactionType();
//        if (transactionType != null) {
//            expenseModel.setTransactionType(transactionType);
//        }
//        TransactionCategory transactionCategory = (TransactionCategory) transactionCategoryService.getDefaultTransactionCategory();
//        if (transactionCategory != null) {
//            expenseModel.setTransactionCategory(transactionCategory);
//        }
//        this.setSelectedExpenseModel(expenseModel);
        return "/pages/secure/expense/create-expense.xhtml?faces-redirect=true";

    }

    public String saveExpense() {
        System.out.println("helllllllllllllllllooooooooooooooooooooooooo: :" + selectedExpenseModel.getExpenseId());
        User loggedInUser = FacesUtil.getLoggedInUser();
        Expense expense = getExpense(selectedExpenseModel);
        expense.setLastUpdateDate(LocalDateTime.now());
        expense.setLastUpdatedBy(loggedInUser.getUserId());
        expense.setDeleteFlag(false);
        expense.setCreatedBy(loggedInUser.getUserId());

        if (selectedExpenseModel.getReceiptAttachmentBinary() != null) {
            expense.setReceiptAttachmentBinary(selectedExpenseModel.getReceiptAttachmentBinary());
        }

        if (selectedExpenseModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(selectedExpenseModel.getTransactionType().getTransactionTypeCode());
            expense.setTransactionType(transactionType);
        }
        if (selectedExpenseModel.getTransactionCategory() != null) {
            TransactionCategory transactionCategory = transactionCategoryService.findByPK(selectedExpenseModel.getTransactionCategory().getTransactionCategoryCode());
            expense.setTransactionCategory(transactionCategory);
        }
        if (selectedExpenseModel.getCurrency() != null) {
            Currency currency = currencyService.getCurrency(selectedExpenseModel.getCurrency().getCurrencyCode());
            expense.setCurrency(currency);
        }
        if (selectedExpenseModel.getProject() != null) {
            Project project = projectService.findByPK(selectedExpenseModel.getProject().getProjectId());
            expense.setProject(project);
        }
        if (selectedExpenseModel.getUser() != null) {
            User user = userServiceNew.findByPK(selectedExpenseModel.getUser().getUserId());
            expense.setUser(user);
        }

        if (expense.getExpenseId() == null || expense.getExpenseId() == 0) {
            expenseService.persist(expense);
        } else {

            expenseService.update(expense, expense.getExpenseId());
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense saved successfully"));
        return "/pages/secure/expense/expenses.xhtml?faces-redirect=true";
    }

    public void saveAndContinueExpense() {
        System.out.println("selected Model : :" + selectedExpenseModel.getExpenseId());
        User loggedInUser = FacesUtil.getLoggedInUser();
        Expense expense = getExpense(selectedExpenseModel);

        expense.setLastUpdateDate(LocalDateTime.now());
        expense.setLastUpdatedBy(loggedInUser.getUserId());
        expense.setDeleteFlag(false);
        expense.setCreatedBy(loggedInUser.getUserId());

        if (selectedExpenseModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(selectedExpenseModel.getTransactionType().getTransactionTypeCode());
            expense.setTransactionType(transactionType);
        }
        if (selectedExpenseModel.getTransactionCategory() != null) {
            TransactionCategory transactionCategory = transactionCategoryService.findByPK(selectedExpenseModel.getTransactionCategory().getTransactionCategoryCode());
            expense.setTransactionCategory(transactionCategory);
        }
        if (selectedExpenseModel.getCurrency() != null) {
            Currency currency = currencyService.getCurrency(selectedExpenseModel.getCurrency().getCurrencyCode());
            expense.setCurrency(currency);
        }
        if (selectedExpenseModel.getProject() != null) {
            Project project = projectService.findByPK(selectedExpenseModel.getProject().getProjectId());
            expense.setProject(project);
        }
        if (selectedExpenseModel.getUser() != null) {
            User user = userServiceNew.findByPK(selectedExpenseModel.getUser().getUserId());
            expense.setUser(user);
        }

        if (this.getSelectedExpenseModel().getAttachmentFile().getSize() > 0) {
            storeUploadedFile(this.getSelectedExpenseModel(), expense, fileLocation);
        }

        if (expense.getExpenseId() == null || expense.getExpenseId() == 0) {
            expenseService.persist(expense);
        } else {
            if (selectedExpenseModel.getReceiptAttachmentBinary() != null) {
                expense.setReceiptAttachmentBinary(selectedExpenseModel.getReceiptAttachmentBinary());
            }
            expenseService.update(expense, expense.getExpenseId());
        }
        this.setSelectedExpenseModel(new ExpenseModel());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense saved successfully"));
    }

    public void fileUploadListener(FileUploadEvent e) {

        selectedExpenseModel.setReceiptAttachmentBinary(e.getFile().getContents());
    }

    public void deleteExpense() {
        System.out.println("selected Model : :" + selectedExpenseModel.getExpenseId());
        Expense expense = getExpense(selectedExpenseModel);
        expense.setDeleteFlag(true);
        expenseService.update(expense);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense deleted successfully"));
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
            if (lastItem.getQuatity() < 1 || lastItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage message = new FacesMessage("Please enter proper detail for all expence items.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("validationId", message);
                validated = false;
            }
        }
        return validated;
    }

    public void updateCurrencyLabel() {  //--------------
        if (null != selectedExpenseModel.getCurrency()) {
            selectedExpenseModel.setCurrency(currencyService.getCurrency(selectedExpenseModel.getCurrency().getCurrencyCode()));
        }
    }

    public void updateSubTotal(final ExpenseItemModel expenseItemModel) {
        final int quantity = expenseItemModel.getQuatity();
        final BigDecimal unitPrice = expenseItemModel.getUnitPrice();
        final BigDecimal vatPer = expenseItemModel.getVatId();
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
        transactionTypes = transactionTypeService.findAll();

        for (TransactionType type : transactionTypes) {
            filterList.add(type);
        }
        return filterList;
    }
    
    public List<SelectItem> getVatPercentageList(){
       List<SelectItem> vatCategorySelectItemList = new ArrayList<>();
        for (VatCategory vatCategory : vatCategoryList) {
            SelectItem item=new SelectItem(vatCategory.getVat(), vatCategory.getName());
            vatCategorySelectItemList.add(item);
            }
      return vatCategorySelectItemList;
    }
    
     private void calculateTotal(){
         total = new BigDecimal(0);
        List<ExpenseItemModel> expenseItem = selectedExpenseModel.getExpenseItem();
        if(expenseItem != null){
            for (ExpenseItemModel expense : expenseItem) {
                if (expense.getSubTotal() != null) {
                    total = total.add(expense.getSubTotal());
                }
            }
        }
    }    
}
