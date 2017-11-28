package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.Purchase;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.PurchaseService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionStatusService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.bankaccount.model.BankAccountModel;
import com.simplevat.web.bankaccount.model.ReferenceObjectClass;
import com.simplevat.web.constant.InvoicePurchaseStatusConstant;
import com.simplevat.web.constant.TransactionCategoryConsatant;
import com.simplevat.web.constant.TransactionCreditDebitConstant;
import com.simplevat.web.constant.TransactionEntryTypeConstant;
import com.simplevat.web.constant.TransactionRefrenceTypeConstant;
import com.simplevat.web.constant.TransactionStatusConstant;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

@Controller
@SpringScopeView
public class TransactionListController extends TransactionControllerHelper implements Serializable {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;
    @Autowired
    private TransactionStatusService transactionStatusService;
    @Autowired
    private TransactionTypeService transactionTypeService;
    @Autowired
    private BankAccountService bankAccountService;
    private List<TransactionModel> transactions;

    @Getter
    private BankAccountModel selectedBankAccountModel;

    private BankAccount bankAccount;

    @Getter
    @Setter
    private TransactionModel transactionModel;

    @Getter
    @Setter
    private TransactionModel childTransactionModel;

    @Getter
    @Setter
    private Transaction transaction;

    @Getter
    @Setter
    private boolean renderedInvoice;

    @Getter
    @Setter
    private int totalUnExplained;

    @Getter
    @Setter
    private int totalExplained;

    @Getter
    @Setter
    private int totalPartiallyExplained;

    @Getter
    @Setter
    private int totalTransactions;

    @Getter
    @Setter
    private TransactionModel selectedTransactionModel;
    private TransactionModel prevParentTransactionModel;
    private TransactionModel prevChildTransactionModel;
    private boolean expanded = false;
    int parentTransIndex;
    private int datatableInitialRowCount = 10;
    private int datatableRowCount = datatableInitialRowCount;
    TransactionModel currentTransactionModel;

    @PostConstruct
    public void init() {
        totalUnExplained = 0;
        totalExplained = 0;
        totalPartiallyExplained = 0;
        try {
            Integer bankAccountId = FacesUtil.getSelectedBankAccountId();
            bankAccount = bankAccountService.findByPK(bankAccountId);
            selectedBankAccountModel = new BankAccountHelper().getBankAccountModel(bankAccount);
            BankAccountHelper bankAccountHelper = new BankAccountHelper();
            TransactionCriteria transactionCriteria = new TransactionCriteria();
            transactionCriteria.setActive(Boolean.TRUE);
            transactionCriteria.setBankAccount(bankAccountHelper.getBankAccount(selectedBankAccountModel));
            List<Transaction> transactionList = transactionService.getAllParentTransactions(bankAccount);
            transactions = new ArrayList<TransactionModel>();
            populateTransactionList(transactionList);
            transactionModel = new TransactionModel();
        } catch (Exception ex) {
            Logger.getLogger(TransactionListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void populateTransactionList(List<Transaction> transactionList) {
        for (Transaction transaction : transactionList) {
            TransactionModel model = this.getTransactionModel(transaction);
            if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.UNEXPLIANED) {
                totalUnExplained++;
            } else if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.PARTIALLYEXPLIANED) {
                totalPartiallyExplained++;
            } else if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.EXPLIANED) {
                totalExplained++;
            }
            totalTransactions++;
            if (model.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.UNEXPLIANED
                    || model.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.PARTIALLYEXPLIANED) {
                getSuggestionStringForUnexplainedOrPartial(model);
            } else {
                getSuggestionStringForExplianed(model);
            }
            transactions.add(model);
        }
        Collections.sort(transactions);
    }

