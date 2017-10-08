package com.simplevat.web.transactioncategory.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.web.exception.UnauthorizedException;
import com.simplevat.security.ContextUtils;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.transactioncategory.model.TransactionCategoryModel;
import java.io.Serializable;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Controller
//@ManagedBean(name = "transactionCategoryView")
@SpringScopeView

public class TransactionCategoryView extends TranscationCategoryHelper implements Serializable {

    @Getter
    public String CREATE_PAGE = "create-transactioncategory";
    @Getter
    public String HOME_PAGE = "setting?faces-redirect=true&tabIndex=2";

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    TransactionTypeService transactionTypeService;

    @Autowired
    TransactionTypeService transactionTypeService1;

    private List<TransactionCategory> transactionCategories;

    @Getter
    @Setter
    private List<TransactionType> transactionTypes;

    @Getter
    @Setter
    TransactionCategoryModel transactionCategoryModel;

    private List<TransactionCategoryModel> transactionCategoryModels;

    @Getter
    public boolean editMode = false;

    @Getter
    @Setter
    private TransactionCategory selectedTransactionCategory;

    @PostConstruct
    public void init() {
        selectedTransactionCategory = new TransactionCategory();
        transactionCategoryModel = new TransactionCategoryModel();

        Object objContactId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedCategoryId");
        if (objContactId != null) {
            transactionTypes = transactionTypeService.findAll();
            selectedTransactionCategory = transactionCategoryService.findByPK(Integer.parseInt(objContactId.toString()));
            transactionCategoryModel = getCategory(selectedTransactionCategory);
        } else {
            transactionTypes = transactionTypeService.findAll();
            transactionTypes = transactionTypeService.findAll();

        }

    }

    public List<TransactionCategory> getTransactionCategories() {
        return transactionCategories;
    }

    public List<TransactionCategoryModel> getAllCategories() {
        transactionCategories = transactionCategoryService.findAllTransactionCategory();
        transactionCategoryModels = new ArrayList<>();

        for (TransactionCategory category : transactionCategories) {
            TransactionCategoryModel model = getCategory(category);
            transactionCategoryModels.add(model);
        }

        return transactionCategoryModels;
    }

    /*
    
    public List<TransactionCategory> getParentTransactionCategories(String q) {
        if (q == null) {
            return this.transactionCategories;
        }
        List<TransactionCategory> filterList = new ArrayList<>();
        
        System.err.println("hello================================================:"+transactionCategories.get(0).getTransactionCategoryName()+"");
        
        for (TransactionCategory transactionCategory : transactionCategories) {
            StringBuilder strbld = new StringBuilder();
            strbld.append(transactionCategory.getTransactionCategoryCode());
            strbld.append("_");
            strbld.append(transactionCategory.getTransactionCategoryName());
            if (strbld.toString().toLowerCase().contains(q.toLowerCase())) {
                filterList.add(transactionCategory);
            }
        }
        return filterList;
    }

     */
    public String createNewCategory() {
        return CREATE_PAGE + "?faces-redirect=true";
    }

    public String saveAndContinue() throws UnauthorizedException {
        int userId = ContextUtils.getUserContext().getUserId();
        selectedTransactionCategory.setCreatedBy(userId);
        selectedTransactionCategory.setDeleteFlag(false);
        selectedTransactionCategory.setLastUpdatedBy(userId);
        selectedTransactionCategory.setOrderSequence(1);
        selectedTransactionCategory.setCreatedDate(LocalDateTime.now());
        selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
        transactionCategoryService.persist(selectedTransactionCategory, selectedTransactionCategory.getTransactionCategoryCode());
        selectedTransactionCategory = new TransactionCategory();

        return CREATE_PAGE + "?faces-redirect=true";
    }

    public String save() throws UnauthorizedException {
        int userId = ContextUtils.getUserContext().getUserId();
        selectedTransactionCategory = getTrascationModel(transactionCategoryModel);
        selectedTransactionCategory.setCreatedBy(userId);
        selectedTransactionCategory.setDeleteFlag(false);
        selectedTransactionCategory.setLastUpdatedBy(userId);
        selectedTransactionCategory.setOrderSequence(1);
        selectedTransactionCategory.setCreatedDate(LocalDateTime.now());
        selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
        if (transactionCategoryModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(transactionCategoryModel.getTransactionType().getTransactionTypeCode());
            selectedTransactionCategory.setTransactionType(transactionType);
        }
        if (transactionCategoryModel.getParentTransactionCategory() != null) {
            selectedTransactionCategory.setTransactionCategoryName(transactionCategoryModel.getTransactionCategoryName());
        }
        transactionCategoryService.persist(selectedTransactionCategory);
        return HOME_PAGE;
    }

    public String update() throws UnauthorizedException {
        int userId = ContextUtils.getUserContext().getUserId();
        selectedTransactionCategory = getTrascationModel(transactionCategoryModel);
        selectedTransactionCategory.setLastUpdatedBy(userId);
        selectedTransactionCategory.setOrderSequence(1);
        selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());

        if (transactionCategoryModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(transactionCategoryModel.getTransactionType().getTransactionTypeCode());
            selectedTransactionCategory.setTransactionType(transactionType);
        }
        if (transactionCategoryModel.getTransactionCategoryCode() != null && transactionCategoryModel.getTransactionCategoryCode() > 0) {
            transactionCategoryService.update(selectedTransactionCategory, selectedTransactionCategory.getTransactionCategoryCode());
        }
        selectedTransactionCategory = new TransactionCategory();
        init();

        return HOME_PAGE;
    }

    public String updateAndContinue() throws UnauthorizedException {
        int userId = ContextUtils.getUserContext().getUserId();
        selectedTransactionCategory.setLastUpdatedBy(userId);
        selectedTransactionCategory.setOrderSequence(1);
        selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
        transactionCategoryService.update(selectedTransactionCategory,
                selectedTransactionCategory.getTransactionCategoryCode());
        selectedTransactionCategory = new TransactionCategory();
        init();
        return CREATE_PAGE + "?faces-redirect=true";
    }

    public String editAction() {
        this.editMode = true;
        //return "create-transactioncategory?faces-redirect=true&selectedCategoryId="+transactionCategoryModel.getTransactionCategoryCode() ;
        return CREATE_PAGE + "?faces-redirect=true&selectedCategoryId=" + transactionCategoryModel.getTransactionCategoryCode();

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

    public String deleteTransactionCategory() {
        /*   
        selectedTransactionCategory.setDeleteFlag(Boolean.TRUE);
    
        transactionCategoryService.update(selectedTransactionCategory);
        this.transactionCategories = transactionCategoryService.executeNamedQuery("findAllTransactionCategory");
        return TRANSACTION_CATEGORY + HOME_PAGE + "?faces-redirect=true";
         */
        transactionCategoryModel.setDeleteFlag(Boolean.TRUE);
        selectedTransactionCategory = getTrascationModel(transactionCategoryModel);
        transactionCategoryService.update(selectedTransactionCategory);
        this.transactionCategories = transactionCategoryService.executeNamedQuery("findAllTransactionCategory");
        return HOME_PAGE;

    }

}
