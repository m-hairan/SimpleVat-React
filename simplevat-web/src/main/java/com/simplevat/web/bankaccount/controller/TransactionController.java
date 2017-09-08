package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.criteria.ProjectCriteria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
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
import com.simplevat.web.constant.BankAccountConstant;
import com.simplevat.web.constant.TransactionStatusConstant;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.PostConstruct;

@Controller
@SpringScopeView
public class TransactionController extends TransactionControllerHelper implements Serializable {

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

        selectedBankAccountModel = FacesUtil.getSelectedBankAccount();
        Object objselectedTransactionModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedTransactionId");

        if (objselectedTransactionModel != null) {
            selectedTransactionModel = getTransactionModel(transactionService.findByPK(Integer.parseInt(objselectedTransactionModel.toString())));
        } else {
            selectedTransactionModel = new TransactionModel();
            TransactionType transactionType = transactionTypeService.getDefaultTransactionType();
            if (transactionType != null) {
                selectedTransactionModel.setTransactionType(transactionType);
            }
            TransactionCategory transactionCategory = transactionCategoryService.getDefaultTransactionCategory();
            if (transactionCategory != null) {
                selectedTransactionModel.setExplainedTransactionCategory(transactionCategory);
            }
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

    public String editTransection() {
        System.out.println("selectedM :" + selectedTransactionModel);
        //FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("selectedTransactionId", String.valueOf(selectedTransactionModel.getTransactionId()));
        return "edit-bank-transaction?faces-redirect=true&selectedTransactionId=" + selectedTransactionModel.getTransactionId();
    }

    public String saveTransaction() {
        User loggedInUser = FacesUtil.getLoggedInUser();
        System.out.println("selectedTransactionModel :" + selectedTransactionModel);
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
        System.out.println("before inside if :" + transaction.getTransactionStatus());

        if (selectedTransactionModel.getTransactionType() != null && selectedTransactionModel.getExplainedTransactionCategory() != null) {
            if (selectedTransactionModel.getTransactionStatus() == null
                    || selectedTransactionModel.getTransactionStatus().getExplainationStatusCode() == 0) {
                System.out.println("inside if :" + transaction.getTransactionStatus());
                TransactionStatus transactionStatus = transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED);
                transaction.setTransactionStatus(transactionStatus);
            }
        } else {

            System.out.println("inside inside if :" + transaction.getTransactionStatus());
            if (selectedTransactionModel.getTransactionStatus() == null
                    || selectedTransactionModel.getTransactionStatus().getExplainationStatusCode() == 0) {
                TransactionStatus transactionStatus = transactionStatusService.findByPK(TransactionStatusConstant.UNEXPLIANED);
                transaction.setTransactionStatus(transactionStatus);
            }
        }
        System.out.println("after inside if :" + transaction.getTransactionStatus());

        if (transaction.getTransactionId() == null) {
                transactionService.persist(transaction);
        } else {
            transactionService.update(transaction);
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction saved successfully"));
        return "bank-transactions?faces-redirect=true";

    }

    public String saveAndContinueTransaction() {
        User loggedInUser = FacesUtil.getLoggedInUser();
        Transaction transaction = getTransaction(selectedTransactionModel);

        transaction.setLastUpdatedBy(loggedInUser.getUserId());
        transaction.setCreatedBy(loggedInUser.getUserId());
        transaction.setBankAccount(selectedBankAccount);

        if (selectedTransactionModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(selectedTransactionModel.getTransactionType().getTransactionTypeCode());
            transaction.setTransactionType(transactionType);
            transaction.setDebitCreditFlag(transactionType.getDebitCreditFlag());
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
        if (transaction.getTransactionId() == null) {
                transactionService.persist(transaction);
        } else {
            transactionService.update(transaction);
        }

        this.setSelectedTransactionModel(new TransactionModel());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction saved successfully"));
        return "/pages/secure/bankaccount/edit-bank-transaction.xhtml?faces-redirect=true";

    }
    
      public List<Project> completeProjects() throws Exception {
        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setActive(Boolean.TRUE);
        return projectService.getProjectsByCriteria(projectCriteria);
    }

      
    public String deleteTransaction() {

        Transaction transaction = getTransaction(selectedTransactionModel);
        transaction.setDeleteFlag(true);
        transactionService.deleteTransaction(transaction);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction deleted successfully"));
        return "bank-transactions?faces-redirect=true";
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
