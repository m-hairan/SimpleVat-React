package org.simplevat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "BANK_ACCOUNT", schema = "simplevat", catalog = "")
public class BankAccountEntity {
    private int bankAccountId;
    private String bankAccountName;
    private Integer bankAccountCurrencyCode;
    private Integer bankAccountStatusCode;
    private String personalCorporateAccountFlag;
    private String isprimaryAccountFlag;
    private String bankName;
    private String accountNumber;
    private String ibanNumber;
    private String swiftCode;
    private BigDecimal openingBalance;
    private BigDecimal currentBalance;
    private Integer bankFeedStatusCode;
    private Integer bankCountryCode;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private CurrencyEntity currencyByBankAccountCurrencyCode;
    private BankAccountStatusEntity bankAccountStatusByBankAccountStatusCode;
    private BankFeedStatusEntity bankFeedStatusByBankFeedStatusCode;
    private CountryEntity countryByBankCountryCode;
    private Collection<ImportedDraftTransactonEntity> importedDraftTransactonsByBankAccountId;
    private Collection<TransactonEntity> transactonsByBankAccountId;

    @Id
    @Column(name = "BANK_ACCOUNT_ID")
    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    @Basic
    @Column(name = "BANK_ACCOUNT_NAME")
    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    @Basic
    @Column(name = "BANK_ACCOUNT_CURRENCY_CODE")
    public Integer getBankAccountCurrencyCode() {
        return bankAccountCurrencyCode;
    }

    public void setBankAccountCurrencyCode(Integer bankAccountCurrencyCode) {
        this.bankAccountCurrencyCode = bankAccountCurrencyCode;
    }

    @Basic
    @Column(name = "BANK_ACCOUNT_STATUS_CODE")
    public Integer getBankAccountStatusCode() {
        return bankAccountStatusCode;
    }

    public void setBankAccountStatusCode(Integer bankAccountStatusCode) {
        this.bankAccountStatusCode = bankAccountStatusCode;
    }

    @Basic
    @Column(name = "PERSONAL_CORPORATE_ACCOUNT_FLAG")
    public String getPersonalCorporateAccountFlag() {
        return personalCorporateAccountFlag;
    }

    public void setPersonalCorporateAccountFlag(String personalCorporateAccountFlag) {
        this.personalCorporateAccountFlag = personalCorporateAccountFlag;
    }

    @Basic
    @Column(name = "ISPRIMARY_ACCOUNT_FLAG")
    public String getIsprimaryAccountFlag() {
        return isprimaryAccountFlag;
    }

    public void setIsprimaryAccountFlag(String isprimaryAccountFlag) {
        this.isprimaryAccountFlag = isprimaryAccountFlag;
    }

