package com.simplevat.service.impl.bankaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.dao.bankaccount.TransactionTypeDao;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.bankaccount.TransactionTypeService;

@Service("transactionTypeService")
public class TransactionTypeServiceImpl implements TransactionTypeService {

	@Autowired
	private TransactionTypeDao transactionTypeDao;
	
	@Override
	public List<TransactionType> getTransactionTypesByCriteria(
			TransactionTypeCriteria transactionTypeCriteria) throws Exception {
		return transactionTypeDao.getTransactionTypesByCriteria(transactionTypeCriteria);
	}

	@Override
	public TransactionType updateOrCreateTransaction(
			TransactionType transactionType) {
		return transactionTypeDao.updateOrCreateTransaction(transactionType);
	}

	@Override
	public TransactionType getTransactionType(Integer id) {
		return transactionTypeDao.getTransactionType(id);
	}


	@Override
	public List<TransactionType> findAll() {
		return transactionTypeDao.findAll();
	}
}
