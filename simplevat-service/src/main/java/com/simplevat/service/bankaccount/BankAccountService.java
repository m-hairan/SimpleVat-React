package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.entity.bankaccount.BankAccount;

public interface BankAccountService {
	
	public List<BankAccount> getBankAccounts();

	public BankAccount createOrUpdateBankAccount(BankAccount bankAccount);
	
	public BankAccount getBankAccount(Integer id);

}
