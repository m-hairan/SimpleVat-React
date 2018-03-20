package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.criteria.ProjectCriteria;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.entity.Project;
import com.simplevat.entity.Purchase;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.entity.bankaccount.TransactionView;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.ProjectService;
import com.simplevat.service.PurchaseService;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionStatusService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.bankaccount.model.BankAccountModel;
import com.simplevat.web.constant.InvoicePurchaseStatusConstant;
import com.simplevat.web.constant.TransactionCategoryConsatant;
import com.simplevat.web.constant.TransactionCreditDebitConstant;
import com.simplevat.web.constant.TransactionEntryTypeConstant;
import com.simplevat.web.constant.TransactionRefrenceTypeConstant;
import com.simplevat.web.constant.TransactionStatusConstant;
import com.simplevat.web.utils.FacesUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

@Controller
@SpringScopeView
public class TransactionController extends TransactionControllerHelper implements Serializable {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TransactionStatusService transactionStatusService;

    @Value("${file.upload.location}")
    private String fileLocation;

    @Getter
    @Setter
    private TransactionModel selectedTransactionModel;

    @Getter
    @Setter
    private BankAccount selectedBankAccount;

    @Getter
    @Setter
    private BankAccountModel selectedBankAccountModel;

    @Getter
    @Setter
    private List<TransactionView> transactionViewList;

    @Getter
    @Setter
    String fileName;

    @Getter
    @Setter
    List<TransactionType> transactionTypeList;

    @PostConstruct
    public void init() {

        Integer bankAccountId = FacesUtil.getSelectedBankAccountId();
        if (bankAccountId != null) {
            BankAccount bankAccount = bankAccountService.findByPK(bankAccountId);
            selectedBankAccountModel = new BankAccountHelper().getBankAccountModel(bankAccount);
        }
        Object objselectedTransactionModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedTransactionId");

        if (objselectedTransactionModel != null) {
            transactionViewList = transactionService.getChildTransactionViewListByParentId(Integer.parseInt(objselectedTransactionModel.toString()));
            selectedTransactionModel = getTransactionModel(transactionService.findByPK(Integer.parseInt(objselectedTransactionModel.toString())));
            if (selectedTransactionModel.getExplainedTransactionAttachement() != null) {
                InputStream stream = new ByteArrayInputStream(selectedTransactionModel.getExplainedTransactionAttachement());
                selectedTransactionModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
            }
        } else if (selectedTransactionModel == null) {
            selectedTransactionModel = new TransactionModel();
            selectedBankAccount = getBankAccount(selectedBankAccountModel);
            selectedTransactionModel.setBankAccount(selectedBankAccount);
        }

    }

    public String createTransaction() {
        return "/pages/secure/bankaccount/edit-bank-transaction.xhtml?faces-redirect=true";
    }

    public String importTransaction() {
        // RequestContext.getCurrentInstance().execute("PF('importTransactionWidget').show(); PF('importTransactionWidget').content.scrollTop('0')");
        return "/pages/secure/bankaccount/import-transaction.xhtml?faces-redirect=true";
    }

    public void exportTransaction() {
        RequestContext.getCurrentInstance().execute("PF('exportTransactionWidget').show();");
    }

    public String editTransection() {
        System.out.println("selectedM :" + selectedTransactionModel);
        //FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("selectedTransactionId", String.valueOf(selectedTransactionModel.getTransactionId()));
        return "edit-bank-transaction?faces-redirect=true&selectedTransactionId=" + selectedTransactionModel.getTransactionId();
    }

    private Transaction createNewChildTransaction(Transaction transaction, char transactionCreditDebitConstant, BigDecimal transactionAmount) {
        Transaction childTransaction = new Transaction();
        childTransaction.setDebitCreditFlag(transactionCreditDebitConstant);
        childTransaction.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
        childTransaction.setTransactionDate(LocalDateTime.now());
        childTransaction.setCurrentBalance(new BigDecimal(0.00));
        childTransaction.setCreatedDate(LocalDateTime.now());
        childTransaction.setParentTransaction(transaction);
        childTransaction.setBankAccount(transaction.getBankAccount());
        childTransaction.setEntryType(TransactionEntryTypeConstant.SYSTEM);
        childTransaction.setTransactionType(transaction.getTransactionType());
        childTransaction.setExplainedTransactionCategory(transaction.getExplainedTransactionCategory());
        childTransaction.setTransactionDescription(transaction.getTransactionDescription());
        childTransaction.setTransactionAmount(transactionAmount);
        return childTransaction;
    }

