package com.simplevat.test.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.test.common.BaseManagerTest;

/**
 * @author Ankit
 *
 */
public class TestBankAccountService extends BaseManagerTest{
	
	@Autowired
	BankAccountService bankAccountService;
	
	
	//@Test
	public void getAllBankAccounts(){
		List<BankAccount> accounts = bankAccountService.getBankAccounts();
		System.out.println(accounts.size());
	}
	
	@Test
	public void getAccountByUser(){
		
		try{
		List<BankAccount> accounts = bankAccountService.getBankAccountByUser(2);
		System.out.println("ACCOUNT SIZE ARE ::::::::::::::::::::::::::::::: "+accounts.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
