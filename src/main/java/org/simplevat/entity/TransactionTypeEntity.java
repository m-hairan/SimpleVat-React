package org.simplevat.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "TRANSACTION_TYPE", schema = "simplevat", catalog = "")
public class TransactionTypeEntity {
    private int transactionTypeCode;
    private String transactionTypeName;
    private String transactionTypeDescription;
    private String transactionCreditDebit;
    private Collection<ExpenseEntity> expensesByTransactionTypeCode;
    private Collection<TransactionCategoryEntity> transactionCategoriesByTransactionTypeCode;
    private Collection<TransactonEntity> transactonsByTransactionTypeCode;

    @Id
    @Column(name = "TRANSACTION_TYPE_CODE")
    public int getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(int transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    @Basic
    @Column(name = "TRANSACTION_TYPE_NAME")
    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    @Basic
    @Column(name = "TRANSACTION_TYPE_DESCRIPTION")
    public String getTransactionTypeDescription() {
        return transactionTypeDescription;
    }

    public void setTransactionTypeDescription(String transactionTypeDescription) {
        this.transactionTypeDescription = transactionTypeDescription;
    }

    @Basic
    @Column(name = "TRANSACTION_CREDIT_DEBIT")
    public String getTransactionCreditDebit() {
        return transactionCreditDebit;
    }

    public void setTransactionCreditDebit(String transactionCreditDebit) {
        this.transactionCreditDebit = transactionCreditDebit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionTypeEntity that = (TransactionTypeEntity) o;

        if (transactionTypeCode != that.transactionTypeCode) return false;
        if (transactionTypeName != null ? !transactionTypeName.equals(that.transactionTypeName) : that.transactionTypeName != null)
            return false;
        if (transactionTypeDescription != null ? !transactionTypeDescription.equals(that.transactionTypeDescription) : that.transactionTypeDescription != null)
            return false;
        if (transactionCreditDebit != null ? !transactionCreditDebit.equals(that.transactionCreditDebit) : that.transactionCreditDebit != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transactionTypeCode;
        result = 31 * result + (transactionTypeName != null ? transactionTypeName.hashCode() : 0);
        result = 31 * result + (transactionTypeDescription != null ? transactionTypeDescription.hashCode() : 0);
        result = 31 * result + (transactionCreditDebit != null ? transactionCreditDebit.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "transactionTypeByTransactionTypeCode")
    public Collection<ExpenseEntity> getExpensesByTransactionTypeCode() {
        return expensesByTransactionTypeCode;
    }

    public void setExpensesByTransactionTypeCode(Collection<ExpenseEntity> expensesByTransactionTypeCode) {
        this.expensesByTransactionTypeCode = expensesByTransactionTypeCode;
    }

    @OneToMany(mappedBy = "transactionTypeByTransactionTypeCode")
    public Collection<TransactionCategoryEntity> getTransactionCategoriesByTransactionTypeCode() {
        return transactionCategoriesByTransactionTypeCode;
    }

    public void setTransactionCategoriesByTransactionTypeCode(Collection<TransactionCategoryEntity> transactionCategoriesByTransactionTypeCode) {
        this.transactionCategoriesByTransactionTypeCode = transactionCategoriesByTransactionTypeCode;
    }

    @OneToMany(mappedBy = "transactionTypeByTransactionTypeCode")
    public Collection<TransactonEntity> getTransactonsByTransactionTypeCode() {
        return transactonsByTransactionTypeCode;
    }

    public void setTransactonsByTransactionTypeCode(Collection<TransactonEntity> transactonsByTransactionTypeCode) {
        this.transactonsByTransactionTypeCode = transactonsByTransactionTypeCode;
    }
}
