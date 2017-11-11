package com.simplevat.web.purchase.controller;

import com.simplevat.web.expense.controller.*;
import com.github.javaplugs.jsf.SpringScopeView;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Currency;
import com.simplevat.entity.Project;
import com.simplevat.entity.Purchase;
import com.simplevat.entity.User;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.PurchaseService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.expense.model.ExpenseItemModel;
import com.simplevat.web.purchase.model.PurchaseItemModel;
import com.simplevat.web.purchase.model.PurchaseModel;
import com.simplevat.web.utils.FacesUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

@Controller
@SpringScopeView
public class PurchaseController extends BaseController implements Serializable {

    private static final long serialVersionUID = 5366159429842989755L;

    @Autowired
    private PurchaseService purchaseService;

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

    @Getter
    @Setter
    private PurchaseModel selectedPurchaseModel;

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

    @Getter
    @Setter
    List<SelectItem> vatCategorySelectItemList = new ArrayList<>();

    @Getter
    @Setter
    String fileName;

    @Getter
    @Setter
    PurchaseControllerHelper purchaseControllerHelper;

    public PurchaseController() {
        super(ModuleName.PURCHASE_MODULE);
    }

    @PostConstruct
    public void init() {
        purchaseControllerHelper = new PurchaseControllerHelper();
        Object objSelectedPurchaseModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedPurchaseModelId");
        selectedPurchaseModel = new PurchaseModel();
        if (objSelectedPurchaseModel == null) {
            selectedPurchaseModel.setPurchaseItems(new ArrayList<>());
            Currency defaultCurrency = currencyService.getDefaultCurrency();
            if (defaultCurrency != null) {
                selectedPurchaseModel.setCurrency(defaultCurrency);
            }
        } else {
            Purchase purchase = purchaseService.findByPK(Integer.parseInt(objSelectedPurchaseModel.toString()));
            selectedPurchaseModel = purchaseControllerHelper.getPurchaseModel(purchase);

            if (selectedPurchaseModel.getReceiptAttachmentBinary() != null) {
                InputStream stream = new ByteArrayInputStream(selectedPurchaseModel.getReceiptAttachmentBinary());
                selectedPurchaseModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
            }
        }
        updateCurrencyLabel();
        populateVatCategory();
        calculateTotal();
    }

    public String createExpense() {

        return "/pages/secure/expense/create-expense.xhtml?faces-redirect=true";

    }