    private void getSuggestionStringForUnexplainedOrPartial(TransactionModel model) {
        String suggestedTransactionString = "";
        BigDecimal suggestionAmount = model.getTransactionAmount();
        if (!model.getChildTransactionList().isEmpty()) {
            int i = 0;
            for (TransactionModel childModel : model.getChildTransactionList()) {
                if (i == model.getChildTransactionList().size() - 1) {
                    break;
                }
                suggestionAmount = suggestionAmount.subtract(childModel.getTransactionAmount());
                i++;
            }
        }
        List<Invoice> invoiceList = invoiceService.getInvoiceListByDueAmount();
        if (invoiceList != null && !invoiceList.isEmpty()) {
            for (Invoice invoice : invoiceList) {
                if (suggestionAmount.doubleValue() == invoice.getDueAmount().doubleValue()) {
                    model.setReferenceId(invoice.getInvoiceId());
                    TransactionCategory category = transactionCategoryService.findByPK(TransactionCategoryConsatant.TRANSACTION_CATEGORY_INVOICE_PAYMENT);
                    suggestedTransactionString = category.getTransactionType().getTransactionTypeName() + " : " + category.getTransactionCategoryName()
                            + " : Invoice Paid : " + invoice.getInvoiceReferenceNumber() + " : " + invoice.getInvoiceContact().getFirstName()
                            + " : " + invoice.getCurrency().getCurrencySymbol() + " " + invoice.getDueAmount()
                            + " : Due On : " + new SimpleDateFormat("MM/dd/yyyy").format(Date.from(invoice.getInvoiceDueDate().atZone(ZoneId.systemDefault()).toInstant()));
                    break;
                }
            }
        }
        model.setSuggestedTransactionString(suggestedTransactionString);
    }

    private void getSuggestionStringForExplianed(TransactionModel model) {
        String suggestedTransactionString = "";
        if (!model.getChildTransactionList().isEmpty()) {
            TransactionModel childModel = model.getChildTransactionList().get(0);
            suggestedTransactionString = generateSuggestedTransactionString(childModel);
        } else {
            suggestedTransactionString = generateSuggestedTransactionString(model);
        }
        model.setSuggestedTransactionString(suggestedTransactionString);
    }

    private String generateSuggestedTransactionString(TransactionModel transactionModel) {
        String[] transactionDecrpStringArr = transactionModel.getTransactionDescription().split(" ");
        String transactionDecription = transactionDecrpStringArr.length > 1 ? transactionDecrpStringArr[0] + " " + transactionDecrpStringArr[1] : transactionDecrpStringArr[0];
        if (transactionModel.getRefObject() != null) {
            if (transactionModel.getRefObject() instanceof Invoice) {
                Invoice invoice = (Invoice) transactionModel.getRefObject();
                return transactionModel.getTransactionType().getTransactionTypeName() + " : " + transactionModel.getExplainedTransactionCategory().getTransactionCategoryName()
                        + " : " + (transactionModel.getTransactionDescription() != null ? transactionDecription + " : " : "") + invoice.getInvoiceReferenceNumber() + " : " + invoice.getInvoiceContact().getFirstName()
                        + " : " + invoice.getCurrency().getCurrencySymbol() + " " + invoice.getDueAmount()
                        + " : Due On : " + new SimpleDateFormat("MM/dd/yyyy").format(Date.from(invoice.getInvoiceDueDate().atZone(ZoneId.systemDefault()).toInstant()))
                        + "......";
            } else if (transactionModel.getRefObject() instanceof Purchase) {
                Purchase purchase = (Purchase) transactionModel.getRefObject();
                return transactionModel.getTransactionType().getTransactionTypeName() + " : " + transactionModel.getExplainedTransactionCategory().getTransactionCategoryName()
                        + " : " + (transactionModel.getTransactionDescription() != null ? transactionDecription + " : " : "") + (purchase.getReceiptNumber() != null ? purchase.getReceiptNumber() + " :" : "") + " " + purchase.getPurchaseContact().getFirstName()
                        + " : " + purchase.getCurrency().getCurrencySymbol() + " " + purchase.getPurchaseDueAmount()
                        + " : Due On : " + new SimpleDateFormat("MM/dd/yyyy").format(Date.from(purchase.getPurchaseDueDate().atZone(ZoneId.systemDefault()).toInstant()))
                        + "......";
            }
        } else {
            return transactionModel.getTransactionType().getTransactionTypeName() + " : " + transactionModel.getExplainedTransactionCategory().getTransactionCategoryName()
                    + " : " + (transactionModel.getTransactionDescription() != null ? transactionDecription : "") + "......";
        }
        return "";
    }

