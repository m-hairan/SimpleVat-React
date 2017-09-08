package com.simplevat.dao.impl.bankaccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.entity.bankaccount.Transaction;
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
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT t FROM Transaction t WHERE t.transactionDate <= :transactionDate and t.deleteFlag = false and t.bankAccount.bankAccountId = :bankAccountId ORDER BY t.transactionDate DESC", Transaction.class);
        query.setParameter("transactionDate", transaction.getTransactionDate());
        query.setParameter("bankAccountId", transaction.getBankAccount().getBankAccountId());
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return query.getResultList().get(0);
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
}
