package com.simplevat.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "BANK_FEED_STATUS", schema = "simplevat", catalog = "")
public class BankFeedStatusEntity {
    private int bankFeedStatusCode;
    private String bankFeedStatusName;
    private String bankFeedStatusDescription;
    private Collection<BankAccountEntity> bankAccountsByBankFeedStatusCode;

    @Id
    @Column(name = "BANK_FEED_STATUS_CODE")
    public int getBankFeedStatusCode() {
        return bankFeedStatusCode;
    }

    public void setBankFeedStatusCode(int bankFeedStatusCode) {
        this.bankFeedStatusCode = bankFeedStatusCode;
    }

    @Basic
    @Column(name = "BANK_FEED_STATUS_NAME")
    public String getBankFeedStatusName() {
        return bankFeedStatusName;
    }

    public void setBankFeedStatusName(String bankFeedStatusName) {
        this.bankFeedStatusName = bankFeedStatusName;
    }

    @Basic
    @Column(name = "BANK_FEED_STATUS_DESCRIPTION")
    public String getBankFeedStatusDescription() {
        return bankFeedStatusDescription;
    }

    public void setBankFeedStatusDescription(String bankFeedStatusDescription) {
        this.bankFeedStatusDescription = bankFeedStatusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankFeedStatusEntity that = (BankFeedStatusEntity) o;

        if (bankFeedStatusCode != that.bankFeedStatusCode) return false;
        if (bankFeedStatusName != null ? !bankFeedStatusName.equals(that.bankFeedStatusName) : that.bankFeedStatusName != null)
            return false;
        if (bankFeedStatusDescription != null ? !bankFeedStatusDescription.equals(that.bankFeedStatusDescription) : that.bankFeedStatusDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bankFeedStatusCode;
        result = 31 * result + (bankFeedStatusName != null ? bankFeedStatusName.hashCode() : 0);
        result = 31 * result + (bankFeedStatusDescription != null ? bankFeedStatusDescription.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "bankFeedStatusByBankFeedStatusCode")
    public Collection<BankAccountEntity> getBankAccountsByBankFeedStatusCode() {
        return bankAccountsByBankFeedStatusCode;
    }

    public void setBankAccountsByBankFeedStatusCode(Collection<BankAccountEntity> bankAccountsByBankFeedStatusCode) {
        this.bankAccountsByBankFeedStatusCode = bankAccountsByBankFeedStatusCode;
    }
}