    public void acceptSuggestion(TransactionModel transactionModel) {
        BigDecimal suggestionAmount = transactionModel.getTransactionAmount();
        if (!transactionModel.getChildTransactionList().isEmpty()) {
            int i = 0;
            for (TransactionModel childModel : transactionModel.getChildTransactionList()) {
                if (i == transactionModel.getChildTransactionList().size() - 1) {
                    break;
                }
                suggestionAmount = suggestionAmount.subtract(childModel.getTransactionAmount());
                i++;
            }
        }
        if (transactionModel.getTransactionAmount().doubleValue() > suggestionAmount.doubleValue()) {
            this.transactionModel = transactionModel.getChildTransactionList().get(transactionModel.getChildTransactionList().size() - 1);
        } else {
            this.transactionModel = transactionModel;
        }
        TransactionCategory category = transactionCategoryService.findByPK(TransactionCategoryConsatant.TRANSACTION_CATEGORY_INVOICE_PAYMENT);
        this.transactionModel.setTransactionType(category.getTransactionType());
        this.transactionModel.setExplainedTransactionCategory(category);
        this.transactionModel.setTransactionDescription("Invoice Paid");
        this.transactionModel.setRefObject(invoiceService.findByPK(transactionModel.getReferenceId()));
        this.transactionModel.setReferenceId(null);
        updateTransactionDropDown();
    }

    public void updateTransactionDropDown() {
        if (transactionModel.isParent()) {
            Transaction transaction = getTransactionEntity(transactionModel);
            saveUpdateDefineTransaction(transaction, null, getRefObject());
        } else {
            Transaction transaction = transactionModel.getParentTransaction();
            Transaction childTransaction = getTransactionEntity(transactionModel);
            saveUpdateDefineTransaction(transaction, childTransaction, getRefObject());
        }
        init();
    }

    private Object getRefObject() {
        Object refObject = null;
        if (transactionModel.getExplainedTransactionCategory().getTransactionCategoryId() == TransactionCategoryConsatant.TRANSACTION_CATEGORY_INVOICE_PAYMENT) {
            refObject = transactionModel.getRefObject();
        } else if (transactionModel.getExplainedTransactionCategory().getParentTransactionCategory().getTransactionCategoryId() == TransactionCategoryConsatant.TRANSACTION_CATEGORY_PURCHASE) {
            refObject = transactionModel.getRefObject();
        }
        return refObject;
    }

    //Service UpdateMethod
    public void saveUpdateDefineTransaction(Transaction transaction, Transaction childTransaction, Object referanceObject) {
        /**
         * Update Ref object if it was attached
         */
        if (childTransaction != null) {
            updatePrevReference(childTransaction);
        } else {
            updatePrevReference(transaction);
        }
        BigDecimal transactionAmount = transaction.getTransactionAmount();
        transaction.setLastUpdateDate(LocalDateTime.now());
        transaction.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
        transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
        BigDecimal explainedAmount = new BigDecimal(0);
        BigDecimal tempTransactionAmount = null;
        if (childTransaction != null) {
            transactionAmount = childTransaction.getTransactionAmount();
            if (childTransaction.getTransactionId() > 0) {
                for (Transaction tempChildTransaction : transaction.getChildTransactionList()) {
                    explainedAmount = explainedAmount.add(tempChildTransaction.getTransactionAmount());
                }
                tempTransactionAmount = transactionAmount.add(transaction.getTransactionAmount().subtract(explainedAmount));
                System.out.println("transactionAmount :" + transactionAmount);
                transactionAmount = tempTransactionAmount;
            }
        }
        if (referanceObject != null) {
            if (referanceObject instanceof Invoice) {
                Invoice invoice = (Invoice) referanceObject;
                invoice = invoiceService.findByPK(invoice.getInvoiceId());// refreshing the object
                referanceObject = invoice;
                updateInvoiceReference(transaction, childTransaction, transactionAmount, invoice);
            } else if (referanceObject instanceof Purchase) {
                Purchase purchase = (Purchase) referanceObject;
                purchase = purchaseService.findByPK(purchase.getPurchaseId());// refreshing the object
                referanceObject = purchase;
                updatePurchaseReference(transaction, childTransaction, transactionAmount, purchase);
            }
        } else {
            if (childTransaction == null) {
                transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
            } else {
                transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                childTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                childTransaction.setCurrentBalance(new BigDecimal(0.00));
                childTransaction.setTransactionDate(LocalDateTime.now());
                if (childTransaction.getTransactionId() == null || childTransaction.getTransactionId() == 0) {
                    childTransaction.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
                    childTransaction.setCreatedDate(LocalDateTime.now());
                } else {
                    childTransaction.setTransactionAmount(transactionAmount);
                    childTransaction.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                    childTransaction.setLastUpdateDate(LocalDateTime.now());
                }
            }
        }

        if (childTransaction != null) {
            transaction.setVersionNumber(childTransaction.getParentTransaction().getVersionNumber());
            transaction.setChildTransactionList(null);
            if (childTransaction.getTransactionAmount().doubleValue() == transaction.getTransactionAmount().doubleValue()) {
                transaction.setReferenceId(childTransaction.getReferenceId());
                transaction.setReferenceType(childTransaction.getReferenceType());
                transaction.setTransactionType(childTransaction.getTransactionType());
                transaction.setExplainedTransactionCategory(childTransaction.getExplainedTransactionCategory());
                transaction.setTransactionDescription(childTransaction.getTransactionDescription());
                transactionService.deleteChildTransaction(childTransaction);
            } else {
                childTransaction.setBankAccount(transaction.getBankAccount());
                transactionService.persistChildTransaction(childTransaction);
            }
        }
        transactionService.update(transaction);
        if (referanceObject != null) {
            if (referanceObject instanceof Invoice) {
                invoiceService.update((Invoice) referanceObject);
            } else if (referanceObject instanceof Purchase) {
                purchaseService.update((Purchase) referanceObject);
            }
        }
    }

