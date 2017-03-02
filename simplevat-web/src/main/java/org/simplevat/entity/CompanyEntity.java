package org.simplevat.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "COMPANY", schema = "simplevat", catalog = "")
public class CompanyEntity {
    private int companyId;
    private String compnayName;
    private String companyRegistrationNumber;
    private Integer companyTypeCode;
    private String vatRegistrationNumber;
    private String companyLogoUrl;
    private String emailAddress;
    private String phoneNumber;
    private String website;
    private String invoicingAddressLine1;
    private String invoicingAddressLine2;
    private String invoicingAddressLine3;
    private String city;
    private String stateRegion;
    private String postZipCode;
    private String poBoxNumber;
    private Integer countryCode;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private CompanyTypeEntity companyTypeByCompanyTypeCode;
    private CountryEntity countryByCountryCode;
    private Collection<UserEntity> usersByCompanyId;

    @Id
    @Column(name = "COMPANY_ID")
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "COMPNAY_NAME")
    public String getCompnayName() {
        return compnayName;
    }

    public void setCompnayName(String compnayName) {
        this.compnayName = compnayName;
    }

    @Basic
    @Column(name = "COMPANY_REGISTRATION_NUMBER")
    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    @Basic
    @Column(name = "COMPANY_TYPE_CODE")
    public Integer getCompanyTypeCode() {
        return companyTypeCode;
    }

    public void setCompanyTypeCode(Integer companyTypeCode) {
        this.companyTypeCode = companyTypeCode;
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
    @Column(name = "COMPANY_LOGO_URL")
    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }

    @Basic
    @Column(name = "EMAIL_ADDRESS")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "PHONE_NUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "WEBSITE")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

        CompanyEntity that = (CompanyEntity) o;

        if (companyId != that.companyId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (compnayName != null ? !compnayName.equals(that.compnayName) : that.compnayName != null) return false;
        if (companyRegistrationNumber != null ? !companyRegistrationNumber.equals(that.companyRegistrationNumber) : that.companyRegistrationNumber != null)
            return false;
        if (companyTypeCode != null ? !companyTypeCode.equals(that.companyTypeCode) : that.companyTypeCode != null)
            return false;
        if (vatRegistrationNumber != null ? !vatRegistrationNumber.equals(that.vatRegistrationNumber) : that.vatRegistrationNumber != null)
            return false;
        if (companyLogoUrl != null ? !companyLogoUrl.equals(that.companyLogoUrl) : that.companyLogoUrl != null)
            return false;
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;
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
        int result = companyId;
        result = 31 * result + (compnayName != null ? compnayName.hashCode() : 0);
        result = 31 * result + (companyRegistrationNumber != null ? companyRegistrationNumber.hashCode() : 0);
        result = 31 * result + (companyTypeCode != null ? companyTypeCode.hashCode() : 0);
        result = 31 * result + (vatRegistrationNumber != null ? vatRegistrationNumber.hashCode() : 0);
        result = 31 * result + (companyLogoUrl != null ? companyLogoUrl.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (invoicingAddressLine1 != null ? invoicingAddressLine1.hashCode() : 0);
        result = 31 * result + (invoicingAddressLine2 != null ? invoicingAddressLine2.hashCode() : 0);
        result = 31 * result + (invoicingAddressLine3 != null ? invoicingAddressLine3.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateRegion != null ? stateRegion.hashCode() : 0);
        result = 31 * result + (postZipCode != null ? postZipCode.hashCode() : 0);
        result = 31 * result + (poBoxNumber != null ? poBoxNumber.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "COMPANY_TYPE_CODE", referencedColumnName = "COMPANY_TYPE_CODE")
    public CompanyTypeEntity getCompanyTypeByCompanyTypeCode() {
        return companyTypeByCompanyTypeCode;
    }

    public void setCompanyTypeByCompanyTypeCode(CompanyTypeEntity companyTypeByCompanyTypeCode) {
        this.companyTypeByCompanyTypeCode = companyTypeByCompanyTypeCode;
    }

    @ManyToOne
    @JoinColumn(name = "COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE")
    public CountryEntity getCountryByCountryCode() {
        return countryByCountryCode;
    }

    public void setCountryByCountryCode(CountryEntity countryByCountryCode) {
        this.countryByCountryCode = countryByCountryCode;
    }

    @OneToMany(mappedBy = "companyByCompanyId")
    public Collection<UserEntity> getUsersByCompanyId() {
        return usersByCompanyId;
    }

    public void setUsersByCompanyId(Collection<UserEntity> usersByCompanyId) {
        this.usersByCompanyId = usersByCompanyId;
    }
}
