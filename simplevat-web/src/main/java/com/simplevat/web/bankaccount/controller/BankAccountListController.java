package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.bankaccount.BankAccountService;

@Controller
@SpringScopeView
public class BankAccountListController {

	@Autowired
	private BankAccountService bankAccountService;
	
	private List<BankAccount> bankAccounts;

	public List<BankAccount> getBankAccounts() {
		
		bankAccounts = bankAccountService.getBankAccounts();
		
		return bankAccounts;
	}

	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	
	
	
}