    public List<User> users(final String searchQuery) throws Exception {
        return userServiceNew.executeNamedQuery("findAllUsers");
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

    public List<TransactionCategory> completeCategory() {
        List<TransactionCategory> transactionCategoryParentList = new ArrayList<>();
        List<TransactionCategory> transactionCategoryList = new ArrayList<>();
        if (selectedPurchaseModel.getTransactionType() != null) {
            transactionCategoryList = transactionCategoryService.findAllTransactionCategoryByTransactionType(selectedPurchaseModel.getTransactionType().getTransactionTypeCode());
        }
        for (TransactionCategory transactionCategory : transactionCategoryList) {
            if (transactionCategory.getParentTransactionCategory() != null) {
                transactionCategoryParentList.add(transactionCategory.getParentTransactionCategory());
            }
        }
        transactionCategoryList.removeAll(transactionCategoryParentList);
        return transactionCategoryList;

    }

    public void deleteLineItem(PurchaseItemModel itemModel) {
        selectedPurchaseModel.getPurchaseItems().remove(itemModel);
        if (itemModel.getSubTotal() != null) {
            total = total.subtract(itemModel.getSubTotal());
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

    public void updateCurrencyLabel() {
        if (null != selectedPurchaseModel.getCurrency()) {
            selectedPurchaseModel.setCurrency(currencyService.getCurrency(selectedPurchaseModel.getCurrency().getCurrencyCode()));
        }
    }

    public void addPurchaseItem() {     //---------------
        boolean validated = validatePurchaseLineItems();

        if (validated) {
            updateCurrencyLabel();
            selectedPurchaseModel.addPurchaseItem(new PurchaseItemModel());
        }

    }

    private boolean validatePurchaseLineItems() { //---------------
        boolean validated = true;
        for (PurchaseItemModel lastItem : selectedPurchaseModel.getPurchaseItems()) {
            if (lastItem.getUnitPrice() == null ||lastItem.getQuatity() < 1 || lastItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage message = new FacesMessage("Please enter proper detail for all expence items.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("validationId", message);
                validated = false;
            }
        }
        return validated;
    }

    private boolean validateAtLeastOneItem() {
        if (selectedPurchaseModel.getPurchaseItems().size() < 1) {
            FacesMessage message = new FacesMessage("Please add atleast one item to create Expense.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("validationId", message);
            return false;
        }
        return true;
    }

    public String deletePurchase() {
        Purchase purchase = purchaseControllerHelper.getPurchase(selectedPurchaseModel);
        purchase.setDeleteFlag(true);
        purchaseService.update(purchase);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Purchase deleted successfully"));
        return "purchase-list.xhtml?faces-redirect=true";
    }

    public String savePurchase() {
        if (!validatePurchaseLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        User loggedInUser = FacesUtil.getLoggedInUser();
        Purchase purchase = purchaseControllerHelper.getPurchase(selectedPurchaseModel);
        purchase.setLastUpdateDate(LocalDateTime.now());
        purchase.setLastUpdateBy(loggedInUser.getUserId());
        purchase.setDeleteFlag(false);
        purchase.setCreatedBy(loggedInUser.getUserId());

        if (selectedPurchaseModel.getReceiptAttachmentBinary() != null) {
            purchase.setReceiptAttachmentBinary(selectedPurchaseModel.getReceiptAttachmentBinary());
        }

        if (selectedPurchaseModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(selectedPurchaseModel.getTransactionType().getTransactionTypeCode());
            purchase.setTransactionType(transactionType);
        }
        if (selectedPurchaseModel.getTransactionCategory() != null) {
            TransactionCategory transactionCategory = transactionCategoryService.findByPK(selectedPurchaseModel.getTransactionCategory().getTransactionCategoryCode());
            purchase.setTransactionCategory(transactionCategory);
        }
        if (selectedPurchaseModel.getCurrency() != null) {
            Currency currency = currencyService.getCurrency(selectedPurchaseModel.getCurrency().getCurrencyCode());
            purchase.setCurrency(currency);
        }
        if (selectedPurchaseModel.getProject() != null) {
            Project project = projectService.findByPK(selectedPurchaseModel.getProject().getProjectId());
            purchase.setProject(project);
        }
        if (selectedPurchaseModel.getUser() != null) {
            User user = userServiceNew.findByPK(selectedPurchaseModel.getUser().getUserId());
            purchase.setUser(user);
        }

        purchase.setPurchaseAmount(total);

        if (purchase.getPurchaseId() == null || purchase.getPurchaseId() == 0) {
            purchaseService.persist(purchase);
        } else {
            purchaseService.update(purchase, purchase.getPurchaseId());
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense saved successfully"));
        return "/pages/secure/purchase/purchase-list.xhtml?faces-redirect=true";
    }

    public String saveAndContinuePurchase() {
        if (!validatePurchaseLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        User loggedInUser = FacesUtil.getLoggedInUser();
        Purchase purchase = purchaseControllerHelper.getPurchase(selectedPurchaseModel);

        purchase.setLastUpdateDate(LocalDateTime.now());
        purchase.setLastUpdateBy(loggedInUser.getUserId());
        purchase.setDeleteFlag(false);
        purchase.setCreatedBy(loggedInUser.getUserId());

        if (selectedPurchaseModel.getReceiptAttachmentBinary() != null) {
            purchase.setReceiptAttachmentBinary(selectedPurchaseModel.getReceiptAttachmentBinary());
        }

        if (selectedPurchaseModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(selectedPurchaseModel.getTransactionType().getTransactionTypeCode());
            purchase.setTransactionType(transactionType);
        }
        if (selectedPurchaseModel.getTransactionCategory() != null) {
            TransactionCategory transactionCategory = transactionCategoryService.findByPK(selectedPurchaseModel.getTransactionCategory().getTransactionCategoryCode());
            purchase.setTransactionCategory(transactionCategory);
        }
        if (selectedPurchaseModel.getCurrency() != null) {
            Currency currency = currencyService.getCurrency(selectedPurchaseModel.getCurrency().getCurrencyCode());
            purchase.setCurrency(currency);
        }
        if (selectedPurchaseModel.getProject() != null) {
            Project project = projectService.findByPK(selectedPurchaseModel.getProject().getProjectId());
            purchase.setProject(project);
        }
        if (selectedPurchaseModel.getUser() != null) {
            User user = userServiceNew.findByPK(selectedPurchaseModel.getUser().getUserId());
            purchase.setUser(user);
        }

        purchase.setPurchaseAmount(total);

        if (purchase.getPurchaseId() == null || purchase.getPurchaseId() == 0) {
            purchaseService.persist(purchase);
        } else {

            purchaseService.update(purchase, purchase.getPurchaseId());
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense saved successfully"));
        return "/pages/secure/purchase/purchase.xhtml?faces-redirect=true";
    }

//
//    public String saveAndContinueExpense() {
//        if (!validateInvoiceLineItems() || !validateAtLeastOneItem()) {
//            return "";
//        }
//        User loggedInUser = FacesUtil.getLoggedInUser();
//        Expense expense = getExpense(selectedExpenseModel);
//
//        expense.setLastUpdateDate(LocalDateTime.now());
//        expense.setLastUpdateBy(loggedInUser.getUserId());
//        expense.setDeleteFlag(false);
//        expense.setCreatedBy(loggedInUser.getUserId());
//
//        if (selectedExpenseModel.getTransactionType() != null) {
//            TransactionType transactionType = transactionTypeService.getTransactionType(selectedExpenseModel.getTransactionType().getTransactionTypeCode());
//            expense.setTransactionType(transactionType);
//        }
//        if (selectedExpenseModel.getTransactionCategory() != null) {
//            TransactionCategory transactionCategory = transactionCategoryService.findByPK(selectedExpenseModel.getTransactionCategory().getTransactionCategoryCode());
//            expense.setTransactionCategory(transactionCategory);
//        }
//        if (selectedExpenseModel.getCurrency() != null) {
//            Currency currency = currencyService.getCurrency(selectedExpenseModel.getCurrency().getCurrencyCode());
//            expense.setCurrency(currency);
//        }
//        if (selectedExpenseModel.getProject() != null) {
//            Project project = projectService.findByPK(selectedExpenseModel.getProject().getProjectId());
//            expense.setProject(project);
//        }
//        if (selectedExpenseModel.getUser() != null) {
//            User user = userServiceNew.findByPK(selectedExpenseModel.getUser().getUserId());
//            expense.setUser(user);
//        }
//        if (expense.getExpenseId() == null || expense.getExpenseId() == 0) {
//            expenseService.persist(expense);
//        } else {
//            if (selectedExpenseModel.getReceiptAttachmentBinary() != null) {
//                expense.setReceiptAttachmentBinary(selectedExpenseModel.getReceiptAttachmentBinary());
//            }
//            expenseService.update(expense, expense.getExpenseId());
//        }
//        this.setSelectedExpenseModel(new ExpenseModel());
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense saved successfully"));
//        return "/pages/secure/expense/create-expense.xhtml?faces-redirect=true";
//    }
//
    public void fileUploadListener(FileUploadEvent e) {
        fileName = e.getFile().getFileName();
        selectedPurchaseModel.setReceiptAttachmentBinary(e.getFile().getContents());
        if (selectedPurchaseModel.getReceiptAttachmentBinary() != null) {
            InputStream stream = new ByteArrayInputStream(selectedPurchaseModel.getReceiptAttachmentBinary());
            selectedPurchaseModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
        }

    }

    public void updateSubTotal(final PurchaseItemModel itemModel) {
        final int quantity = itemModel.getQuatity();
        final BigDecimal unitPrice = itemModel.getUnitPrice();
        final BigDecimal vatPer = itemModel.getVatId();
        if (null != unitPrice) {
            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
            itemModel.setSubTotal(amountWithoutTax);
            if (vatPer != null && vatPer.compareTo(BigDecimal.ZERO) >= 1) {
                final BigDecimal amountWithTax = amountWithoutTax
                        .add(amountWithoutTax.multiply(vatPer).multiply(new BigDecimal(0.01)));
                itemModel.setSubTotal(amountWithTax);
            }
        }
        calculateTotal();
    }

    private void calculateTotal() {
        total = new BigDecimal(0);
        List<PurchaseItemModel> purchaseItemModels = selectedPurchaseModel.getPurchaseItems();
        if (purchaseItemModels != null) {
            for (PurchaseItemModel itemModel : purchaseItemModels) {
                if (itemModel.getSubTotal() != null) {
                    total = total.add(itemModel.getSubTotal());
                }
            }
        }
    }

//
//    public String deleteExpense() {
//        System.out.println("selected Model : :" + selectedExpenseModel.getExpenseId());
//        Expense expense = getExpense(selectedExpenseModel);
//        expense.setDeleteFlag(true);
//        expenseService.update(expense);
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expense deleted successfully"));
//        return "expenses.xhtml?faces-redirect=true";
//    }
//
//    public List<User> users(final String searchQuery) throws Exception {
//        return userServiceNew.executeNamedQuery("findAllUsers");
//
//    }
//
//    public void addExpenseItem() {     //---------------
//        boolean validated = validateExpenceLineItems();
//
//        if (validated) {
//            updateCurrencyLabel();
//            selectedExpenseModel.addExpenseItem(new ExpenseItemModel());
//        }
//
//    }
//
//    private boolean validateExpenceLineItems() { //---------------
//        boolean validated = true;
//        for (ExpenseItemModel lastItem : selectedExpenseModel.getExpenseItem()) {
//            if (lastItem.getQuatity() < 1 || lastItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
//                FacesMessage message = new FacesMessage("Please enter proper detail for all expence items.");
//                message.setSeverity(FacesMessage.SEVERITY_ERROR);
//                FacesContext.getCurrentInstance().addMessage("validationId", message);
//                validated = false;
//            }
//        }
//        return validated;
//    }
//
//    public void updateCurrencyLabel() {
//        if (null != selectedExpenseModel.getCurrency()) {
//            selectedExpenseModel.setCurrency(currencyService.getCurrency(selectedExpenseModel.getCurrency().getCurrencyCode()));
//        }
//    }
//
//    public void updateSubTotal(final ExpenseItemModel expenseItemModel) {
//        final int quantity = expenseItemModel.getQuatity();
//        final BigDecimal unitPrice = expenseItemModel.getUnitPrice();
//        final BigDecimal vatPer = expenseItemModel.getVatId();
//        if (null != unitPrice) {
//            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
//            expenseItemModel.setSubTotal(amountWithoutTax);
//            if (vatPer != null && vatPer.compareTo(BigDecimal.ZERO) >= 1) {
//                final BigDecimal amountWithTax = amountWithoutTax
//                        .add(amountWithoutTax.multiply(vatPer).multiply(new BigDecimal(0.01)));
//                expenseItemModel.setSubTotal(amountWithTax);
//            }
//        }
//        calculateTotal();
//    }
//
//    public List<TransactionType> getAllTransactionType(String str) {
//        if (str == null) {
//            return this.transactionTypes;
//        }
//        List<TransactionType> filterList = new ArrayList<>();
//        transactionTypes = transactionTypeService.findAll();
//        for (TransactionType type : transactionTypes) {
//            filterList.add(type);
//        }
//        return filterList;
//    }
//
//    private void calculateTotal() {
//        total = new BigDecimal(0);
//        List<ExpenseItemModel> expenseItem = selectedExpenseModel.getExpenseItem();
//        if (expenseItem != null) {
//            for (ExpenseItemModel expense : expenseItem) {
//                if (expense.getSubTotal() != null) {
//                    total = total.add(expense.getSubTotal());
//                }
//            }
//        }
//    }
//
//    private void populateVatCategory() {
//        vatCategoryList = vatCategoryService.getVatCategoryList();
//        if (vatCategoryList != null) {
//            for (VatCategory vatCategory : vatCategoryList) {
//                SelectItem item = new SelectItem(vatCategory.getVat(), vatCategory.getName());
//                vatCategorySelectItemList.add(item);
//            }
//        }
//    }
}
