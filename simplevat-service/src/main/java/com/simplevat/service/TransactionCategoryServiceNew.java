package com.simplevat.service;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplevat.dao.Dao;

@SuppressWarnings("hiding")
@Service
@ManagedBean(name = "transactionCategoryServiceNew")
public class TransactionCategoryServiceNew<Integer, TransactionCategory>
		implements SimpleVatService<Integer, TransactionCategory> {
	

	@Autowired
	private Dao<Integer, TransactionCategory> dao;

	@Override
	public Dao<Integer, TransactionCategory> getDao() {
		return dao;
	}
}
