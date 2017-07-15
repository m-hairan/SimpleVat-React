package com.simplevat.service.impl.bankaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.bankaccount.BankAccountDao;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.bankaccount.BankAccountService;

@Service("bankAccountService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class BankAccountServiceImpl implements BankAccountService{

	@Autowired
    public BankAccountDao bankAccountDao;
    
	@Override
	public List<BankAccount> getBankAccounts() {
		return getDao().getBankAccounts();
	}

	@Override
	public List<BankAccount> getBankAccountByUser(int userId) {
		return bankAccountDao.getBankAccountByUser(userId);
	}

	@Override
	public BankAccountDao getDao() {
		return this.bankAccountDao;
	}
	
}
