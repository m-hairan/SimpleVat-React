package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.dao.Dao;
import com.simplevat.entity.bankaccount.BankAccount;

public interface BankAccountDao extends Dao<Integer, BankAccount> {
	
	public List<BankAccount> getBankAccounts();

	
	public List<BankAccount> getBankAccountByUser(int userId);
	
}
