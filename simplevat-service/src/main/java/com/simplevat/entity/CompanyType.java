package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "COMPANY_TYPE")
@Data
public class CompanyType {

    @Id
    @Column(name = "COMPANY_TYPE_CODE")
    private Integer id;
    @Basic
    @Column(name = "COMPANY_TYPE_NAME")
    private String companyTypeName;
    @Basic
    @Column(name = "COMPANY_TYPE_DESCRIPTION")
    private String companyTypeDescription;
}
