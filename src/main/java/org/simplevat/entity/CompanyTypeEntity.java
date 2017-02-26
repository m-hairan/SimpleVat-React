package org.simplevat.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "COMPANY_TYPE", schema = "simplevat", catalog = "")
public class CompanyTypeEntity {
    private int companyTypeCode;
    private String companyTypeName;
    private String companyTypeDescription;
    private Collection<CompanyEntity> companiesByCompanyTypeCode;

    @Id
    @Column(name = "COMPANY_TYPE_CODE")
    public int getCompanyTypeCode() {
        return companyTypeCode;
    }

    public void setCompanyTypeCode(int companyTypeCode) {
        this.companyTypeCode = companyTypeCode;
    }

    @Basic
    @Column(name = "COMPANY_TYPE_NAME")
    public String getCompanyTypeName() {
        return companyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }

    @Basic
    @Column(name = "COMPANY_TYPE_DESCRIPTION")
    public String getCompanyTypeDescription() {
        return companyTypeDescription;
    }

    public void setCompanyTypeDescription(String companyTypeDescription) {
        this.companyTypeDescription = companyTypeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyTypeEntity that = (CompanyTypeEntity) o;

        if (companyTypeCode != that.companyTypeCode) return false;
        if (companyTypeName != null ? !companyTypeName.equals(that.companyTypeName) : that.companyTypeName != null)
            return false;
        if (companyTypeDescription != null ? !companyTypeDescription.equals(that.companyTypeDescription) : that.companyTypeDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyTypeCode;
        result = 31 * result + (companyTypeName != null ? companyTypeName.hashCode() : 0);
        result = 31 * result + (companyTypeDescription != null ? companyTypeDescription.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "companyTypeByCompanyTypeCode")
    public Collection<CompanyEntity> getCompaniesByCompanyTypeCode() {
        return companiesByCompanyTypeCode;
    }

    public void setCompaniesByCompanyTypeCode(Collection<CompanyEntity> companiesByCompanyTypeCode) {
        this.companiesByCompanyTypeCode = companiesByCompanyTypeCode;
    }
}
