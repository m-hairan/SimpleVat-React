package com.simplevat.entity;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "BANK_ACCOUNT")
@Data
@Transactional
public class BankAccount {

    @Id
    @Column(name = "BANK_ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bankAccountId;

    @Basic
    @Column(name = "BANK_ACCOUNT_NAME")
    private String bankAccountName;
    @Basic
    @Column(name = "BANK_ACCOUNT_CURRENCY_CODE")
    private Integer bankAccountCurrencyCode;
    @Basic
    @Column(name = "BANK_ACCOUNT_STATUS_CODE")
    private Integer bankAccountStatusCode;
    @Basic
    @Column(name = "PERSONAL_CORPORATE_ACCOUNT_FLAG")
    private Character personalCorporateAccountFlag;
    @Basic
    @Column(name = "ISPRIMARY_ACCOUNT_FLAG")
    private Character isprimaryAccountFlag;
    @Basic
    @Column(name = "BANK_NAME")
    private String bankName;
    @Basic
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Basic
    @Column(name = "IBAN_NUMBER")
    private String ibanNumber;
    @Basic
    @Column(name = "SWIFT_CODE")
    private String swiftCode;
    @Basic
    @Column(name = "OPENING_BALANCE")
    private BigDecimal openingBalance;
    @Basic
    @Column(name = "CURRENT_BALANCE")
    private BigDecimal currentBalance;
    @Basic
    @Column(name = "BANK_FEED_STATUS_CODE")
    private Integer bankFeedStatusCode;
    @Basic
    @Column(name = "BANK_COUNTRY_CODE")
    private Integer bankCountryCode;
    @Basic
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Basic
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Character deleteFlag;
    @Basic
    @Column(name = "VERSION_NUMBER")
    private int versionNumber;
//    private Currency currencyByBankAccountCurrencyCode;
//    private BankAccountStatus bankAccountStatusByBankAccountStatusCode;
//    private BankFeedStatus bankFeedStatusByBankFeedStatusCode;
//    private Country countryByBankCountryCode;
//    private Collection<ImportedDraftTransacton> importedDraftTransactonsByBankAccountId;
//    private Collection<Transaction> transactonsByBankAccountId;

}
