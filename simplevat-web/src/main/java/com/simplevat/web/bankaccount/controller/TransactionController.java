package com.simplevat.web.bankaccount.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.ProjectService;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionStatusService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.bankaccount.model.BankAccountModel;
import com.simplevat.web.utils.FacesUtil;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@Scope("view")
public class TransactionController extends TransactionControllerHelper {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    private BankAccountService bankAccountService;

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

    @PostConstruct
    public void init() {

        selectedTransactionModel = new TransactionModel();
        selectedBankAccountModel=FacesUtil.getSelectedBankAccount();
        TransactionType transactionType = transactionTypeService.getDefaultTransactionType();
        if (transactionType != null) {
            selectedTransactionModel.setTransactionType(transactionType);
        }
        TransactionCategory transactionCategory = transactionCategoryService.getDefaultTransactionCategory();
        if (transactionCategory != null) {
            selectedTransactionModel.setExplainedTransactionCategory(transactionCategory);
        }

    }

    public String createTransaction() {
//		 this.selectedTransactionModel = new TransactionModel();
//		 
//		TransactionType transactionType = transactionTypeService.getDefaultTransactionType();
//		if (transactionType != null) {
//			selectedTransactionModel.setTransactionType(transactionType);
//		}
//		TransactionCategory transactionCategory = transactionCategoryService.getDefaultTransactionCategory();
//		if (transactionCategory != null) {
//			selectedTransactionModel.setExplainedTransactionCategory(transactionCategory);
//		}

        return "/pages/secure/bankaccount/edit-bank-transaction.xhtml";
    }

    public String saveTransaction() {
        User loggedInUser = FacesUtil.getLoggedInUser();
        System.out.println("selectedTransactionModel :"+selectedTransactionModel);
        Transaction transaction = getTransaction(selectedTransactionModel);
        selectedBankAccount = getBankAccount(selectedBankAccountModel);
        transaction.setLastUpdatedBy(loggedInUser.getUserId());
        transaction.setCreatedBy(loggedInUser.getUserId());
        transaction.setBankAccount(selectedBankAccount);

        if (selectedTransactionModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(selectedTransactionModel.getTransactionType().getTransactionTypeCode());
            transaction.setTransactionType(transactionType);
            transaction.setDebitCreditFlag(transactionType.getDebitCreditFlag());
        }

        BankAccount bankAccount = bankAccountService.findByPK(selectedBankAccount.getBankAccountId());
        if (transaction.getDebitCreditFlag() == 'C') {
            bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().add(transaction.getTransactionAmount()));
        } else if (transaction.getDebitCreditFlag() == 'D') {
            bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().subtract(transaction.getTransactionAmount()));
        }

        if (selectedTransactionModel.getExplainedTransactionCategory() != null) {
            TransactionCategory transactionCategory = transactionCategoryService.findByPK(selectedTransactionModel.getExplainedTransactionCategory().getTransactionCategoryCode());
            transaction.setExplainedTransactionCategory(transactionCategory);
        }

        if (selectedTransactionModel.getProject() != null) {
            Project project = projectService.findByPK(selectedTransactionModel.getProject().getProjectId());
            transaction.setProject(project);
        }

        if (selectedTransactionModel.getTransactionStatus() != null) {
            TransactionStatus transactionStatus = transactionStatusService.findByPK(selectedTransactionModel.getTransactionStatus().getExplainationStatusCode());
            transaction.setTransactionStatus(transactionStatus);
        }

        if (selectedTransactionModel.getAttachmentFile() != null && selectedTransactionModel.getAttachmentFile().getSize() > 0) {
            storeUploadedFile(selectedTransactionModel, transaction, fileLocation);
        }

        if (selectedTransactionModel.getTransactionStatus() == null
                || selectedTransactionModel.getTransactionStatus().getExplainationStatusCode() == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("explainationStatusName", "EXPLAINED");
            TransactionStatus transactionStatus = transactionStatusService.findByAttributes(map).get(0);
            transaction.setTransactionStatus(transactionStatus);
        }