    private Object getRefObject() {
        Object refObject = null;
        if (selectedTransactionModel.getExplainedTransactionCategory() != null
                && selectedTransactionModel.getExplainedTransactionCategory().getTransactionCategoryId() == TransactionCategoryConsatant.TRANSACTION_CATEGORY_INVOICE_PAYMENT) {
            refObject = selectedTransactionModel.getRefObject();
        } else if (selectedTransactionModel.getExplainedTransactionCategory() != null && selectedTransactionModel.getExplainedTransactionCategory().getParentTransactionCategory() != null
                && selectedTransactionModel.getExplainedTransactionCategory().getParentTransactionCategory().getTransactionCategoryId() == TransactionCategoryConsatant.TRANSACTION_CATEGORY_PURCHASE) {
            refObject = selectedTransactionModel.getRefObject();
        }
        return refObject;
    }

    private void updatePrevReference(Transaction transaction) {
        Integer prevReferanceId = transaction.getReferenceId();
        if (prevReferanceId != null) {
            if (transaction.getReferenceType() == TransactionRefrenceTypeConstant.INVOICE) {
                Invoice invoice = invoiceService.findByPK(transaction.getReferenceId());
                invoice.setDueAmount(invoice.getDueAmount().add(transaction.getTransactionAmount()));
                invoice.setFreeze(true);
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

    private void updateRefObjectAmount(Transaction transaction, Object object) {
        updatePrevReference(transaction);
        if (object instanceof Invoice) {
            Invoice invoice = (Invoice) object;
            updateInvoice(transaction, invoice);
        } else if (object instanceof Purchase) {
            Purchase purchase = (Purchase) object;
            updatePurchase(transaction, purchase);
        }
    }

    private void updateInvoice(Transaction transaction, Invoice invoice) {
        BigDecimal invoiceDueAmount = invoice.getDueAmount();
        invoice.setStatus(InvoicePurchaseStatusConstant.PAID);
        if (transaction.getTransactionAmount().doubleValue() <= invoiceDueAmount.doubleValue()) {
            invoice.setDueAmount(invoiceDueAmount.subtract(transaction.getTransactionAmount()));
            if (invoiceDueAmount.doubleValue() > transaction.getTransactionAmount().doubleValue()) {
                invoice.setStatus(InvoicePurchaseStatusConstant.PARTIALPAID);
            }
            transaction.setReferenceId(invoice.getInvoiceId());
            transaction.setReferenceType(TransactionRefrenceTypeConstant.INVOICE);
            transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
        } else {
            transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.PARTIALLYEXPLIANED));
            Transaction newChildTransaction = createNewChildTransaction(transaction, TransactionCreditDebitConstant.CREDIT, invoice.getDueAmount());
            newChildTransaction.setReferenceId(invoice.getInvoiceId());
            newChildTransaction.setReferenceType(TransactionRefrenceTypeConstant.INVOICE);
            newChildTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
            transaction.getChildTransactionList().add(newChildTransaction);
            invoice.setDueAmount(new BigDecimal(0));
        }
    }

    private void updatePurchase(Transaction transaction, Purchase purchase) {
        BigDecimal invoiceDueAmount = purchase.getPurchaseDueAmount();
        purchase.setStatus(InvoicePurchaseStatusConstant.PAID);
        if (transaction.getTransactionAmount().doubleValue() <= invoiceDueAmount.doubleValue()) {
            purchase.setPurchaseDueAmount(invoiceDueAmount.subtract(transaction.getTransactionAmount()));
            if (invoiceDueAmount.doubleValue() > transaction.getTransactionAmount().doubleValue()) {
                purchase.setStatus(InvoicePurchaseStatusConstant.PARTIALPAID);
            }
            transaction.setReferenceId(purchase.getPurchaseId());
            transaction.setReferenceType(TransactionRefrenceTypeConstant.PURCHASE);
            transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
        } else {
            transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.PARTIALLYEXPLIANED));
            Transaction newChildTransaction = createNewChildTransaction(transaction, TransactionCreditDebitConstant.DEBIT, purchase.getPurchaseDueAmount());
            newChildTransaction.setReferenceId(purchase.getPurchaseId());
            newChildTransaction.setReferenceType(TransactionRefrenceTypeConstant.PURCHASE);
            newChildTransaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
            transaction.getChildTransactionList().add(newChildTransaction);
            purchase.setPurchaseDueAmount(new BigDecimal(0));
        }
    }

    private boolean validTransactionAmount(Transaction transaction) {
        BigDecimal validTransactionAmount = new BigDecimal(0);
        if (transaction.getChildTransactionList() != null && !transaction.getChildTransactionList().isEmpty()) {
            for (Transaction childTransaction : transaction.getChildTransactionList()) {
                validTransactionAmount = validTransactionAmount.add(childTransaction.getTransactionAmount());
            }
        }
        if (validTransactionAmount.doubleValue() > transaction.getTransactionAmount().doubleValue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction amount can't be less than " + validTransactionAmount.doubleValue()));
            return false;
        }
        return true;
    }

    public String saveTransaction() {
        User loggedInUser = FacesUtil.getLoggedInUser();
        Transaction transaction = getTransactionEntity(selectedTransactionModel);
        if (transaction.getTransactionId() != null) {
            if (!validTransactionAmount(transaction)) {
                return "";
            }
        }
        selectedBankAccount = getBankAccount(selectedBankAccountModel);
        transaction.setLastUpdateBy(loggedInUser.getUserId());
        if (transaction.getTransactionId() == null) {
            transaction.setCreatedBy(loggedInUser.getUserId());
        }
        transaction.setBankAccount(selectedBankAccount);
        transaction.setEntryType(TransactionEntryTypeConstant.MANUALLY);
        transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
        if (selectedTransactionModel.getTransactionType() != null) {
            transaction.setDebitCreditFlag(selectedTransactionModel.getTransactionType().getDebitCreditFlag());
        }
        if (selectedTransactionModel.getProject() != null) {
            Project project = projectService.findByPK(selectedTransactionModel.getProject().getProjectId());
            transaction.setProject(project);
        }
        if (transaction.getChildTransactionList() != null && !transaction.getChildTransactionList().isEmpty()) {
            BigDecimal totalAmount = new BigDecimal(0);
            for (Transaction tempTransaction : transaction.getChildTransactionList()) {
                System.out.println("tempTransaction createdBy :" + tempTransaction.getCreatedBy());
                totalAmount = totalAmount.add(tempTransaction.getTransactionAmount());
            }
            if (selectedTransactionModel.getTransactionAmount().doubleValue() > totalAmount.doubleValue()) {
                transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.PARTIALLYEXPLIANED));
            }
        }

        if (getRefObject() != null) {
            updateRefObjectAmount(transaction, getRefObject());
            if (transaction.getChildTransactionList() != null && !transaction.getChildTransactionList().isEmpty()) {
                transaction.setExplainedTransactionCategory(null);
                transaction.setTransactionType(null);
            }
        }
        System.out.println("transaction createdBy :" + transaction.getCreatedBy());
        if (transaction.getTransactionId() == null) {
            transactionService.persist(transaction);
        } else {
            transactionService.update(transaction);
        }
        if (getRefObject() != null) {
            if (getRefObject() instanceof Invoice) {
                invoiceService.update((Invoice) getRefObject());
            } else if (getRefObject() instanceof Purchase) {
                purchaseService.update((Purchase) getRefObject());
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("", "Transaction saved successfully"));
        return "bank-transactions?faces-redirect=true";

    }

    public String saveAndContinueTransaction() {

        User loggedInUser = FacesUtil.getLoggedInUser();
        Transaction transaction = getTransactionEntity(selectedTransactionModel);
        if (transaction.getTransactionId() != null) {
            if (!validTransactionAmount(transaction)) {
                return "";
            }
        }
        selectedBankAccount = getBankAccount(selectedBankAccountModel);
        transaction.setLastUpdateBy(loggedInUser.getUserId());
        if (transaction.getTransactionId() == null) {
            transaction.setCreatedBy(loggedInUser.getUserId());
        }
        transaction.setBankAccount(selectedBankAccount);
        transaction.setEntryType(TransactionEntryTypeConstant.MANUALLY);
        transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED));
        if (selectedTransactionModel.getTransactionType() != null) {
            transaction.setDebitCreditFlag(selectedTransactionModel.getTransactionType().getDebitCreditFlag());
        }
        if (selectedTransactionModel.getProject() != null) {
            Project project = projectService.findByPK(selectedTransactionModel.getProject().getProjectId());
            transaction.setProject(project);
        }
        if (transaction.getChildTransactionList() != null && !transaction.getChildTransactionList().isEmpty()) {
            BigDecimal totalAmount = new BigDecimal(0);
            for (Transaction tempTransaction : transaction.getChildTransactionList()) {
                System.out.println("tempTransaction createdBy :" + tempTransaction.getCreatedBy());
                totalAmount = totalAmount.add(tempTransaction.getTransactionAmount());
            }
            if (selectedTransactionModel.getTransactionAmount().doubleValue() > totalAmount.doubleValue()) {
                transaction.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.PARTIALLYEXPLIANED));
            }
        }

        if (getRefObject() != null) {
            updateRefObjectAmount(transaction, getRefObject());
            if (transaction.getChildTransactionList() != null && !transaction.getChildTransactionList().isEmpty()) {
                transaction.setExplainedTransactionCategory(null);
                transaction.setTransactionType(null);
            }
        }
        System.out.println("transaction createdBy :" + transaction.getCreatedBy());
        if (transaction.getTransactionId() == null) {
            transactionService.persist(transaction);
        } else {
            transactionService.update(transaction);
        }
        if (getRefObject() != null) {
            if (getRefObject() instanceof Invoice) {
                invoiceService.update((Invoice) getRefObject());
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("", "Transaction saved successfully"));
        return "/pages/secure/bankaccount/edit-bank-transaction.xhtml?faces-redirect=true";

    }

    public void fileUploadListener(FileUploadEvent e) {
        fileName = e.getFile().getFileName();
        selectedTransactionModel.setExplainedTransactionAttachement(e.getFile().getContents());
        if (selectedTransactionModel.getExplainedTransactionAttachement() != null) {
            InputStream stream = new ByteArrayInputStream(selectedTransactionModel.getExplainedTransactionAttachement());
            selectedTransactionModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
        }
    }

    public List<Project> completeProjects() throws Exception {
        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setActive(Boolean.TRUE);
        return projectService.getProjectsByCriteria(projectCriteria);
    }

    public String deleteTransaction() {

        Transaction transaction = getTransactionEntity(selectedTransactionModel);
        transaction.setDeleteFlag(true);
        if (transaction.getParentTransaction() != null) {
            transactionService.deleteChildTransaction(transaction);
        } else {
            transactionService.deleteTransaction(transaction);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("", "Transaction deleted successfully"));
        return "bank-transactions?faces-redirect=true";
    }

    public List<TransactionType> transactionTypes() throws Exception {
        return transactionTypeService.findAllChild();

    }

    public List<Invoice> completeInvoice() {
        List<Invoice> invoiceList = new ArrayList<>();
        for (Invoice invoice : invoiceService.getInvoiceListByDueAmount()) {
            invoiceList.add(invoice);
        }
        return invoiceList;
    }

    public List<Purchase> completePurchase() {
        List<Purchase> purchaseList = purchaseService.getPurchaseListByDueAmount();
        return purchaseList;
    }

    public String autocompleteInvoiceLebal(Invoice invoice) {
        return invoice != null ? invoice.getInvoiceReferenceNumber() + ':' + invoice.getDueAmount() + '(' + invoice.getInvoiceContact().getFirstName() + ')' : "";
    }

    public List<TransactionCategory> transactionCategories(TransactionType transactionType) throws Exception {
        String name="";
        List<TransactionCategory> transactionCategoryParentList = new ArrayList<>();
        List<TransactionCategory> transactionCategoryList = new ArrayList<>();
        if (transactionType != null) {
            transactionCategoryList = transactionCategoryService.findAllTransactionCategoryByTransactionType(transactionType.getTransactionTypeCode(),name);
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

}
