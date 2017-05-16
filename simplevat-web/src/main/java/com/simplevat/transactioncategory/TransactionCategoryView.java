package com.simplevat.transactioncategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.exception.UnauthorizedException;
import com.simplevat.security.ContextUtils;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.TransactionTypeService;

import lombok.Getter;
import lombok.Setter;

@Controller
@ManagedBean(name = "transactionCategoryView")
@RequestScoped
public class TransactionCategoryView {

	
	@Getter
	public String TRANSACTION_CATEGORY = "/pages/secure/transactioncategory/";
	@Getter
	public String CREATE_PAGE = "create-transactioncategory.xhtml";
	@Getter
	public final String LIST_PAGE = "all-transactioncategory.xhtml";
	@Getter
	public String HOME_PAGE = "transactioncategory.xhtml";
	
	@Autowired
	private TransactionCategoryServiceNew<Integer, TransactionCategory> transactionCategoryService;
	
	@Autowired
	TransactionTypeService transactionTypeService;

	private List<TransactionCategory> transactionCategories;

	@Getter
	private List<TransactionType> transactionTypes;


	@Getter
	public boolean editMode = false;
	
	@Getter
	@Setter
	private TransactionCategory selectedTransactionCategory;

	@PostConstruct
	public void init() {
		this.transactionCategories = transactionCategoryService.findAll();
		this.transactionTypes = transactionTypeService.findAll();
	}
	
	public List<TransactionCategory> getTransactionCategories() {
		return transactionCategories;
	}

	public List<TransactionCategory> getParentTransactionCategories(String q) {
		List<TransactionCategory> filterList = new ArrayList<>();
		for(TransactionCategory transactionCategory : transactionCategories) {
			StringBuilder strbld = new StringBuilder();
			strbld.append(transactionCategory.getTransactionCategoryCode());
			strbld.append("_");
			strbld.append(transactionCategory.getTransactionCategoryName());
			if(strbld.toString().toLowerCase().contains(q.toLowerCase())) {
				filterList.add(transactionCategory);
			}
		}
		return filterList;
	}

	public String createNewCategory() {
		editMode = false;
		selectedTransactionCategory = new TransactionCategory();
		this.transactionTypes = transactionTypeService.findAll();
		return TRANSACTION_CATEGORY + CREATE_PAGE;
	}

	public String deleteTransactionCategory() {
		transactionCategoryService.delete(selectedTransactionCategory,
				selectedTransactionCategory.getTransactionCategoryCode());
		this.transactionCategories = transactionCategoryService.findAll();
		return TRANSACTION_CATEGORY + HOME_PAGE + "?faces-redirect=true";
	}


	public String saveAndContinue() throws UnauthorizedException {
		int userId = ContextUtils.getUserContext().getUserId();
		selectedTransactionCategory.setCreatedBy(userId);
		selectedTransactionCategory.setDeleteFlag(false);
		selectedTransactionCategory.setLastUpdatedBy(userId);
		selectedTransactionCategory.setOrderSequence(1);
		selectedTransactionCategory.setCreatedDate(LocalDateTime.now());
		selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
		transactionCategoryService.persist(selectedTransactionCategory,
				selectedTransactionCategory.getTransactionCategoryCode());
		selectedTransactionCategory = new TransactionCategory();
		return TRANSACTION_CATEGORY + CREATE_PAGE + "?faces-redirect=true";
	}

	public String save() throws UnauthorizedException {
		int userId = ContextUtils.getUserContext().getUserId();
		selectedTransactionCategory.setCreatedBy(userId);
		selectedTransactionCategory.setDeleteFlag(false);
		selectedTransactionCategory.setLastUpdatedBy(userId);
		selectedTransactionCategory.setOrderSequence(1);
		selectedTransactionCategory.setCreatedDate(LocalDateTime.now());
		selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
		transactionCategoryService.persist(selectedTransactionCategory,
				selectedTransactionCategory.getTransactionCategoryCode());
		init();
		// FacesContext.getCurrentInstance().getExternalContext().redirect("transactioncategory.xhtml?faces-redirect=true");
		return TRANSACTION_CATEGORY + HOME_PAGE + "?faces-redirect=true";
	}

	public String update() throws UnauthorizedException {
		int userId = ContextUtils.getUserContext().getUserId();
		selectedTransactionCategory.setLastUpdatedBy(userId);
		selectedTransactionCategory.setOrderSequence(1);
		selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
		transactionCategoryService.update(selectedTransactionCategory,
				selectedTransactionCategory.getTransactionCategoryCode());
		init();
		return TRANSACTION_CATEGORY + HOME_PAGE + "?faces-redirect=true";
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
		return TRANSACTION_CATEGORY + CREATE_PAGE + "?faces-redirect=true";
	}
	
	public String editAction() {
		this.editMode=true;
		return TRANSACTION_CATEGORY + CREATE_PAGE;
	}
	
	public List<TransactionType> getAllTransactionType(String str) {
		if(str == null ) {
			return this.transactionTypes;
		}
		List<TransactionType> filterList = new ArrayList<>();
		for(TransactionType type : this.transactionTypes) {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(type.getTransactionTypeName());
			strBuilder.append("_");
			strBuilder.append(type.getTransactionTypeCode());
			if(strBuilder.toString().contains(str)) {
				filterList.add(type);
			}
		}
		
		return filterList;
	}
}
