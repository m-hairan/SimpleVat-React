package com.simplevat.service;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplevat.dao.Dao;
import org.springframework.beans.factory.annotation.Qualifier;

@SuppressWarnings("hiding")
@Service
@ManagedBean(name = "transactionCategoryServiceNew")
public class TransactionCategoryServiceNew<Integer, TransactionCategory>
        implements SimpleVatService<Integer, TransactionCategory> {


    @Autowired
    @Qualifier(value = "transactionCategoryDao")
    private Dao<Integer, TransactionCategory> dao;

    @Override
    public Dao<Integer, TransactionCategory> getDao() {
        return dao;
    }
}
