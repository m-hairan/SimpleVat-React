package com.simplevat.dao.impl.bankaccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import java.time.Instant;
import java.time.ZoneId;
import javax.persistence.TypedQuery;

@Repository
public class TransactionDaoImpl extends AbstractDao<Integer, Transaction> implements TransactionDao {

    @Override
    public Transaction updateOrCreateTransaction(Transaction transaction) {
        return this.update(transaction);
    }

    @Override
    public List<Object[]> getCashInData(Date startDate, Date endDate) {
        List<Object[]> cashInData = new ArrayList<>(0);
        try {
            String queryString = "select "
                    + "sum(transactionAmount) as total, CONCAT(MONTH(transactionDate),'-' , Year(transactionDate)) as month "
                    + "from Transaction "
                    + "where debitCreditFlag = 'd' and transactionDate BETWEEN :startDate AND :endDate "
                    + "group by CONCAT(MONTH(transactionDate),'-' , Year(transactionDate))";

            Query query = getEntityManager().createQuery(queryString)
                    .setParameter("startDate", startDate, TemporalType.DATE)
                    .setParameter("endDate", endDate, TemporalType.DATE);
            cashInData = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cashInData;
    }

    @Override
    public List<Object[]> getCashOutData(Date startDate, Date endDate) {
        List<Object[]> cashOutData = new ArrayList<>(0);
        try {
            String queryString = "select "
                    + "sum(transactionAmount) as total, CONCAT(MONTH(transactionDate),'-' , Year(transactionDate)) as month "
                    + "from Transaction "
                    + "where debitCreditFlag = 'c' and transactionDate BETWEEN :startDate AND :endDate "
                    + "group by CONCAT(MONTH(transactionDate),'-' , Year(transactionDate))";
            Query query = getEntityManager().createQuery(queryString)
                    .setParameter("startDate", startDate, TemporalType.DATE)
                    .setParameter("endDate", endDate, TemporalType.DATE);
            cashOutData = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cashOutData;
    }

    @Override
    public Transaction getBeforeTransaction(Transaction transaction) {
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT t FROM Transaction t WHERE t.transactionDate <= :transactionDate and t.transactionId < :transactionId and t.deleteFlag = false and t.bankAccount.bankAccountId = :bankAccountId ORDER BY t.transactionDate DESC", Transaction.class);
        query.setParameter("transactionDate", transaction.getTransactionDate());
        query.setParameter("bankAccountId", transaction.getBankAccount().getBankAccountId());
        query.setParameter("transactionId", transaction.getTransactionId());
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return query.getResultList().get(query.getResultList().size() - 1);
        }
        return null;
    }

    @Override
    public List<Transaction> getAfterTransaction(Transaction transaction) {
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT t FROM Transaction t WHERE t.transactionDate > :transactionDate and t.deleteFlag = false and t.bankAccount.bankAccountId = :bankAccountId ORDER BY t.transactionDate ASC", Transaction.class);
        query.setParameter("transactionDate", transaction.getTransactionDate());
        query.setParameter("bankAccountId", transaction.getBankAccount().getBankAccountId());
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return query.getResultList();
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByDateRangeAndTranscationTypeAndTranscationCategory(TransactionType transactionType, TransactionCategory category, Date startDate, Date lastDate) {
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT t FROM Transaction t WHERE t.deleteFlag = false and t.transactionType.transactionTypeCode =:transactionTypeCode and t.explainedTransactionCategory.transactionCategoryCode =:transactionCategoryCode and t.transactionDate BETWEEN :startDate AND :lastDate ORDER BY t.transactionDate ASC", Transaction.class);
        query.setParameter("transactionTypeCode", transactionType.getTransactionTypeCode());
        query.setParameter("transactionCategoryCode", category.getTransactionCategoryCode());
        query.setParameter("startDate", Instant.ofEpochMilli(startDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        query.setParameter("lastDate", Instant.ofEpochMilli(lastDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return query.getResultList();
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByDateRangeAndBankAccountId(BankAccount bankAccount, Date startDate, Date lastDate) {
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT t FROM Transaction t WHERE t.deleteFlag = false and t.bankAccount.bankAccountId = :bankAccountId and t.transactionDate BETWEEN :startDate AND :lastDate ORDER BY t.transactionDate ASC", Transaction.class);
        query.setParameter("bankAccountId", bankAccount.getBankAccountId());
        query.setParameter("startDate", Instant.ofEpochMilli(startDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        query.setParameter("lastDate", Instant.ofEpochMilli(lastDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return query.getResultList();
        }
        return null;
    }

    @Override
    public List<Transaction> getChildTransactionListByParentId(int parentId) {
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT t FROM Transaction t WHERE t.deleteFlag = false and t.parentTransaction.transactionId =:parentId ORDER BY t.transactionDate ASC", Transaction.class);
        query.setParameter("parentId", parentId);
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return query.getResultList();
        }
        return null;
    }

    @Override
    public List<Transaction> getAllParentTransactions(BankAccount bankAccount) {
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT t FROM Transaction t WHERE t.deleteFlag = false and t.parentTransaction = null and t.bankAccount.bankAccountId = :bankAccountId ORDER BY t.transactionDate DESC", Transaction.class);
        query.setParameter("bankAccountId", bankAccount.getBankAccountId());
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return query.getResultList();
        }
        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT t FROM Transaction t WHERE t.deleteFlag = false ORDER BY t.transactionDate ASC", Transaction.class);
        List<Transaction> transactionList = query.getResultList();
        if (transactionList != null && !transactionList.isEmpty()) {
            return transactionList;
        }
        return null;
    }

}
