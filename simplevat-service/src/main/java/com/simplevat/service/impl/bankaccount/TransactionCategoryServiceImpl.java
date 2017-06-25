package com.simplevat.service.impl.bankaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.dao.bankaccount.TransactionCategoryDao;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.service.bankaccount.TransactionCategoryService;

@Service("transactionCategoryService")
public class TransactionCategoryServiceImpl implements TransactionCategoryService {

	@Autowired
	private TransactionCategoryDao transactionCategoryDao;
	
	@Override
	public List<TransactionCategory> getTransactionCategoriesByCriteria(
			TransactionCategoryCriteria transactionCategoryCriteria)
			throws Exception {
		return transactionCategoryDao.getTransactionCategoriesByCriteria(transactionCategoryCriteria);
	}

	@Override
	public TransactionCategory updateOrCreateTransaction(
			TransactionCategory transactionCategory) {
		return transactionCategoryDao.updateOrCreateTransaction(transactionCategory);
	}

	@Override
	public TransactionCategory getTransactionCategory(Integer id) {
		return transactionCategoryDao.getTransactionCategory(id);
	}

	@Override
	public TransactionCategory getDefaultTransactionCategory() {
		return transactionCategoryDao.getDefaultTransactionCategory();
	}

}
