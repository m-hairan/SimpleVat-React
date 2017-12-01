/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.company.controller;

import com.simplevat.entity.CompanyType;
import com.simplevat.entity.Country;
import com.simplevat.entity.IndustryType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author uday
 */
@Getter
@Setter
public class CompanyModel {

    private int companyId;
    private String companyName;
    private String companyRegistrationNumber;
    private CompanyType companyTypeCode;
    private IndustryType industryTypeCode;
    private String vatRegistrationNumber;
    private byte[] companyLogo;
    private String emailAddress;
    private String phoneNumber;
    private String website;
    private String invoicingReferencePattern;
    private String invoicingAddressLine1;
    private String invoicingAddressLine2;
    private String invoicingAddressLine3;
    private String invoicingCity;
    private String invoicingStateRegion;
    private String invoicingPostZipCode;
    private String invoicingPoBoxNumber;
    private Country invoicingCountryCode;
    private String companyAddressLine1;
    private String companyAddressLine2;
    private String companyAddressLine3;
    private String companyCity;
    private String companyStateRegion;
    private String companyPostZipCode;
    private String companyPoBoxNumber;
    private Country companyCountryCode;
    private BigDecimal companyExpenseBudget;
    private BigDecimal companyRevenueBudget;
    private Integer createdBy;
    private LocalDateTime createdDate;
    private Integer lastUpdatedBy;
    private LocalDateTime lastUpdateDate;
    private UploadedFile profileImage;
    private Boolean deleteFlag = Boolean.FALSE;
    private Integer versionNumber;

}
