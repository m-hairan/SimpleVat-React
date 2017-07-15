package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.SimpleVatService;

public interface BankAccountService extends SimpleVatService<Integer,BankAccount> {
	
	public List<BankAccount> getBankAccounts();


	public List<BankAccount>  getBankAccountByUser(int userId);

}
