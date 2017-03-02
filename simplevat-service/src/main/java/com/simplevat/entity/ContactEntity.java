package com.simplevat.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "CONTACT", schema = "simplevat", catalog = "")
public class ContactEntity {
    private int contactId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String organization;
    private String email;
    private String billingEmail;
    private String telephone;
    private String mobileNumber;
    private String invoicingAddressLine1;
    private String invoicingAddressLine2;
    private String invoicingAddressLine3;
    private String city;
    private String stateRegion;
    private String postZipCode;
    private String poBoxNumber;
    private Integer countryCode;
    private String contractPoNumber;
    private String vatRegistrationNumber;
    private Integer invoiceLanguageCode;
    private Integer currencyCode;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private CountryEntity countryByCountryCode;
    private LanguageEntity languageByInvoiceLanguageCode;
    private CurrencyEntity currencyByCurrencyCode;
    private Collection<InvoiceEntity> invoicesByContactId;
    private Collection<ProjectEntity> projectsByContactId;

    @Id
    @Column(name = "CONTACT_ID")
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "MIDDLE_NAME")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "ORGANIZATION")
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "BILLING_EMAIL")
    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    @Basic
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "MOBILE_NUMBER")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE1")
    public String getInvoicingAddressLine1() {
        return invoicingAddressLine1;
    }

    public void setInvoicingAddressLine1(String invoicingAddressLine1) {
        this.invoicingAddressLine1 = invoicingAddressLine1;
    }

    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE2")
    public String getInvoicingAddressLine2() {
        return invoicingAddressLine2;
    }

    public void setInvoicingAddressLine2(String invoicingAddressLine2) {
        this.invoicingAddressLine2 = invoicingAddressLine2;
    }

    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE3")
    public String getInvoicingAddressLine3() {
        return invoicingAddressLine3;
    }

    public void setInvoicingAddressLine3(String invoicingAddressLine3) {
        this.invoicingAddressLine3 = invoicingAddressLine3;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "STATE_REGION")
    public String getStateRegion() {
        return stateRegion;
    }

    public void setStateRegion(String stateRegion) {
        this.stateRegion = stateRegion;
    }

    @Basic
    @Column(name = "POST_ZIP_CODE")
    public String getPostZipCode() {
        return postZipCode;
    }

    public void setPostZipCode(String postZipCode) {
        this.postZipCode = postZipCode;
    }

    @Basic
    @Column(name = "PO_BOX_NUMBER")
    public String getPoBoxNumber() {
        return poBoxNumber;
    }

    public void setPoBoxNumber(String poBoxNumber) {
        this.poBoxNumber = poBoxNumber;
    }

    @Basic
    @Column(name = "COUNTRY_CODE")
    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "CONTRACT_PO_NUMBER")
    public String getContractPoNumber() {
        return contractPoNumber;
    }

    public void setContractPoNumber(String contractPoNumber) {
        this.contractPoNumber = contractPoNumber;
    }

    @Basic
    @Column(name = "VAT_REGISTRATION_NUMBER")
    public String getVatRegistrationNumber() {
        return vatRegistrationNumber;
    }

    public void setVatRegistrationNumber(String vatRegistrationNumber) {
        this.vatRegistrationNumber = vatRegistrationNumber;
    }

    @Basic
    @Column(name = "INVOICE_LANGUAGE_CODE")
    public Integer getInvoiceLanguageCode() {
        return invoiceLanguageCode;
    }

    public void setInvoiceLanguageCode(Integer invoiceLanguageCode) {
        this.invoiceLanguageCode = invoiceLanguageCode;
    }

    @Basic
    @Column(name = "CURRENCY_CODE")
    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
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

        ContactEntity that = (ContactEntity) o;

        if (contactId != that.contactId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (organization != null ? !organization.equals(that.organization) : that.organization != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (billingEmail != null ? !billingEmail.equals(that.billingEmail) : that.billingEmail != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (mobileNumber != null ? !mobileNumber.equals(that.mobileNumber) : that.mobileNumber != null) return false;
        if (invoicingAddressLine1 != null ? !invoicingAddressLine1.equals(that.invoicingAddressLine1) : that.invoicingAddressLine1 != null)
            return false;
        if (invoicingAddressLine2 != null ? !invoicingAddressLine2.equals(that.invoicingAddressLine2) : that.invoicingAddressLine2 != null)
            return false;
        if (invoicingAddressLine3 != null ? !invoicingAddressLine3.equals(that.invoicingAddressLine3) : that.invoicingAddressLine3 != null)
            return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (stateRegion != null ? !stateRegion.equals(that.stateRegion) : that.stateRegion != null) return false;
        if (postZipCode != null ? !postZipCode.equals(that.postZipCode) : that.postZipCode != null) return false;
        if (poBoxNumber != null ? !poBoxNumber.equals(that.poBoxNumber) : that.poBoxNumber != null) return false;
        if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) return false;
        if (contractPoNumber != null ? !contractPoNumber.equals(that.contractPoNumber) : that.contractPoNumber != null)
            return false;
        if (vatRegistrationNumber != null ? !vatRegistrationNumber.equals(that.vatRegistrationNumber) : that.vatRegistrationNumber != null)
            return false;
        if (invoiceLanguageCode != null ? !invoiceLanguageCode.equals(that.invoiceLanguageCode) : that.invoiceLanguageCode != null)
            return false;
        if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) return false;
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
        int result = contactId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (billingEmail != null ? billingEmail.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        result = 31 * result + (invoicingAddressLine1 != null ? invoicingAddressLine1.hashCode() : 0);
        result = 31 * result + (invoicingAddressLine2 != null ? invoicingAddressLine2.hashCode() : 0);
        result = 31 * result + (invoicingAddressLine3 != null ? invoicingAddressLine3.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateRegion != null ? stateRegion.hashCode() : 0);
        result = 31 * result + (postZipCode != null ? postZipCode.hashCode() : 0);
        result = 31 * result + (poBoxNumber != null ? poBoxNumber.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (contractPoNumber != null ? contractPoNumber.hashCode() : 0);
        result = 31 * result + (vatRegistrationNumber != null ? vatRegistrationNumber.hashCode() : 0);
        result = 31 * result + (invoiceLanguageCode != null ? invoiceLanguageCode.hashCode() : 0);
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE")
    public CountryEntity getCountryByCountryCode() {
        return countryByCountryCode;
    }

    public void setCountryByCountryCode(CountryEntity countryByCountryCode) {
        this.countryByCountryCode = countryByCountryCode;
    }

    @ManyToOne
    @JoinColumn(name = "INVOICE_LANGUAGE_CODE", referencedColumnName = "LANGUAGE_CODE")
    public LanguageEntity getLanguageByInvoiceLanguageCode() {
        return languageByInvoiceLanguageCode;
    }

    public void setLanguageByInvoiceLanguageCode(LanguageEntity languageByInvoiceLanguageCode) {
        this.languageByInvoiceLanguageCode = languageByInvoiceLanguageCode;
    }

    @ManyToOne
    @JoinColumn(name = "CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE")
    public CurrencyEntity getCurrencyByCurrencyCode() {
        return currencyByCurrencyCode;
    }

    public void setCurrencyByCurrencyCode(CurrencyEntity currencyByCurrencyCode) {
        this.currencyByCurrencyCode = currencyByCurrencyCode;
    }

    @OneToMany(mappedBy = "contactByContactId")
    public Collection<InvoiceEntity> getInvoicesByContactId() {
        return invoicesByContactId;
    }

    public void setInvoicesByContactId(Collection<InvoiceEntity> invoicesByContactId) {
        this.invoicesByContactId = invoicesByContactId;
    }

    @OneToMany(mappedBy = "contactByContactId")
    public Collection<ProjectEntity> getProjectsByContactId() {
        return projectsByContactId;
    }

    public void setProjectsByContactId(Collection<ProjectEntity> projectsByContactId) {
        this.projectsByContactId = projectsByContactId;
    }
}
