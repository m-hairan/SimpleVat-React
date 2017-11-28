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
import com.simplevat.web.exceptions.UnauthorizedException;
import com.simplevat.security.ContextUtils;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.transactioncategory.model.TransactionCategoryModel;
import java.io.Serializable;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

@Controller
//@ManagedBean(name = "transactionCategoryView")
@SpringScopeView

public class TransactionCategoryView extends BaseController implements Serializable {

    @Getter
    public String CREATE_PAGE = "create-transactioncategory";
    @Getter
    public String HOME_PAGE = "index?faces-redirect=true&tabIndex=2";

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
    public TransactionCategoryModel transactionCategoryModel;

    private List<TransactionCategoryModel> transactionCategoryModels;

    @Getter
    public boolean editMode = false;

    @Getter
    @Setter
    private TransactionCategory selectedTransactionCategory;

    @Getter
    @Setter
    private TranscationCategoryHelper transcationCategoryHelper;

    public TransactionCategoryView() {
        super(ModuleName.SETTING_MODULE);
    }

    @PostConstruct
    public void init() {
        selectedTransactionCategory = new TransactionCategory();
        transcationCategoryHelper = new TranscationCategoryHelper();
        Object objContactId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedCategoryId");
        if (objContactId != null) {
            transactionTypes = transactionTypeService.findAll();
            selectedTransactionCategory = transactionCategoryService.findByPK(Integer.parseInt(objContactId.toString()));
            transactionCategoryModel = transcationCategoryHelper.getCategory(selectedTransactionCategory);
        } else {
            transactionTypes = transactionTypeService.findAll();
            transactionCategoryModel = new TransactionCategoryModel();
            TransactionCategory transactionCategory = transactionCategoryService.getDefaultTransactionCategory();
            if (transactionCategory != null) {
                transactionCategoryModel.setParentTransactionCategory(transactionCategory);
            }
        }

    }

    public List<TransactionCategory> getTransactionCategories() {
        return transactionCategories;
    }

    public List<TransactionCategoryModel> getAllCategories() {
        transactionCategories = transactionCategoryService.findAllTransactionCategory();
        transactionCategoryModels = new ArrayList<>();

        for (TransactionCategory category : transactionCategories) {
            TransactionCategoryModel model = transcationCategoryHelper.getCategory(category);
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
        selectedTransactionCategory.setLastUpdateBy(userId);
        selectedTransactionCategory.setOrderSequence(1);
        selectedTransactionCategory.setCreatedDate(LocalDateTime.now());
        selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
        transactionCategoryService.persist(selectedTransactionCategory, selectedTransactionCategory.getTransactionCategoryId());
        selectedTransactionCategory = new TransactionCategory();

        return CREATE_PAGE + "?faces-redirect=true";
    }

    public String save() throws UnauthorizedException {
        int userId = ContextUtils.getUserContext().getUserId();
        selectedTransactionCategory = transcationCategoryHelper.getTrascationModel(transactionCategoryModel);
        selectedTransactionCategory.setCreatedBy(userId);
        selectedTransactionCategory.setDeleteFlag(false);
        selectedTransactionCategory.setLastUpdateBy(userId);
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
        selectedTransactionCategory = transcationCategoryHelper.getTrascationModel(transactionCategoryModel);
        selectedTransactionCategory.setLastUpdateBy(userId);
        selectedTransactionCategory.setOrderSequence(1);
        selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());

        if (transactionCategoryModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(transactionCategoryModel.getTransactionType().getTransactionTypeCode());
            selectedTransactionCategory.setTransactionType(transactionType);
        }
        if (transactionCategoryModel.getTransactionCategoryId() != null && transactionCategoryModel.getTransactionCategoryId() > 0) {
            transactionCategoryService.update(selectedTransactionCategory);
        }
        selectedTransactionCategory = new TransactionCategory();
        init();

        return HOME_PAGE;
    }

    public String updateAndContinue() throws UnauthorizedException {
        int userId = ContextUtils.getUserContext().getUserId();
        selectedTransactionCategory.setLastUpdateBy(userId);
        selectedTransactionCategory.setOrderSequence(1);
        selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
        transactionCategoryService.update(selectedTransactionCategory,
        selectedTransactionCategory.getTransactionCategoryId());
        selectedTransactionCategory = new TransactionCategory();
        init();
        return CREATE_PAGE + "?faces-redirect=true";
    }

    public String editAction() {
        this.editMode = true;
        //return "create-transactioncategory?faces-redirect=true&selectedCategoryId="+transactionCategoryModel.getTransactionCategoryCode() ;
        System.out.println("transactionCategoryModel.getTransactionCategoryId()======================"+transactionCategoryModel.getTransactionCategoryId());
        return CREATE_PAGE + "?faces-redirect=true&selectedCategoryId=" + transactionCategoryModel.getTransactionCategoryId();

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
        transactionCategoryModel.setDeleteFlag(Boolean.TRUE);
        selectedTransactionCategory = transcationCategoryHelper.getTrascationModel(transactionCategoryModel);
        transactionCategoryService.update(selectedTransactionCategory);
        this.transactionCategories = transactionCategoryService.executeNamedQuery("findAllTransactionCategory");
        return HOME_PAGE;

    }

}