    private void updateInvoiceReference(Transaction parentTransaction, Transaction childTransaction, BigDecimal transactionAmount, Invoice invoice) {
        BigDecimal invoiceDueAmount = invoice.getDueAmount();
        invoice.setLastUpdateDate(LocalDateTime.now());
        invoice.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
        ReferenceObjectClass referenceObjectClass = new ReferenceObjectClass(invoice.getInvoiceId(), invoice.getDueAmount(), TransactionRefrenceTypeConstant.INVOICE, invoice.getStatus());
        updateTransactionByRefrenceObject(parentTransaction, childTransaction, transactionAmount, invoiceDueAmount, referenceObjectClass);
        invoice.setDueAmount(referenceObjectClass.getDueAmount());
        invoice.setStatus(referenceObjectClass.getStatus());
    }

    private void updatePurchaseReference(Transaction parentTransaction, Transaction childTransaction, BigDecimal transactionAmount, Purchase purchase) {
        BigDecimal purchaseDueAmount = purchase.getPurchaseDueAmount();
        purchase.setLastUpdateDate(LocalDateTime.now());
        purchase.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
        ReferenceObjectClass referenceObjectClass = new ReferenceObjectClass(purchase.getPurchaseId(), purchase.getPurchaseDueAmount(), TransactionRefrenceTypeConstant.PURCHASE, purchase.getStatus());
        updateTransactionByRefrenceObject(parentTransaction, childTransaction, transactionAmount, purchaseDueAmount, referenceObjectClass);
        purchase.setPurchaseDueAmount(referenceObjectClass.getDueAmount());
        purchase.setStatus(referenceObjectClass.getStatus());
    }

