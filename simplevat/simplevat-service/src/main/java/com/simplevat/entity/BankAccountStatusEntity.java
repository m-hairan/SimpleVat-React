package com.simplevat.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "BANK_ACCOUNT_STATUS", schema = "simplevat", catalog = "")
public class BankAccountStatusEntity {
    private int bankAccountStatusCode;
    private String bankAccountStatusName;
    private String bankAccountStatusDescription;
    private Collection<BankAccountEntity> bankAccountsByBankAccountStatusCode;

    @Id
    @Column(name = "BANK_ACCOUNT_STATUS_CODE")
    public int getBankAccountStatusCode() {
        return bankAccountStatusCode;
    }

    public void setBankAccountStatusCode(int bankAccountStatusCode) {
        this.bankAccountStatusCode = bankAccountStatusCode;
    }

    @Basic
    @Column(name = "BANK_ACCOUNT_STATUS_NAME")
    public String getBankAccountStatusName() {
        return bankAccountStatusName;
    }

    public void setBankAccountStatusName(String bankAccountStatusName) {
        this.bankAccountStatusName = bankAccountStatusName;
    }

    @Basic
    @Column(name = "BANK_ACCOUNT_STATUS_DESCRIPTION")
    public String getBankAccountStatusDescription() {
        return bankAccountStatusDescription;
    }

    public void setBankAccountStatusDescription(String bankAccountStatusDescription) {
        this.bankAccountStatusDescription = bankAccountStatusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccountStatusEntity that = (BankAccountStatusEntity) o;

        if (bankAccountStatusCode != that.bankAccountStatusCode) return false;
        if (bankAccountStatusName != null ? !bankAccountStatusName.equals(that.bankAccountStatusName) : that.bankAccountStatusName != null)
            return false;
        if (bankAccountStatusDescription != null ? !bankAccountStatusDescription.equals(that.bankAccountStatusDescription) : that.bankAccountStatusDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bankAccountStatusCode;
        result = 31 * result + (bankAccountStatusName != null ? bankAccountStatusName.hashCode() : 0);
        result = 31 * result + (bankAccountStatusDescription != null ? bankAccountStatusDescription.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "bankAccountStatusByBankAccountStatusCode")
    public Collection<BankAccountEntity> getBankAccountsByBankAccountStatusCode() {
        return bankAccountsByBankAccountStatusCode;
    }

    public void setBankAccountsByBankAccountStatusCode(Collection<BankAccountEntity> bankAccountsByBankAccountStatusCode) {
        this.bankAccountsByBankAccountStatusCode = bankAccountsByBankAccountStatusCode;
    }
}
