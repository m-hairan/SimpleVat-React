package com.simplevat.entity;

import com.simplevat.entity.converter.DateConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "COMPANY")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    private Integer companyId;
    @Basic
    @Column(name = "COMPNAY_NAME")
    private String companyName;
    @Basic
    @Column(name = "COMPANY_REGISTRATION_NUMBER")
    private String companyRegistrationNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_TYPE_CODE")
    private CompanyType companyTypeCode;
    @Basic
    @Column(name = "VAT_REGISTRATION_NUMBER")
    private String vatRegistrationNumber;
    @Basic
    @Lob
    @Column(name = "COMPANY_LOGO")
    private byte[] companyLogo;
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
    @Column(name = "INVOICING_CITY")
    private String invoicingCity;
    @Basic
    @Column(name = "INVOICING_STATE_REGION")
    private String invoicingStateRegion;
    @Basic
    @Column(name = "INVOICING_POST_ZIP_CODE")
    private String invoicingPostZipCode;
    @Basic
    @Column(name = "INVOICING_PO_BOX_NUMBER")
    private String invoicingPoBoxNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICING_COUNTRY_CODE")
    private Country invoicingCountryCode;
    @Basic
    @Column(name = "COMPANY_ADDRESS_LINE1")
    private String companyAddressLine1;
    @Basic
    @Column(name = "COMPANY_ADDRESS_LINE2")
    private String companyAddressLine2;
    @Basic
    @Column(name = "COMPANY_ADDRESS_LINE3")
    private String companyAddressLine3;
    @Basic
    @Column(name = "COMPANY_CITY")
    private String companyCity;
    @Basic
    @Column(name = "COMPANY_STATE_REGION")
    private String companyStateRegion;
    @Basic
    @Column(name = "COMPANY_POST_ZIP_CODE")
    private String companyPostZipCode;
    @Basic
    @Column(name = "COMPANY_PO_BOX_NUMBER")
    private String companyPoBoxNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_COUNTRY_CODE")
    private Country companyCountryCode;
    @Basic
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Basic
    @Column(name = "CREATED_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag = Boolean.FALSE;
    @Basic
    @Version
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber;
//    private CompanyType companyTypeByCompanyTypeCode;
//    private Country countryByCountryCode;
//    private Collection<User> usersByCompanyId;
}
