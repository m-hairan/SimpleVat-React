package com.simplevat.dao.bankaccount;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import com.simplevat.dao.AbstractFilter;
import com.simplevat.entity.bankaccount.TransactionCategory;

public class TransactionCategoryFilter extends AbstractFilter<TransactionCategory> {

	private TransactionCategory transactionCategory;
	
	public TransactionCategoryFilter(TransactionCategory transactionCategory_) {
		this.transactionCategory = transactionCategory_;
	}
	@Override
	protected void buildPredicates(Root<TransactionCategory> root, CriteriaBuilder cb) {
		add(cb.equal(root.get("deleteFlag"), transactionCategory.getDeleteFlag()));
	}

}
