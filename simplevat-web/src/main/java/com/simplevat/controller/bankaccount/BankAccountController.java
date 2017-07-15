package com.simplevat.controller.bankaccount;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.BankAccountStatus;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.BankAccountStatusService;

import lombok.Getter;
import lombok.Setter;

@Controller
@ManagedBean(name = "bankAccountController")
@RequestScoped
public class BankAccountController {

	
	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private BankAccountStatusService bankAccountStatusService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private CountryService countryService;

	@Getter
	@Setter
	private BankAccount selectedBankAccount;

	/**
	 * @return List<BankAccount>
	 * 
	 * Return list of all banks
	 */
	public List<BankAccount> populateAccountDetails() {
		List<BankAccount> banks = new ArrayList<>(0);
		try {
			banks = bankAccountService.getBankAccounts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return banks;
	}

	public String createBankAccount(){
		 this.selectedBankAccount = new BankAccount();
		 
		 Currency defaultCurrency = currencyService.getDefaultCurrency();
		 if(defaultCurrency != null){
			 this.selectedBankAccount.setBankAccountCurrency(defaultCurrency);
		 }
		 
		 Country defaultCountry = countryService.getDefaultCountry();
		 if(defaultCountry != null){
			 this.selectedBankAccount.setBankCountry(defaultCountry);
		 }
		 
		 return "/pages/secure/bankaccount/edit-bankaccount.xhtml";
	}

	public String saveBankAccount() {
		try {
			selectedBankAccount.setCreatedBy(12345);

			if (selectedBankAccount.getBankAccountStatus() != null) {
				BankAccountStatus bankAccountStatus = bankAccountStatusService
						.getBankAccountStatus(selectedBankAccount.getBankAccountStatus().getBankAccountStatusCode());
				selectedBankAccount.setBankAccountStatus(bankAccountStatus);
			}
			if (selectedBankAccount.getBankAccountCurrency() != null) {
				Currency currency = currencyService
						.getCurrency(selectedBankAccount.getBankAccountCurrency().getCurrencyCode());
				selectedBankAccount.setBankAccountCurrency(currency);
			}

			if(selectedBankAccount.getBankAccountId() == null || selectedBankAccount.getBankAccountId() == 0){
				selectedBankAccount.setCurrentBalance(selectedBankAccount.getOpeningBalance());
				BankAccountStatus bankAccountStatus = bankAccountStatusService.getBankAccountStatusByName("ACTIVE");
				selectedBankAccount.setBankAccountStatus(bankAccountStatus);
			}
			bankAccountService.update(selectedBankAccount,selectedBankAccount.getBankAccountId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BankAccount saved successfully"));
		return "/pages/secure/bankaccount/bankaccounts.xhtml";

	}

	
	public void saveAndContinueBankAccount(){
		try{
			selectedBankAccount.setCreatedBy(12345);
			
			if(selectedBankAccount.getBankAccountStatus() != null){
				BankAccountStatus bankAccountStatus = bankAccountStatusService.getBankAccountStatus(selectedBankAccount.getBankAccountStatus().getBankAccountStatusCode());
				selectedBankAccount.setBankAccountStatus(bankAccountStatus);
			}
			if(selectedBankAccount.getBankAccountCurrency() != null){
				Currency currency = currencyService.getCurrency(selectedBankAccount.getBankAccountCurrency().getCurrencyCode());
				selectedBankAccount.setBankAccountCurrency(currency);
			}
			if(selectedBankAccount.getBankAccountId() == null || selectedBankAccount.getBankAccountId() == 0){
				selectedBankAccount.setCurrentBalance(selectedBankAccount.getOpeningBalance());
				BankAccountStatus bankAccountStatus = bankAccountStatusService.getBankAccountStatusByName("ACTIVE");
				selectedBankAccount.setBankAccountStatus(bankAccountStatus);
			}
			bankAccountService.update(selectedBankAccount,selectedBankAccount.getBankAccountId());
		}catch(Exception e){
			e.printStackTrace();
		}
		

		setSelectedBankAccount(new BankAccount());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BankAccount saved successfully"));
	}
	
	public void deleteBankAccount(){
		selectedBankAccount.setDeleteFlag(true);
		bankAccountService.update(selectedBankAccount, selectedBankAccount.getBankAccountId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BankAccount deleted successfully"));

	}

	public List<BankAccountStatus> bankAccountStatuses(final String searchQuery) {
		return bankAccountStatusService.getBankAccountStatuses();
	}

}
