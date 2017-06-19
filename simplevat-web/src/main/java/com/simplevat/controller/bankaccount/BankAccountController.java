package com.simplevat.controller.bankaccount;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

	public String createBankAccount() {
		this.selectedBankAccount = new BankAccount();

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

			bankAccountService.createOrUpdateBankAccount(selectedBankAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pages/secure/bankaccount/bankaccounts.xhtml";

	}

	public String saveAndContinueBankAccount() {
		selectedBankAccount.setCreatedBy(12345);
		bankAccountService.createOrUpdateBankAccount(selectedBankAccount);

		setSelectedBankAccount(new BankAccount());
		return "/pages/secure/bankaccount/edit-bankaccount.xhtml?faces-redirect=true";
	}

	public String deleteBankAccount() {
		selectedBankAccount.setDeleteFlag(true);
		bankAccountService.createOrUpdateBankAccount(selectedBankAccount);

		return "/pages/secure/bankaccount/bankaccounts.xhtml?faces-redirect=true";

	}

	public List<BankAccountStatus> bankAccountStatuses(final String searchQuery) {
		return bankAccountStatusService.getBankAccountStatuses();
	}

}
