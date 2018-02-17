package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.SimpleVatService;

public abstract class TransactionTypeService extends SimpleVatService<Integer, TransactionType> {

    public abstract List<TransactionType> getTransactionTypesByCriteria(TransactionTypeCriteria transactionTypeCriteria) throws Exception;

    public abstract TransactionType updateOrCreateTransaction(TransactionType transactionType);

    public abstract TransactionType getTransactionType(Integer id);

    public abstract TransactionType getDefaultTransactionType();

    public abstract List<TransactionType> findAll();
   
    public abstract List<TransactionType> findByText(String transactionTxt);

    public abstract List<TransactionType> findAllChild();

}
