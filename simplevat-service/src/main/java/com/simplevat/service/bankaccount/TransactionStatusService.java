package com.simplevat.service.bankaccount;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.simplevat.dao.Dao;
import com.simplevat.service.SimpleVatService;

@SuppressWarnings("hiding")
@Service
@ManagedBean(name = "transactionStatusService")
public class TransactionStatusService<Integer, TransactionStatus>  
	implements SimpleVatService<Integer, TransactionStatus> {
	
	@Autowired
    @Qualifier(value = "transactionStatusDao")
    private Dao<Integer, TransactionStatus> dao;

	@Override
	public Dao<Integer, TransactionStatus> getDao() {
		 return dao;
	}

}
