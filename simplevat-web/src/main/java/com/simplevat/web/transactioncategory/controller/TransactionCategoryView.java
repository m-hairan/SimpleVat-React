package com.simplevat.web.transactioncategory.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.VatCategory;
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
import com.simplevat.service.VatCategoryService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.DefualtTypeConstant;
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
    public String HOME_PAGE = "transactioncategory-list?faces-redirect=true";

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    TransactionTypeService transactionTypeService;

    @Autowired
    VatCategoryService vatCategoryService;

    @Getter
    @Setter
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
//            TransactionCategory transactionCategory = transactionCategoryService.getDefaultTransactionCategory();
//            if (transactionCategory != null) {
//                transactionCategoryModel.setParentTransactionCategory(transactionCategory);
//            }
        }
        transactionCategories = transactionCategoryService.findAllTransactionCategory();

    }
//
//    public List<TransactionCategory> getAllCategories() {
//        List<TransactionCategory> transactionCategorys = transactionCategoryService.findAllTransactionCategory();
//        for (TransactionCategory transactionCategory : transactionCategorys) {
//            totalRowCount++;
//        }
//        return transactionCategorys;
//    }

    public List<TransactionCategory> completeCategoriesByTransaction(String transcationTxt) {
        System.out.println("===sout==" + transcationTxt);
        if (transactionCategoryModel.getTransactionType() != null) {
            return transactionCategoryService.findAllTransactionCategoryByTransactionType(transactionCategoryModel.getTransactionType().getTransactionTypeCode());
        }
        return new ArrayList<>();
    }

    public List<VatCategory> completeVatCategories() {
        return vatCategoryService.getVatCategoryList();
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

    public String saveAndContinueTransactionCategory() throws UnauthorizedException {
        save();
        return CREATE_PAGE + "?faces-redirect=true";
    }

    public String saveTransactionCategory() throws UnauthorizedException {
        save();
        return HOME_PAGE;
    }

    public void save() throws UnauthorizedException {
        int userId = ContextUtils.getUserContext().getUserId();
        selectedTransactionCategory = transcationCategoryHelper.getTrascationModel(transactionCategoryModel);
        if (selectedTransactionCategory.getDefaltFlag() == DefualtTypeConstant.YES) {
            System.out.println("insideif=========1=========" + selectedTransactionCategory.getDefaltFlag());
            TransactionCategory transactionCategory = transactionCategoryService.getDefaultTransactionCategoryByTransactionCategoryId(selectedTransactionCategory.getTransactionCategoryId());
            if (transactionCategory != null) {
                System.out.println("insideif=========3=========" + transactionCategory.getDefaltFlag());
                transactionCategory.setDefaltFlag(DefualtTypeConstant.NO);
                transactionCategoryService.update(transactionCategory);
            }
        }
        if (selectedTransactionCategory.getTransactionCategoryId() == null) {
            selectedTransactionCategory.setOrderSequence(1);
            selectedTransactionCategory.setCreatedBy(userId);
            selectedTransactionCategory.setCreatedDate(LocalDateTime.now());
            transactionCategoryService.persist(selectedTransactionCategory);
        } else {
            selectedTransactionCategory.setLastUpdateBy(userId);
            selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
            transactionCategoryService.update(selectedTransactionCategory);
        }
    }

    public String editAction() {
        this.editMode = true;
        //return "create-transactioncategory?faces-redirect=true&selectedCategoryId="+transactionCategoryModel.getTransactionCategoryCode() ;
        System.out.println("transactionCategoryModel.getTransactionCategoryId()======================" + selectedTransactionCategory.getTransactionCategoryId());
        return CREATE_PAGE + "?faces-redirect=true&selectedCategoryId=" + selectedTransactionCategory.getTransactionCategoryId();
    }

    public List<TransactionType> getAllTransactionType(String str) {
        if (str == null) {
            return this.transactionTypes;
        }
        List<TransactionType> filterList = new ArrayList<>();
        transactionTypes = transactionTypeService.findByText(str);
        System.out.println("=transactionTypes===" + transactionTypes);
        for (TransactionType type : transactionTypes) {
            filterList.add(type);
        }
        return filterList;
    }

    public String deleteTransactionCategory() {
        selectedTransactionCategory.setDeleteFlag(Boolean.TRUE);
        transactionCategoryService.update(selectedTransactionCategory);
        transactionCategories = transactionCategoryService.findAllTransactionCategory();
        return HOME_PAGE;

    }

}