//                if (transaction.getTransactionId() == null || transaction.getTransactionId() == 0) {
//                   transaction.setTransactionId(null);
//                   transactionService.persist(transaction, 0);
//                }else{
//                   transactionService.update(transaction, transaction.getTransactionId());
//                }
        transaction.setCurrentBalance(bankAccount.getCurrentBalance());
        transactionService.persist(transaction);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction saved successfully"));
        return "bank-transactions?faces-redirect=true";

    }

    public String saveAndContinueTransaction() {

        Transaction transaction = getTransaction(selectedTransactionModel);

        transaction.setLastUpdatedBy(12345);
        transaction.setCreatedBy(12345);
        transaction.setBankAccount(selectedBankAccount);

        if (selectedTransactionModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(selectedTransactionModel.getTransactionType().getTransactionTypeCode());
            transaction.setTransactionType(transactionType);
            transaction.setDebitCreditFlag(transactionType.getDebitCreditFlag());
        }

        BankAccount bankAccount = bankAccountService.findByPK(selectedBankAccount.getBankAccountId());
        if (transaction.getDebitCreditFlag() == 'C') {
            bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().add(transaction.getTransactionAmount()));
        } else if (transaction.getDebitCreditFlag() == 'D') {
            bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().subtract(transaction.getTransactionAmount()));
        }

        if (selectedTransactionModel.getExplainedTransactionCategory() != null) {
            TransactionCategory transactionCategory = transactionCategoryService.findByPK(selectedTransactionModel.getExplainedTransactionCategory().getTransactionCategoryCode());
            transaction.setExplainedTransactionCategory(transactionCategory);
        }

        if (selectedTransactionModel.getTransactionStatus() != null) {
            TransactionStatus transactionStatus = transactionStatusService.findByPK(selectedTransactionModel.getTransactionStatus().getExplainationStatusCode());
            transaction.setTransactionStatus(transactionStatus);
        }

        if (selectedTransactionModel.getTransactionStatus().getExplainationStatusCode() == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("explainationStatusName", "EXPLAINED");
            TransactionStatus transactionStatus = transactionStatusService.findByAttributes(map).get(0);
            transaction.setTransactionStatus(transactionStatus);
        }

        if (selectedTransactionModel.getAttachmentFile().getSize() > 0) {
            storeUploadedFile(selectedTransactionModel, transaction, fileLocation);
        }
        bankAccountService.persist(bankAccount);
        transactionService.updateOrCreateTransaction(transaction);
        this.setSelectedTransactionModel(new TransactionModel());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction saved successfully"));
        return "/pages/secure/bankaccount/edit-bank-transaction.xhtml?faces-redirect=true";

    }

    public void deleteTransaction() {

        Transaction transaction = getTransaction(selectedTransactionModel);
        transaction.setDeleteFlag(true);
        transactionService.updateOrCreateTransaction(transaction);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction deleted successfully"));

    }

    public List<TransactionType> transactionTypes(final String searchQuery) throws Exception {
        TransactionTypeCriteria transactionTypeCriteria = new TransactionTypeCriteria();
        transactionTypeCriteria.setActive(Boolean.TRUE);
        return transactionTypeService.getTransactionTypesByCriteria(transactionTypeCriteria);
    }

    public List<TransactionCategory> transactionCategories(final String searchQuery) throws Exception {
        TransactionCategoryCriteria transactionCategoryCriteria = new TransactionCategoryCriteria();
        transactionCategoryCriteria.setActive(Boolean.TRUE);
        return transactionCategoryService.getCategoriesByComplexCriteria(transactionCategoryCriteria);
    }

    public List<TransactionStatus> transactionStatuses(final String searchQuery) throws Exception {
        return transactionStatusService.executeNamedQuery("findAllTransactionStatues");
    }

}
