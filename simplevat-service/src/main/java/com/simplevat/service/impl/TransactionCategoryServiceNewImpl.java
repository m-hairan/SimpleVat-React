package com.simplevat.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.criteria.TransactionCategoryFilterNew;
import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.dao.bankaccount.TransactionCategoryDaoNew;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.service.TransactionCategoryServiceNew;

@Service("transactionCategoryService")
@Transactional
public class TransactionCategoryServiceNewImpl extends TransactionCategoryServiceNew {

    @Autowired
    @Qualifier(value = "transactionCategoryDao")
    private TransactionCategoryDaoNew dao;

    @Override
    public TransactionCategoryDaoNew getDao() {
        return dao;
    }

    @Override
    public List<TransactionCategory> findAllTransactionCategory() {
        return getDao().executeNamedQuery("findAllTransactionCategory");
    }

    @Override
    public TransactionCategory getDefaultTransactionCategory() {
        List<TransactionCategory> transactionCategories = findAllTransactionCategory();

        if (CollectionUtils.isNotEmpty(transactionCategories)) {
            return transactionCategories.get(0);
        }
        return null;
    }

    @Override
    public List<TransactionCategory> getCategoriesByComplexCriteria(TransactionCategoryCriteria criteria) {
        TransactionCategoryFilterNew filter = new TransactionCategoryFilterNew(criteria);
        return this.filter(filter);

    }

    @Override
    public List<TransactionCategory> findAllTransactionCategoryByTransactionType(Integer transactionTypeCode) {
        return dao.findAllTransactionCategoryByTransactionType(transactionTypeCode);
    }

}
