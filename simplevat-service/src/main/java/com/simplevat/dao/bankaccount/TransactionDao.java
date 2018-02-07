package com.simplevat.dao.bankaccount;

import java.util.Date;
import java.util.List;

import com.simplevat.dao.Dao;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.entity.bankaccount.TransactionView;
import java.time.LocalDateTime;

public interface TransactionDao extends Dao<Integer, Transaction> {

    Transaction updateOrCreateTransaction(Transaction transaction);

    public List<Object[]> getCashInData(Date startDate, Date endDate);

    public List<Object[]> getCashOutData(Date startDate, Date endDate);

    public Transaction getBeforeTransaction(Transaction transaction);

    public List<Transaction> getAfterTransaction(Transaction transaction);

    public List<Transaction> getTransactionsByDateRangeAndTranscationTypeAndTranscationCategory(TransactionType transactionType, TransactionCategory category, Date startDate, Date lastDate);

    public List<Transaction> getChildTransactionListByParentId(int parentId);

    public List<Transaction> getAllParentTransactions(BankAccount bankAccount);

    public List<Transaction> getTransactionsByDateRangeAndBankAccountId(BankAccount bankAccount, Date startDate, Date lastDate);

    public List<Transaction> getAllTransactionListByBankAccountId(Integer bankAccountId);

    public List<Transaction> getAllTransactions();

    public List<TransactionView> getAllTransactionViewList(Integer bankAccountId);
    
    public List<Transaction> getAllTransactionsByRefId(int transactionRefType, int transactionRefId);
    
    public List<TransactionView> getChildTransactionViewListByParentId(Integer parentTransaction);

    public List<TransactionView> getTransactionViewList(int pageSize, Integer bankAccountId, int rowCount, Integer transactionStatus);

    public Integer getTotalTransactionCountByBankAccountIdForLazyModel(Integer bankAccountId, Integer transactionStatus);

    public Integer getTotalExplainedTransactionCountByBankAccountId(Integer bankAccountId);
    
    public Integer getTotalUnexplainedTransactionCountByBankAccountId(Integer bankAccountId);
    
    public Integer getTotalPartiallyExplainedTransactionCountByBankAccountId(Integer bankAccountId);
    
    public Integer getTotalAllTransactionCountByBankAccountId(Integer bankAccountId);

    public Integer getTransactionCountByRangeAndBankAccountId(int pageSize, Integer bankAccountId, int rowCount);

    public List<Transaction> getParentTransactionListByRangeAndBankAccountId(int pageSize, Integer bankAccountId, int rowCount, Integer transactionStatus);

    public List<TransactionView> getTransactionViewListByDateRang(Integer bankAccountId, Date startDate, Date endDate);

}
