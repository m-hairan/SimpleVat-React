package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionStatusService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.bankaccount.model.BankAccountModel;
import com.simplevat.web.constant.InvoiceStatusConstant;
import com.simplevat.web.constant.TransactionCategoryConsatant;
import com.simplevat.web.constant.TransactionCreditDebitConstant;
import com.simplevat.web.constant.TransactionEntryTypeConstant;
import com.simplevat.web.constant.TransactionRefrenceTypeConstant;
import com.simplevat.web.constant.TransactionStatusConstant;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    public Integer referenceType = null;
    BigDecimal transactionAmount = null;
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
            transactions.add(model);
        }
        Collections.sort(transactions);
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
        if (transactionModel.getExplainedTransactionCategory().getTransactionCategoryCode() == TransactionRefrenceTypeConstant.INVOICE) {
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
                BigDecimal invoiceDueAmount = invoice.getDueAmount();
                invoice.setLastUpdateDate(LocalDateTime.now());
                invoice.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                if (transactionAmount.doubleValue() <= invoiceDueAmount.doubleValue()) {
                    invoice.setDueAmount(invoiceDueAmount.subtract(transactionAmount));
                    invoice.setStatus(InvoiceStatusConstant.PAID);
                    if (invoiceDueAmount.doubleValue() > transactionAmount.doubleValue()) {
                        invoice.setStatus(InvoiceStatusConstant.PARTIALPAID);
                    }
                    if (childTransaction != null) {
//                        if (tempTransactionAmount != null && childTransaction.getTransactionId() != 0) {
//                            transactionAmount = transactionAmount.add(tempTransactionAmount);
//                        }
                        if (childTransaction.getTransactionId() == 0) {
                            childTransaction = createNewChildTransaction(childTransaction, TransactionCreditDebitConstant.CREDIT, transactionAmount);
                        } else {
                            childTransaction.setTransactionAmount(transactionAmount);
                            childTransaction.setLastUpdateBy(FacesUtil.getLoggedInUser().getUserId());
                            childTransaction.setLastUpdateDate(LocalDateTime.now());
                        }
                        childTransaction.setReferenceId(invoice.getInvoiceId());
                        childTransaction.setReferenceType(TransactionRefrenceTypeConstant.INVOICE);
                        childTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                    } else {
                        transaction.setReferenceId(invoice.getInvoiceId());
                        transaction.setReferenceType(TransactionRefrenceTypeConstant.INVOICE);
                    }
                } else {
                    transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.PARTIALLYEXPLIANED));
                    Transaction newChildTransaction = null;
                    if (childTransaction == null) {
                        newChildTransaction = createNewChildTransaction(transaction, TransactionCreditDebitConstant.CREDIT, invoice.getDueAmount());
                    } else if (childTransaction.getTransactionId() == 0) {
                        newChildTransaction = createNewChildTransaction(childTransaction, TransactionCreditDebitConstant.CREDIT, invoice.getDueAmount());
                    } else {
                        childTransaction.setReferenceId(invoice.getInvoiceId());
                        childTransaction.setReferenceType(TransactionRefrenceTypeConstant.INVOICE);
                        childTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                        childTransaction.setTransactionAmount(invoice.getDueAmount());
                    }
                    if (newChildTransaction != null) {
                        newChildTransaction.setReferenceId(invoice.getInvoiceId());
                        newChildTransaction.setReferenceType(TransactionRefrenceTypeConstant.INVOICE);
                        newChildTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                        transaction.getChildTransactionList().add(newChildTransaction);
                    }
                    invoice.setDueAmount(new BigDecimal(0));
                    invoice.setStatus(InvoiceStatusConstant.PAID);
                }
            }
        } else {
            if (childTransaction == null) {
                transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
            } else {
                transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                childTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
                childTransaction.setCurrentBalance(new BigDecimal(0.00));
                childTransaction.setTransactionDate(LocalDateTime.now());
//                if (tempTransactionAmount != null && childTransaction.getTransactionId() != 0) {
//                    transactionAmount = transactionAmount.add(tempTransactionAmount);
//                }
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
            }
        }
    }

    private void updatePrevReference(Transaction transaction) {
        Integer prevReferanceId = transaction.getReferenceId();
        if (prevReferanceId != null) {
            if (transaction.getReferenceType() == TransactionRefrenceTypeConstant.INVOICE) {
                Invoice invoice = invoiceService.findByPK(transaction.getReferenceId());
                invoice.setDueAmount(invoice.getDueAmount().add(transaction.getTransactionAmount()));
                invoiceService.update(invoice);
            }
            transaction.setReferenceId(null);
            transaction.setReferenceType(null);
        }
    }

    private Transaction createNewChildTransaction(Transaction transaction, char transactionCreditDebitConstant, BigDecimal transactionAmount) {
        Transaction childTransaction = new Transaction();
        childTransaction.setDebitCreditFlag(transactionCreditDebitConstant);
        childTransaction.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
        childTransaction.setTransactionDate(LocalDateTime.now());
        childTransaction.setCurrentBalance(new BigDecimal(0.00));
        childTransaction.setCreatedDate(LocalDateTime.now());
        if (transaction.getParentTransaction() == null) {
            childTransaction.setParentTransaction(transaction);
        } else {
            childTransaction.setParentTransaction(transaction.getParentTransaction());
        }
        childTransaction.setBankAccount(transaction.getBankAccount());
        childTransaction.setEntryType(TransactionEntryTypeConstant.SYSTEM);
        childTransaction.setTransactionType(transaction.getTransactionType());
        childTransaction.setExplainedTransactionCategory(transaction.getExplainedTransactionCategory());
        childTransaction.setTransactionDescription(transaction.getTransactionDescription());
        childTransaction.setTransactionAmount(transactionAmount);
        return childTransaction;
    }

    public void transactionCategoryListner(TransactionCategory category) {
        if (category.getTransactionCategoryName().equals(TransactionCategoryConsatant.INVOICEPAYMENT)) {
            renderedInvoice = true;
        }
    }

    public void referenceListner(Object refObject) {
        if (refObject instanceof Invoice) {
            referenceType = TransactionRefrenceTypeConstant.INVOICE;
        }
    }

    public List<Invoice> completeInvoice() {
        return invoiceService.getInvoiceListByDueAmount();
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
