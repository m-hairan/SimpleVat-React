package com.simplevat.dao.bankaccount;

import java.util.Date;
import java.util.List;

import com.simplevat.dao.Dao;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import java.time.LocalDateTime;

public interface TransactionDao extends Dao<Integer, Transaction> {

    Transaction updateOrCreateTransaction(Transaction transaction);

    public List<Object[]> getCashInData(Date startDate, Date endDate);

    public List<Object[]> getCashOutData(Date startDate, Date endDate);

    public Transaction getBeforeTransaction(Transaction transaction);

    public List<Transaction> getAfterTransaction(Transaction transaction);

    public List<Transaction> getTransactionsByDateRangeAndBankAccountId(BankAccount bankAccount, Date startDate, Date lastDate);

}
