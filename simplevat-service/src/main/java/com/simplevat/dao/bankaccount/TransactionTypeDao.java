package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.dao.Dao;
import com.simplevat.entity.bankaccount.TransactionType;

public interface TransactionTypeDao extends Dao<Integer, TransactionType> {

    public TransactionType updateOrCreateTransaction(TransactionType transactionType);

    public TransactionType getTransactionType(Integer id);

    public TransactionType getDefaultTransactionType();

    public List<TransactionType> findAll();

    public List<TransactionType> findAllChild();

    public List<TransactionType> findByText(String transactionTxt);

}
