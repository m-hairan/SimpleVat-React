package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.entity.bankaccount.BankAccount;

public interface BankAccountDao {
	
	public List<BankAccount> getBankAccounts();

	public BankAccount createOrUpdateBankAccount(BankAccount bankAccount);
	
	public BankAccount getBankAccount(Integer id);
	
}
