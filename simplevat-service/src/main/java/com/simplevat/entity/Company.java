package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "COMPANY")
@Data
public class Company {
    @Id
    @Column(name = "COMPANY_ID")
    private int companyId;
    @Basic
    @Column(name = "COMPNAY_NAME")
    private String compnayName;
    @Basic
    @Column(name = "COMPANY_REGISTRATION_NUMBER")
    private String companyRegistrationNumber;
    @Basic
    @Column(name = "COMPANY_TYPE_CODE")
    private Integer companyTypeCode;
    @Basic
    @Column(name = "VAT_REGISTRATION_NUMBER")
    private String vatRegistrationNumber;
    @Basic
    @Column(name = "COMPANY_LOGO_URL")
    private String companyLogoUrl;
    @Basic
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Basic
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Basic
    @Column(name = "WEBSITE")
    private String website;
    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE1")
    private String invoicingAddressLine1;
    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE2")
    private String invoicingAddressLine2;
    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE3")
    private String invoicingAddressLine3;
    @Basic
    @Column(name = "CITY")
    private String city;
    @Basic
    @Column(name = "STATE_REGION")
    private String stateRegion;
    @Basic
    @Column(name = "POST_ZIP_CODE")
    private String postZipCode;
    @Basic
    @Column(name = "PO_BOX_NUMBER")
    private String poBoxNumber;
    @Basic
    @Column(name = "COUNTRY_CODE")
    private Integer countryCode;
    @Basic
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Basic
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Date lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Character deleteFlag;
    @Basic
    @Column(name = "VERSION_NUMBER")
    private int versionNumber;
//    private CompanyType companyTypeByCompanyTypeCode;
//    private Country countryByCountryCode;
//    private Collection<User> usersByCompanyId;
}
