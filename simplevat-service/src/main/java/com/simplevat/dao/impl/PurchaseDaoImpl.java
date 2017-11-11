package com.simplevat.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.ExpenseDao;
import com.simplevat.dao.PurchaseDao;
import com.simplevat.entity.Expense;
import com.simplevat.entity.Purchase;

@Repository
@Transactional
public class PurchaseDaoImpl extends AbstractDao<Integer, Purchase> implements PurchaseDao {

	
    @Override
	public List<Purchase> getAllPurchase() {
		List<Purchase> expenses = this.executeNamedQuery("allPurchase");
		return expenses;
	}

}
