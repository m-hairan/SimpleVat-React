package com.simplevat.service.impl.bankaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.criteria.bankaccount.TransactionTypeFilter;
import com.simplevat.dao.bankaccount.TransactionTypeDao;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.bankaccount.TransactionTypeService;

@Service("transactionTypeService")
public class TransactionTypeServiceImpl extends TransactionTypeService {

    @Autowired
    private TransactionTypeDao transactionTypeDao;

    @Override
    public List<TransactionType> getTransactionTypesByCriteria(
            TransactionTypeCriteria transactionTypeCriteria) throws Exception {
        TransactionTypeFilter filter = new TransactionTypeFilter(transactionTypeCriteria);
        return transactionTypeDao.filter(filter);
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

    @Override
    public TransactionType getDefaultTransactionType() {
        return transactionTypeDao.getDefaultTransactionType();
    }

    @Override
    public TransactionTypeDao getDao() {
        return this.transactionTypeDao;
    }

    @Override
    public List<TransactionType> findAllChild() {
        return transactionTypeDao.findAllChild();
    }
}