    private void updateTransactionByRefrenceObject(Transaction parentTransaction, Transaction childTransaction, BigDecimal transactionAmount, BigDecimal prevDueAmount, ReferenceObjectClass referenceObject) {
        if (transactionAmount.doubleValue() <= prevDueAmount.doubleValue()) {
            referenceObject.setDueAmount(prevDueAmount.subtract(transactionAmount));
            referenceObject.setStatus(InvoicePurchaseStatusConstant.PAID);
            if (prevDueAmount.doubleValue() > transactionAmount.doubleValue()) {
                referenceObject.setStatus(InvoicePurchaseStatusConstant.PARTIALPAID);
            }
            if (childTransaction != null) {
                if (childTransaction.getTransactionId() == 0) {
                    childTransaction = createNewChildTransaction(childTransaction, parentTransaction.getDebitCreditFlag(), transactionAmount);
                } else {
                    childTransaction.setTransactionAmount(transactionAmount);
                    childTransaction.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                    childTransaction.setLastUpdateDate(LocalDateTime.now());
                }
                childTransaction.setReferenceId(referenceObject.getId());
                childTransaction.setReferenceType(referenceObject.getReferenceType());
                childTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
            } else {
                parentTransaction.setReferenceId(referenceObject.getId());
                parentTransaction.setReferenceType(referenceObject.getReferenceType());
            }
        } else {
            parentTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.PARTIALLYEXPLIANED));
            Transaction newChildTransaction = null;
            if (childTransaction == null) {
                newChildTransaction = createNewChildTransaction(parentTransaction, parentTransaction.getDebitCreditFlag(), referenceObject.getDueAmount());
            } else if (childTransaction.getTransactionId() == 0) {
                newChildTransaction = createNewChildTransaction(childTransaction, parentTransaction.getDebitCreditFlag(), referenceObject.getDueAmount());
            } else {
                childTransaction.setReferenceId(referenceObject.getId());
                childTransaction.setReferenceType(referenceObject.getReferenceType());
                childTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                childTransaction.setTransactionAmount(referenceObject.getDueAmount());
            }
            if (newChildTransaction != null) {
                newChildTransaction.setReferenceId(referenceObject.getId());
                newChildTransaction.setReferenceType(referenceObject.getReferenceType());
                newChildTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                parentTransaction.getChildTransactionList().add(newChildTransaction);
            }
            parentTransaction.setExplainedTransactionCategory(null);
            parentTransaction.setTransactionType(null);
            referenceObject.setDueAmount(new BigDecimal(0));
            referenceObject.setStatus(InvoicePurchaseStatusConstant.PAID);
        }
    }

    private void updatePrevReference(Transaction transaction) {
        Integer prevReferanceId = transaction.getReferenceId();
        if (prevReferanceId != null) {
            if (transaction.getReferenceType() == TransactionRefrenceTypeConstant.INVOICE) {
                Invoice invoice = invoiceService.findByPK(transaction.getReferenceId());
                invoice.setDueAmount(invoice.getDueAmount().add(transaction.getTransactionAmount()));
                invoiceService.update(invoice);
            } else if (transaction.getReferenceType() == TransactionRefrenceTypeConstant.PURCHASE) {
                Purchase purchase = purchaseService.findByPK(transaction.getReferenceId());
                purchase.setPurchaseDueAmount(purchase.getPurchaseDueAmount().add(transaction.getTransactionAmount()));
                purchaseService.update(purchase);
            }
            transaction.setReferenceId(null);
            transaction.setReferenceType(null);
        }
    }

    private Transaction createNewChildTransaction(Transaction transaction, char transactionCreditDebitConstant, BigDecimal transactionAmount) {
        Transaction childTransaction = new Transaction();
        childTransaction.setDebitCreditFlag(transactionCreditDebitConstant);
        childTransaction.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
        childTransaction.setCurrentBalance(new BigDecimal(0.00));
        childTransaction.setCreatedDate(LocalDateTime.now());
        if (transaction.getParentTransaction() == null) {
            childTransaction.setParentTransaction(transaction);
        } else {
            childTransaction.setParentTransaction(transaction.getParentTransaction());
        }
        childTransaction.setTransactionDate(childTransaction.getTransactionDate());
        childTransaction.setBankAccount(transaction.getBankAccount());
        childTransaction.setEntryType(TransactionEntryTypeConstant.SYSTEM);
        childTransaction.setTransactionType(transaction.getTransactionType());
        childTransaction.setExplainedTransactionCategory(transaction.getExplainedTransactionCategory());
        childTransaction.setTransactionDescription(transaction.getTransactionDescription());
        childTransaction.setTransactionAmount(transactionAmount);
        return childTransaction;
    }

    public List<Invoice> completeInvoice() {
        return invoiceService.getInvoiceListByDueAmount();
    }

    public List<Purchase> completePurchase() {
        List<Purchase> purchaseList = purchaseService.getPurchaseListByDueAmount();
        return purchaseList;
    }

    public List<TransactionCategory> transactionCategories(TransactionType transactionType) throws Exception {
        List<TransactionCategory> transactionCategoryParentList = new ArrayList<>();
        List<TransactionCategory> transactionCategoryList = new ArrayList<>();
        if (transactionType != null) {
            transactionCategoryList = transactionCategoryService.findAllTransactionCategoryByTransactionType(transactionType.getTransactionTypeCode());
        }
        for (TransactionCategory transactionCategory : transactionCategoryList) {
            if (transactionCategory.getParentTransactionCategory() != null) {
                transactionCategoryParentList.add(transactionCategory.getParentTransactionCategory());
            }
        }
        transactionCategoryList.removeAll(transactionCategoryParentList);
        return transactionCategoryList;
    }

    public List<TransactionStatus> transactionStatuses(final String searchQuery) throws Exception {
        return transactionStatusService.executeNamedQuery("findAllTransactionStatues");
    }

    public List<TransactionType> transactionTypes(char debitCreditFlag) throws Exception {
        if (debitCreditFlag == TransactionCreditDebitConstant.CREDIT) {
            return transactionTypeService.executeNamedQuery("findMoneyInTransactionType");
        } else if (debitCreditFlag == TransactionCreditDebitConstant.DEBIT) {
            return transactionTypeService.executeNamedQuery("findMoneyOutTransactionType");
        }
        return new ArrayList<>();
    }

    public void expandCollapseOnRowSelect(String clientId) throws Exception {
        expandCollapse(selectedTransactionModel, clientId);
    }

    public void refreshTableDataonPageChange() {
        datatableRowCount = datatableInitialRowCount;
        System.out.println("datatableRowCount ===================" + datatableRowCount);
        if (prevParentTransactionModel != null && prevParentTransactionModel.isParent()) {
            if (prevParentTransactionModel.getChildTransactionList() != null && !prevParentTransactionModel.getChildTransactionList().isEmpty()) {
                for (TransactionModel model : prevParentTransactionModel.getChildTransactionList()) {
                    transactions.remove(model);
                }
            }
            expanded = false;
        }
    }

    public void expandCollapse(TransactionModel currentTransactionModel, String clientId) throws Exception {
        System.out.println("transactions size :" + transactions.size());
        this.currentTransactionModel = currentTransactionModel;
        currentTransactionModel.setExpandIcon(!currentTransactionModel.isExpandIcon());
        if (!expanded) {
            expandTransaction(currentTransactionModel, clientId);
            if (currentTransactionModel.isParent()) {
                prevParentTransactionModel = currentTransactionModel;
            } else {
                prevChildTransactionModel = currentTransactionModel;
            }
        } else {
            System.out.println("currentTransactionModel :" + currentTransactionModel);
            if (Objects.equals(currentTransactionModel.getTransactionId(), prevParentTransactionModel.getTransactionId())
                    || (prevChildTransactionModel != null && Objects.equals(currentTransactionModel.getTransactionId(), prevChildTransactionModel.getTransactionId()))) {
                collapseTransaction(currentTransactionModel, clientId);
                if (currentTransactionModel.isParent()) {
                    prevParentTransactionModel = null;
                    prevChildTransactionModel = null;
                } else {
                    prevChildTransactionModel = null;
                }
            } else {
                if (!currentTransactionModel.isParent()) {
                    if (prevChildTransactionModel == null) {
                        expandTransaction(currentTransactionModel, clientId);
                    } else {
                        if (Objects.equals(currentTransactionModel.getTransactionId(), prevChildTransactionModel.getTransactionId())) {
                            collapseTransaction(currentTransactionModel, clientId);
                        } else {
                            collapseTransaction(prevChildTransactionModel, clientId);
                            expandTransaction(currentTransactionModel, clientId);
                        }
                    }
                    prevChildTransactionModel = currentTransactionModel;
                } else {
                    collapseTransaction(prevParentTransactionModel, clientId);
                    expandTransaction(currentTransactionModel, clientId);
                    prevChildTransactionModel = null;
                    prevParentTransactionModel = currentTransactionModel;
                }
            }
        }
        displayTreeIcon();
    }

    public void expandTransaction(TransactionModel transactionModel, String clientId) throws Exception {
        if (transactionModel.isParent()
                && transactionModel.getChildTransactionList() != null
                && !transactionModel.getChildTransactionList().isEmpty()) {
            parentTransIndex = transactions.indexOf(transactionModel);
            if (transactionModel.getChildTransactionList() != null && !transactionModel.getChildTransactionList().isEmpty()) {
                datatableRowCount = datatableInitialRowCount;
                for (TransactionModel childTransaction : transactionModel.getChildTransactionList()) {
                    if (childTransaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.UNEXPLIANED
                            || childTransaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.PARTIALLYEXPLIANED) {
                        getSuggestionStringForUnexplainedOrPartial(childTransaction);
                    } else {
                        getSuggestionStringForExplianed(childTransaction);
                    }
                    transactions.add(++parentTransIndex, childTransaction);
                    ++datatableRowCount;
                }
            }
        } else {
//            if (!transactionModel.isParent() && transactionModel.getParentTransaction().getChildTransactionList() != null) {
//                for (TransactionModel transModel : transactionModel.getParentTransaction().getChildTransactionList()) {
//                    transModel.setExpandIcon(false);
//                }
//                transactionModel.setExpandIcon(true);
//            }
            expandToggler(clientId);
        }
        expanded = true;
    }

    private void displayTreeIcon() {
        if (expanded) {
            TransactionModel parentTransactionModel = currentTransactionModel;
            if (!parentTransactionModel.isParent()) {
                parentTransactionModel = prevParentTransactionModel;
            }
            int startIndex = transactions.indexOf(parentTransactionModel);
            int childTransCount = parentTransactionModel.getChildTransactionList().size();
            if (childTransCount > 0) {
                RequestContext.getCurrentInstance().execute(" $('.ui-datatable-data').find('tr').eq(" + startIndex + ").addClass('first-child');");
                RequestContext.getCurrentInstance().execute(" $('.ui-datatable-data').find('tr').eq(" + startIndex + ").find('td').eq(0).addClass('first-child');");
                for (int i = 0; i < childTransCount; i++) {
                    RequestContext.getCurrentInstance().execute(" $('.ui-datatable-data').find('tr').eq(" + ++startIndex + ").find('td').eq(0).addClass('first-child');");
                }
                RequestContext.getCurrentInstance().execute(" $('.ui-datatable-data').find('tr').eq(" + startIndex + ").addClass('last-child');");
            }
        }
    }

    public void collapseTransaction(TransactionModel parentTransactionModelId, String clientId) throws Exception {
        //Collapse
        if (parentTransactionModelId.isParent()) {
            parentTransactionModelId.setExpandIcon(false);
            if (parentTransactionModelId.getChildTransactionList() != null && !parentTransactionModelId.getChildTransactionList().isEmpty()) {
                for (TransactionModel model : parentTransactionModelId.getChildTransactionList()) {
                    transactions.remove(model);
                }
                datatableRowCount = datatableInitialRowCount;
            }
            expanded = false;
        } else {
//            expandToggler(clientId);
        }
    }

    private void expandToggler(String btnComponentId) {
        int itemIndex = transactions.indexOf(currentTransactionModel);
        itemIndex = itemIndex % datatableInitialRowCount;
        String tempBtnComponentId = btnComponentId.substring(0, btnComponentId.indexOf("transactionsTable:") + 18) + itemIndex + ":collapseExpandBtn";
        btnComponentId = tempBtnComponentId.replace(":", "\\\\:");
        RequestContext.getCurrentInstance().execute(" $('#" + btnComponentId + "').parent().find('.ui-row-toggler').trigger('click');");
    }

    public String redirectToReference() {
        if (childTransactionModel.getReferenceType() == TransactionRefrenceTypeConstant.INVOICE) {
            return "/pages/secure/invoice/invoice.xhtml?faces-redirect=true&selectedInvoiceModelId=" + childTransactionModel.getReferenceId();
        }
        return null;
    }

    public void allUnExplainedTransactions() {
        transactions.clear();
        for (Transaction transaction : transactionService.getAllParentTransactions(bankAccount)) {
            TransactionModel model = this.getTransactionModel(transaction);
            if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.UNEXPLIANED) {
                transactions.add(model);
            }
        }
    }

    public void allExplainedTransactions() {
        transactions.clear();
        for (Transaction transaction : transactionService.getAllParentTransactions(bankAccount)) {
            TransactionModel model = this.getTransactionModel(transaction);
            if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.EXPLIANED) {
                transactions.add(model);
            }
        }
    }

    public void allPartiallyExplainedTransactions() {
        transactions.clear();
        for (Transaction transaction : transactionService.getAllParentTransactions(bankAccount)) {
            TransactionModel model = this.getTransactionModel(transaction);
            if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.PARTIALLYEXPLIANED) {
                transactions.add(model);
            }
        }
    }

    public List<TransactionModel> getTransactions() throws Exception {
        return transactions;
    }

    public void setTransactions(List<TransactionModel> transactions) {
        this.transactions = transactions;
    }

    public int getDatatableRowCount() {
        return datatableRowCount;
    }

    public void allTransactions() {

        for (Transaction transaction : transactionService.getAllParentTransactions(bankAccount)) {
            TransactionModel model = this.getTransactionModel(transaction);
            if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.UNEXPLIANED) {
                totalUnExplained++;
            } else if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.PARTIALLYEXPLIANED) {
                totalPartiallyExplained++;
            } else if (transaction.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.EXPLIANED) {
                totalExplained++;
            }
            totalTransactions++;
            transactions.add(model);
        }
    }
}
