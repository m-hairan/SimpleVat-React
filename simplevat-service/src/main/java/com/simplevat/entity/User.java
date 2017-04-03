package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "USER", schema = "simplevat", catalog = "")
@Data
public class User {
    @Id
    @Column(name = "USER_EMAIL_ID")
    private int userEmailId;
    @Basic
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Basic
    @Column(name = "LAST_NAME")
    private String lastName;
    @Basic
    @Column(name = "DATA_OF_BIRTH")
    private Date dataOfBirth;
    @Basic
    @Column(name = "COMPANY_ID")
    private Integer companyId;
    @Basic
    @Column(name = "ROLE_CODE")
    private Integer roleCode;
    @Basic
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Basic
    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag;
    @Basic
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber;
//    private Collection<Expense> expensesByUserEmailId;
//    private Company companyByCompanyId;
//    private Role roleByRoleCode;

}
