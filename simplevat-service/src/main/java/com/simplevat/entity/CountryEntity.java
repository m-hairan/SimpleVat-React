package com.simplevat.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "COUNTRY", schema = "simplevat", catalog = "")
public class CountryEntity {
    private int countryCode;
    private String countryName;
    private String countryDescription;
    private String isoAlpha3Code;
    private Collection<BankAccountEntity> bankAccountsByCountryCode;
    private Collection<CompanyEntity> companiesByCountryCode;
    private Collection<ContactEntity> contactsByCountryCode;

    @Id
    @Column(name = "COUNTRY_CODE")
    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "COUNTRY_NAME")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "COUNTRY_DESCRIPTION")
    public String getCountryDescription() {
        return countryDescription;
    }

    public void setCountryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
    }

    @Basic
    @Column(name = "ISO_ALPHA3_CODE")
    public String getIsoAlpha3Code() {
        return isoAlpha3Code;
    }

    public void setIsoAlpha3Code(String isoAlpha3Code) {
        this.isoAlpha3Code = isoAlpha3Code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (countryCode != that.countryCode) return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;
        if (countryDescription != null ? !countryDescription.equals(that.countryDescription) : that.countryDescription != null)
            return false;
        if (isoAlpha3Code != null ? !isoAlpha3Code.equals(that.isoAlpha3Code) : that.isoAlpha3Code != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryCode;
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        result = 31 * result + (countryDescription != null ? countryDescription.hashCode() : 0);
        result = 31 * result + (isoAlpha3Code != null ? isoAlpha3Code.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "countryByBankCountryCode")
    public Collection<BankAccountEntity> getBankAccountsByCountryCode() {
        return bankAccountsByCountryCode;
    }

    public void setBankAccountsByCountryCode(Collection<BankAccountEntity> bankAccountsByCountryCode) {
        this.bankAccountsByCountryCode = bankAccountsByCountryCode;
    }

    @OneToMany(mappedBy = "countryByCountryCode")
    public Collection<CompanyEntity> getCompaniesByCountryCode() {
        return companiesByCountryCode;
    }

    public void setCompaniesByCountryCode(Collection<CompanyEntity> companiesByCountryCode) {
        this.companiesByCountryCode = companiesByCountryCode;
    }

    @OneToMany(mappedBy = "countryByCountryCode")
    public Collection<ContactEntity> getContactsByCountryCode() {
        return contactsByCountryCode;
    }

    public void setContactsByCountryCode(Collection<ContactEntity> contactsByCountryCode) {
        this.contactsByCountryCode = contactsByCountryCode;
    }
}
