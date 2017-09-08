package com.simplevat.service.bankaccount;

import java.util.List;
import java.util.Map;

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.service.SimpleVatService;

public abstract class TransactionService extends SimpleVatService<Integer, Transaction> {

    public abstract List<Transaction> getTransactionsByCriteria(
            TransactionCriteria transactionCriteria) throws Exception;

    public abstract Transaction updateOrCreateTransaction(Transaction transaction);

    public abstract Map<Object, Number> getCashOutData();

    public abstract Map<Object, Number> getCashInData();

    public abstract int getMaxTransactionValue(Map<Object, Number> cashInMap, Map<Object, Number> cashOutMap);

    public abstract void persist(Transaction transaction);

    public abstract Transaction update(Transaction transaction);
    
    public abstract Transaction deleteTransaction(Transaction transaction);
}
