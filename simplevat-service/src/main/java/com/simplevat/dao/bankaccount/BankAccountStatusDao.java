package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.entity.bankaccount.BankAccountStatus;

public interface BankAccountStatusDao {
	
	public List<BankAccountStatus> getBankAccountStatuses();
	
	public BankAccountStatus getBankAccountStatus(Integer id);
	
	public BankAccountStatus getBankAccountStatusByName(String status);

}