    @Basic
    @Column(name = "BANK_NAME")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "ACCOUNT_NUMBER")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "IBAN_NUMBER")
    public String getIbanNumber() {
        return ibanNumber;
    }

    public void setIbanNumber(String ibanNumber) {
        this.ibanNumber = ibanNumber;
    }

    @Basic
    @Column(name = "SWIFT_CODE")
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    @Basic
    @Column(name = "OPENING_BALANCE")
    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    @Basic
    @Column(name = "CURRENT_BALANCE")
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Basic
    @Column(name = "BANK_FEED_STATUS_CODE")
    public Integer getBankFeedStatusCode() {
        return bankFeedStatusCode;
    }

    public void setBankFeedStatusCode(Integer bankFeedStatusCode) {
        this.bankFeedStatusCode = bankFeedStatusCode;
    }

    @Basic
    @Column(name = "BANK_COUNTRY_CODE")
    public Integer getBankCountryCode() {
        return bankCountryCode;
    }

    public void setBankCountryCode(Integer bankCountryCode) {
        this.bankCountryCode = bankCountryCode;
    }

    @Basic
    @Column(name = "CREATED_BY")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "LAST_UPDATED_BY")
    public Date getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Date lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Basic
    @Column(name = "DELETE_FLAG")
    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Basic
    @Column(name = "VERSION_NUMBER")
    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccountEntity that = (BankAccountEntity) o;

        if (bankAccountId != that.bankAccountId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (bankAccountName != null ? !bankAccountName.equals(that.bankAccountName) : that.bankAccountName != null)
            return false;
        if (bankAccountCurrencyCode != null ? !bankAccountCurrencyCode.equals(that.bankAccountCurrencyCode) : that.bankAccountCurrencyCode != null)
            return false;
        if (bankAccountStatusCode != null ? !bankAccountStatusCode.equals(that.bankAccountStatusCode) : that.bankAccountStatusCode != null)
            return false;
        if (personalCorporateAccountFlag != null ? !personalCorporateAccountFlag.equals(that.personalCorporateAccountFlag) : that.personalCorporateAccountFlag != null)
            return false;
        if (isprimaryAccountFlag != null ? !isprimaryAccountFlag.equals(that.isprimaryAccountFlag) : that.isprimaryAccountFlag != null)
            return false;
        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (ibanNumber != null ? !ibanNumber.equals(that.ibanNumber) : that.ibanNumber != null) return false;
        if (swiftCode != null ? !swiftCode.equals(that.swiftCode) : that.swiftCode != null) return false;
        if (openingBalance != null ? !openingBalance.equals(that.openingBalance) : that.openingBalance != null)
            return false;
        if (currentBalance != null ? !currentBalance.equals(that.currentBalance) : that.currentBalance != null)
            return false;
        if (bankFeedStatusCode != null ? !bankFeedStatusCode.equals(that.bankFeedStatusCode) : that.bankFeedStatusCode != null)
            return false;
        if (bankCountryCode != null ? !bankCountryCode.equals(that.bankCountryCode) : that.bankCountryCode != null)
            return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastUpdatedBy != null ? !lastUpdatedBy.equals(that.lastUpdatedBy) : that.lastUpdatedBy != null)
            return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;
        if (deleteFlag != null ? !deleteFlag.equals(that.deleteFlag) : that.deleteFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bankAccountId;
        result = 31 * result + (bankAccountName != null ? bankAccountName.hashCode() : 0);
        result = 31 * result + (bankAccountCurrencyCode != null ? bankAccountCurrencyCode.hashCode() : 0);
        result = 31 * result + (bankAccountStatusCode != null ? bankAccountStatusCode.hashCode() : 0);
        result = 31 * result + (personalCorporateAccountFlag != null ? personalCorporateAccountFlag.hashCode() : 0);
        result = 31 * result + (isprimaryAccountFlag != null ? isprimaryAccountFlag.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (ibanNumber != null ? ibanNumber.hashCode() : 0);
        result = 31 * result + (swiftCode != null ? swiftCode.hashCode() : 0);
        result = 31 * result + (openingBalance != null ? openingBalance.hashCode() : 0);
        result = 31 * result + (currentBalance != null ? currentBalance.hashCode() : 0);
        result = 31 * result + (bankFeedStatusCode != null ? bankFeedStatusCode.hashCode() : 0);
        result = 31 * result + (bankCountryCode != null ? bankCountryCode.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE")
    public CurrencyEntity getCurrencyByBankAccountCurrencyCode() {
        return currencyByBankAccountCurrencyCode;
    }

    public void setCurrencyByBankAccountCurrencyCode(CurrencyEntity currencyByBankAccountCurrencyCode) {
        this.currencyByBankAccountCurrencyCode = currencyByBankAccountCurrencyCode;
    }

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_STATUS_CODE", referencedColumnName = "BANK_ACCOUNT_STATUS_CODE")
    public BankAccountStatusEntity getBankAccountStatusByBankAccountStatusCode() {
        return bankAccountStatusByBankAccountStatusCode;
    }

    public void setBankAccountStatusByBankAccountStatusCode(BankAccountStatusEntity bankAccountStatusByBankAccountStatusCode) {
        this.bankAccountStatusByBankAccountStatusCode = bankAccountStatusByBankAccountStatusCode;
    }

    @ManyToOne
    @JoinColumn(name = "BANK_FEED_STATUS_CODE", referencedColumnName = "BANK_FEED_STATUS_CODE")
    public BankFeedStatusEntity getBankFeedStatusByBankFeedStatusCode() {
        return bankFeedStatusByBankFeedStatusCode;
    }

    public void setBankFeedStatusByBankFeedStatusCode(BankFeedStatusEntity bankFeedStatusByBankFeedStatusCode) {
        this.bankFeedStatusByBankFeedStatusCode = bankFeedStatusByBankFeedStatusCode;
    }

    @ManyToOne
    @JoinColumn(name = "BANK_COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE")
    public CountryEntity getCountryByBankCountryCode() {
        return countryByBankCountryCode;
    }

    public void setCountryByBankCountryCode(CountryEntity countryByBankCountryCode) {
        this.countryByBankCountryCode = countryByBankCountryCode;
    }

    @OneToMany(mappedBy = "bankAccountByBankAccountId")
    public Collection<ImportedDraftTransactonEntity> getImportedDraftTransactonsByBankAccountId() {
        return importedDraftTransactonsByBankAccountId;
    }

    public void setImportedDraftTransactonsByBankAccountId(Collection<ImportedDraftTransactonEntity> importedDraftTransactonsByBankAccountId) {
        this.importedDraftTransactonsByBankAccountId = importedDraftTransactonsByBankAccountId;
    }

    @OneToMany(mappedBy = "bankAccountByBankAccountId")
    public Collection<TransactonEntity> getTransactonsByBankAccountId() {
        return transactonsByBankAccountId;
    }

    public void setTransactonsByBankAccountId(Collection<TransactonEntity> transactonsByBankAccountId) {
        this.transactonsByBankAccountId = transactonsByBankAccountId;
    }
}
