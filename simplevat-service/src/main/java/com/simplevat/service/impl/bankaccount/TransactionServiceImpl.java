package com.simplevat.service.impl.bankaccount;

import com.simplevat.constants.InvoiceStatusConstant;
import com.simplevat.constants.TransactionCreditDebitConstant;
import com.simplevat.constants.TransactionRefrenceTypeConstant;
import com.simplevat.constants.TransactionStatusConstant;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.criteria.bankaccount.TransactionFilter;
import com.simplevat.dao.bankaccount.BankAccountDao;
import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.dao.bankaccount.TransactionStatusDao;
import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.entity.bankaccount.TransactionView;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.util.ChartUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Service("transactionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TransactionServiceImpl extends TransactionService {

    @Autowired
    ChartUtil util;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private TransactionStatusDao transactionStatusDao;

    @Autowired
    private BankAccountDao bankAccountDao;

    @Override
    public List<Transaction> getTransactionsByCriteria(TransactionCriteria transactionCriteria) throws Exception {
        TransactionFilter filter = new TransactionFilter(transactionCriteria);
        return transactionDao.filter(filter);
    }

    @Override
    public Transaction updateOrCreateTransaction(Transaction transaction) {
        return transactionDao.updateOrCreateTransaction(transaction);
    }

    @Override
    public Map<Object, Number> getCashOutData() {
        List<Object[]> rows = transactionDao.getCashOutData(util.getStartDate(Calendar.YEAR, -1).getTime(), util.getEndDate().getTime());
        return util.getCashMap(rows);
    }

    @Override
    public Map<Object, Number> getCashInData() {
        List<Object[]> rows = transactionDao.getCashInData(util.getStartDate(Calendar.YEAR, -1).getTime(), util.getEndDate().getTime());
        return util.getCashMap(rows);
    }

    public int getMaxTransactionValue(Map<Object, Number> cashInMap, Map<Object, Number> cashOutMap) {
        int cashInMax = util.getMaxValue(cashInMap);
        int cashOutMax = util.getMaxValue(cashOutMap);
        if (cashInMax >= cashOutMax) {
            return cashInMax;
        } else {
            return cashOutMax;
        }
    }

    @Override
    public TransactionDao getDao() {
        return this.transactionDao;
    }

    @Override
    public void persist(Transaction transaction) {
        List<Transaction> afterTransactionList = transactionDao.getAfterTransaction(transaction);
        if (afterTransactionList == null || afterTransactionList.isEmpty()) {
            if (transaction.getDebitCreditFlag() == 'C') {
                transaction.setCurrentBalance(transaction.getBankAccount().getCurrentBalance().add(transaction.getTransactionAmount()));
            } else {
                transaction.setCurrentBalance(transaction.getBankAccount().getCurrentBalance().subtract(transaction.getTransactionAmount()));
            }
            transactionDao.persist(transaction);
            BigDecimal balanceAmount = transaction.getCurrentBalance();
            updateAccountBalance(balanceAmount, transaction);
        } else {
            BigDecimal differenceAmount = transaction.getTransactionAmount();
            if (transaction.getDebitCreditFlag() == 'D') {
                differenceAmount = differenceAmount.negate();
            }
            updateLatestTransaction(differenceAmount, transaction);

            Transaction previousTransaction = transactionDao.getBeforeTransaction(transaction);
            if (previousTransaction == null) {
                Transaction latestTransaction = afterTransactionList.get(0);
                BigDecimal balanceAmount = latestTransaction.getCurrentBalance();
                if (latestTransaction.getDebitCreditFlag() == 'C') {
                    balanceAmount = balanceAmount.subtract(latestTransaction.getTransactionAmount());
                } else {
                    balanceAmount = balanceAmount.add(latestTransaction.getTransactionAmount());
                }
                transaction.setCurrentBalance(balanceAmount);
            } else {
                BigDecimal balanceAmount = previousTransaction.getCurrentBalance();
                if (transaction.getDebitCreditFlag() == 'D') {
                    balanceAmount = balanceAmount.subtract(transaction.getTransactionAmount());
                } else {
                    balanceAmount = balanceAmount.add(transaction.getTransactionAmount());
                }
                transaction.setCurrentBalance(balanceAmount);
            }
            transactionDao.persist(transaction);

            BigDecimal balance = transaction.getBankAccount().getCurrentBalance();
            if (transaction.getDebitCreditFlag() == 'D') {
                balance = balance.subtract(transaction.getTransactionAmount());
            } else {
                balance = balance.add(transaction.getTransactionAmount());
            }
            updateAccountBalance(balance, transaction);
        }
    }

    @Override
    public Transaction update(Transaction transaction) {
        Transaction currentTransaction = transactionDao.findByPK(transaction.getTransactionId());
        BigDecimal differenceAmount = new BigDecimal(0);
        BigDecimal balanceAmount = transaction.getBankAccount().getCurrentBalance();
        if (Objects.equals(currentTransaction.getDebitCreditFlag(), transaction.getDebitCreditFlag())) {
            differenceAmount = transaction.getTransactionAmount().subtract(currentTransaction.getTransactionAmount());
        } else {
            differenceAmount = transaction.getTransactionAmount().add(currentTransaction.getTransactionAmount());
        }
        if (differenceAmount.compareTo(new BigDecimal(0)) != 0) {
            if (transaction.getDebitCreditFlag() == 'D') {
                balanceAmount = balanceAmount.subtract(differenceAmount);
                transaction.setCurrentBalance(transaction.getCurrentBalance().subtract(differenceAmount));
            } else {
                balanceAmount = balanceAmount.add(differenceAmount);
                transaction.setCurrentBalance(transaction.getCurrentBalance().add(differenceAmount));
            }
            updateLatestTransaction(differenceAmount, transaction);
            updateAccountBalance(balanceAmount, transaction);
        }
        transaction = transactionDao.update(transaction);
        return transaction;
    }

    private void updateLatestTransaction(final BigDecimal differenceAmount, Transaction transaction) {
        List<Transaction> latestTransactionList = transactionDao.getAfterTransaction(transaction);
        if (latestTransactionList != null && !latestTransactionList.isEmpty()) {
            latestTransactionList.stream().map((transaction1) -> {
                transaction1.setCurrentBalance(transaction1.getCurrentBalance().add(differenceAmount));
                return transaction1;
            }).forEachOrdered((transaction1) -> {
                transactionDao.update(transaction1);
            });
        }
    }

    private void updateAccountBalance(final BigDecimal balanceAmount, Transaction transaction) {
        BankAccount bankAccount = transaction.getBankAccount();
        bankAccount.setCurrentBalance(balanceAmount);
        bankAccountDao.update(bankAccount);
    }

    @Override
    public Transaction deleteTransaction(Transaction transaction) {
        BigDecimal balanceAmount = transaction.getBankAccount().getCurrentBalance();
        BigDecimal diffAmount = transaction.getTransactionAmount();
        if (transaction.getDebitCreditFlag() == 'D') {
            balanceAmount = balanceAmount.add(transaction.getTransactionAmount());
        } else {
            balanceAmount = balanceAmount.subtract(transaction.getTransactionAmount());
            diffAmount = diffAmount.negate();
        }
        updateOrCreateTransaction(transaction);
        updateLatestTransaction(diffAmount, transaction);
        updateAccountBalance(balanceAmount, transaction);
        return transaction;
    }

    @Override
    public Transaction deleteChildTransaction(Transaction transaction) {
        transaction.setDeleteFlag(true);
        transactionDao.delete(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsByDateRangeAndTranscationTypeAndTranscationCategory(TransactionType transactionType, TransactionCategory category, Date startDate, Date lastDate) {
        return transactionDao.getTransactionsByDateRangeAndTranscationTypeAndTranscationCategory(transactionType, category, startDate, lastDate);
    }

    @Override
    public List<Transaction> getChildTransactionListByParentId(int parentId) {
        return transactionDao.getChildTransactionListByParentId(parentId);
    }

    @Override
    public void persistChildTransaction(Transaction transaction) {
        if (transaction.getTransactionId() == null) {
            transactionDao.persist(transaction);
        } else {
            transactionDao.update(transaction);
        }
    }

    @Override
    public List<Transaction> getAllParentTransactions(BankAccount bankAccount) {
        return transactionDao.getAllParentTransactions(bankAccount);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    @Override
    public List<TransactionView> getAllTransactionViewList(Integer bankAccountId) {
        return transactionDao.getAllTransactionViewList(bankAccountId);
    }

    @Override
    public List<TransactionView> getChildTransactionViewListByParentId(Integer parentTransaction) {
        return transactionDao.getChildTransactionViewListByParentId(parentTransaction);
    }

}
