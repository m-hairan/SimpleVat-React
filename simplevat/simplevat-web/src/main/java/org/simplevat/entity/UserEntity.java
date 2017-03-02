package org.simplevat.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "USER", schema = "simplevat", catalog = "")
public class UserEntity {
    private int userEmailId;
    private String firstName;
    private String lastName;
    private Date dataOfBirth;
    private Integer companyId;
    private Integer roleCode;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private Collection<ExpenseEntity> expensesByUserEmailId;
    private CompanyEntity companyByCompanyId;
    private RoleEntity roleByRoleCode;

    @Id
    @Column(name = "USER_EMAIL_ID")
    public int getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(int userEmailId) {
        this.userEmailId = userEmailId;
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
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "DATA_OF_BIRTH")
    public Date getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(Date dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    @Basic
    @Column(name = "COMPANY_ID")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "ROLE_CODE")
    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
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

        UserEntity that = (UserEntity) o;

        if (userEmailId != that.userEmailId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (dataOfBirth != null ? !dataOfBirth.equals(that.dataOfBirth) : that.dataOfBirth != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (roleCode != null ? !roleCode.equals(that.roleCode) : that.roleCode != null) return false;
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
        int result = userEmailId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dataOfBirth != null ? dataOfBirth.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (roleCode != null ? roleCode.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @OneToMany(mappedBy = "userByClaimantId")
    public Collection<ExpenseEntity> getExpensesByUserEmailId() {
        return expensesByUserEmailId;
    }

    public void setExpensesByUserEmailId(Collection<ExpenseEntity> expensesByUserEmailId) {
        this.expensesByUserEmailId = expensesByUserEmailId;
    }

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID")
    public CompanyEntity getCompanyByCompanyId() {
        return companyByCompanyId;
    }

    public void setCompanyByCompanyId(CompanyEntity companyByCompanyId) {
        this.companyByCompanyId = companyByCompanyId;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_CODE", referencedColumnName = "ROLE_CODE")
    public RoleEntity getRoleByRoleCode() {
        return roleByRoleCode;
    }

    public void setRoleByRoleCode(RoleEntity roleByRoleCode) {
        this.roleByRoleCode = roleByRoleCode;
    }
}
