package com.simplevat.service.impl.bankaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.simplevat.dao.bankaccount.TransactionStatusDao;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.service.bankaccount.TransactionStatusService;

@Service("transactionStatusService")
public class TransactionStatusServiceImpl implements TransactionStatusService {

	@Autowired
    @Qualifier(value = "transactionStatusDao")
    private TransactionStatusDao dao;
	
	@Override
	public TransactionStatusDao getDao() {
		return dao;
	}

	@Override
	public List<TransactionStatus> findAllTransactionStatues() {
		return getDao().findAllTransactionStatues();
	}

}
