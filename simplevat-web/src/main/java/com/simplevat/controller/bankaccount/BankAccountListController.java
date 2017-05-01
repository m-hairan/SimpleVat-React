package com.simplevat.controller.bankaccount;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.bankaccount.BankAccountService;

@Controller
@ManagedBean(name = "bankAccountListController")
@RequestScoped
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
