package com.simplevat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.Dao;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.service.TransactionCategoryServiceNew;

import org.springframework.beans.factory.annotation.Qualifier;

@Service("transactionCategoryServiceNew")
@Transactional
public class TransactionCategoryServiceNewImpl implements TransactionCategoryServiceNew {


    @Autowired
    @Qualifier(value = "transactionCategoryDao")
    private Dao<Integer, TransactionCategory> dao;

    @Override
    public Dao<Integer, TransactionCategory> getDao() {
        return dao;
    }

	@Override
	public List<TransactionCategory> findAllTransactionCategory() {
		return getDao().executeNamedQuery("findAllTransactionCategory");
	}
}
