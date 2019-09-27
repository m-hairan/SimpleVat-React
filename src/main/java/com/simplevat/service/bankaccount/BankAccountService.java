package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.SimpleVatService;

public abstract class BankAccountService extends SimpleVatService<Integer, BankAccount> {

    public abstract List<BankAccount> getBankAccounts();

    public abstract List<BankAccount> getBankAccountByUser(int userId);

    public abstract BankAccount getBankAccountById(int id);

}
