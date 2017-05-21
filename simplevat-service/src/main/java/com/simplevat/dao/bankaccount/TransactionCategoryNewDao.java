package com.simplevat.dao.bankaccount;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.AbstractDao;
import com.simplevat.entity.bankaccount.TransactionCategory;

@Repository(value = "transactionCategoryDao")
public class TransactionCategoryNewDao extends AbstractDao<Integer, TransactionCategory> {


}
