package com.simplevat.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "CURRENCY", schema = "simplevat", catalog = "")
public class CurrencyEntity {
    private int currencyCode;
    private String currencyName;
    private String currencyDescription;
    private String currencyIsoCode;
    private String currencySymbol;
    private Collection<BankAccountEntity> bankAccountsByCurrencyCode;
    private Collection<ContactEntity> contactsByCurrencyCode;
    private Collection<ExpenseEntity> expensesByCurrencyCode;
    private Collection<InvoiceEntity> invoicesByCurrencyCode;
    private Collection<ProjectEntity> projectsByCurrencyCode;

    @Id
    @Column(name = "CURRENCY_CODE")
    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Basic
    @Column(name = "CURRENCY_NAME")
    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Basic
    @Column(name = "CURRENCY_DESCRIPTION")
    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    @Basic
    @Column(name = "CURRENCY_ISO_CODE")
    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }

    @Basic
    @Column(name = "CURRENCY_SYMBOL")
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyEntity that = (CurrencyEntity) o;

        if (currencyCode != that.currencyCode) return false;
        if (currencyName != null ? !currencyName.equals(that.currencyName) : that.currencyName != null) return false;
        if (currencyDescription != null ? !currencyDescription.equals(that.currencyDescription) : that.currencyDescription != null)
            return false;
        if (currencyIsoCode != null ? !currencyIsoCode.equals(that.currencyIsoCode) : that.currencyIsoCode != null)
            return false;
        if (currencySymbol != null ? !currencySymbol.equals(that.currencySymbol) : that.currencySymbol != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currencyCode;
        result = 31 * result + (currencyName != null ? currencyName.hashCode() : 0);
        result = 31 * result + (currencyDescription != null ? currencyDescription.hashCode() : 0);
        result = 31 * result + (currencyIsoCode != null ? currencyIsoCode.hashCode() : 0);
        result = 31 * result + (currencySymbol != null ? currencySymbol.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "currencyByBankAccountCurrencyCode")
    public Collection<BankAccountEntity> getBankAccountsByCurrencyCode() {
        return bankAccountsByCurrencyCode;
    }

    public void setBankAccountsByCurrencyCode(Collection<BankAccountEntity> bankAccountsByCurrencyCode) {
        this.bankAccountsByCurrencyCode = bankAccountsByCurrencyCode;
    }

    @OneToMany(mappedBy = "currencyByCurrencyCode")
    public Collection<ContactEntity> getContactsByCurrencyCode() {
        return contactsByCurrencyCode;
    }

    public void setContactsByCurrencyCode(Collection<ContactEntity> contactsByCurrencyCode) {
        this.contactsByCurrencyCode = contactsByCurrencyCode;
    }

    @OneToMany(mappedBy = "currencyByCurrencyCode")
    public Collection<ExpenseEntity> getExpensesByCurrencyCode() {
        return expensesByCurrencyCode;
    }

    public void setExpensesByCurrencyCode(Collection<ExpenseEntity> expensesByCurrencyCode) {
        this.expensesByCurrencyCode = expensesByCurrencyCode;
    }

    @OneToMany(mappedBy = "currencyByCurrencyCode")
    public Collection<InvoiceEntity> getInvoicesByCurrencyCode() {
        return invoicesByCurrencyCode;
    }

    public void setInvoicesByCurrencyCode(Collection<InvoiceEntity> invoicesByCurrencyCode) {
        this.invoicesByCurrencyCode = invoicesByCurrencyCode;
    }

    @OneToMany(mappedBy = "currencyByCurrencyCode")
    public Collection<ProjectEntity> getProjectsByCurrencyCode() {
        return projectsByCurrencyCode;
    }

    public void setProjectsByCurrencyCode(Collection<ProjectEntity> projectsByCurrencyCode) {
        this.projectsByCurrencyCode = projectsByCurrencyCode;
    }
}
