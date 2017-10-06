/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.company.controller;

import com.simplevat.entity.Company;

/**
 *
 * @author admin
 */
class CompanyHelper {

    public CompanyModel getCompanyModelFromCompany(Company company) {
        CompanyModel companyModel = new CompanyModel();
        companyModel.setCompanyAddressLine1(company.getCompanyAddressLine1());
        companyModel.setCompanyAddressLine2(company.getCompanyAddressLine2());
        companyModel.setCompanyAddressLine3(company.getCompanyAddressLine3());
        companyModel.setCompanyCity(company.getCompanyCity());
        companyModel.setCompanyCountryCode(company.getCompanyCountryCode());
        companyModel.setCompanyId(company.getCompanyId());
        companyModel.setCompanyLogo(company.getCompanyLogo());
        companyModel.setCompanyPoBoxNumber(company.getCompanyPoBoxNumber());
        companyModel.setCompanyPostZipCode(company.getCompanyPostZipCode());
        companyModel.setCompanyRegistrationNumber(company.getCompanyRegistrationNumber());
        companyModel.setCompanyStateRegion(company.getCompanyStateRegion());
        companyModel.setCompanyTypeCode(company.getCompanyTypeCode());
        companyModel.setCompanyName(company.getCompanyName());
        companyModel.setCreatedBy(company.getCreatedBy());
        companyModel.setCreatedDate(company.getCreatedDate());
        companyModel.setDeleteFlag(company.getDeleteFlag());
        companyModel.setEmailAddress(company.getEmailAddress());
        companyModel.setInvoicingReferencePattern(company.getInvoicingReferencePattern());
        companyModel.setInvoicingAddressLine1(company.getInvoicingAddressLine1());
        companyModel.setInvoicingAddressLine2(company.getInvoicingAddressLine2());
        companyModel.setInvoicingAddressLine3(company.getInvoicingAddressLine3());
        companyModel.setInvoicingCity(company.getInvoicingCity());
        companyModel.setInvoicingCountryCode(company.getInvoicingCountryCode());
        companyModel.setInvoicingPoBoxNumber(company.getInvoicingPoBoxNumber());
        companyModel.setInvoicingPostZipCode(company.getInvoicingPostZipCode());
        companyModel.setInvoicingStateRegion(company.getInvoicingStateRegion());
        companyModel.setLastUpdateDate(company.getLastUpdateDate());
        companyModel.setLastUpdatedBy(company.getLastUpdatedBy());
        companyModel.setPhoneNumber(company.getPhoneNumber());
        companyModel.setVatRegistrationNumber(company.getVatRegistrationNumber());
        companyModel.setVersionNumber(company.getVersionNumber());
        companyModel.setWebsite(company.getWebsite());
        return companyModel;
    }

    public Company getCompanyFromCompanyModel(CompanyModel companyModel) {
        Company company = new Company();
        company.setCompanyAddressLine1(companyModel.getCompanyAddressLine1());
        company.setCompanyAddressLine2(companyModel.getCompanyAddressLine2());
        company.setCompanyAddressLine3(companyModel.getCompanyAddressLine3());
        company.setCompanyCity(companyModel.getCompanyCity());
        company.setCompanyCountryCode(companyModel.getCompanyCountryCode());
        company.setCompanyId(companyModel.getCompanyId());
        company.setCompanyLogo(companyModel.getCompanyLogo());
        company.setCompanyPoBoxNumber(companyModel.getCompanyPoBoxNumber());
        company.setCompanyPostZipCode(companyModel.getCompanyPostZipCode());
        company.setCompanyRegistrationNumber(companyModel.getCompanyRegistrationNumber());
        company.setCompanyStateRegion(companyModel.getCompanyStateRegion());
        company.setCompanyTypeCode(companyModel.getCompanyTypeCode());
        company.setCompanyName(companyModel.getCompanyName());
        company.setCreatedBy(companyModel.getCreatedBy());
        company.setCreatedDate(companyModel.getCreatedDate());
        company.setDeleteFlag(companyModel.getDeleteFlag());
        company.setEmailAddress(companyModel.getEmailAddress());
        company.setInvoicingReferencePattern(companyModel.getInvoicingReferencePattern());
        company.setInvoicingAddressLine1(companyModel.getInvoicingAddressLine1());
        company.setInvoicingAddressLine2(companyModel.getInvoicingAddressLine2());
        company.setInvoicingAddressLine3(companyModel.getInvoicingAddressLine3());
        company.setInvoicingCity(companyModel.getInvoicingCity());
        company.setInvoicingCountryCode(companyModel.getInvoicingCountryCode());
        company.setInvoicingPoBoxNumber(companyModel.getInvoicingPoBoxNumber());
        company.setInvoicingPostZipCode(companyModel.getInvoicingPostZipCode());
        company.setInvoicingStateRegion(companyModel.getInvoicingStateRegion());
        company.setLastUpdateDate(companyModel.getLastUpdateDate());
        company.setLastUpdatedBy(companyModel.getLastUpdatedBy());
        company.setPhoneNumber(companyModel.getPhoneNumber());
        company.setVatRegistrationNumber(companyModel.getVatRegistrationNumber());
        company.setVersionNumber(companyModel.getVersionNumber());
        company.setWebsite(companyModel.getWebsite());
        return company;
    }

}
