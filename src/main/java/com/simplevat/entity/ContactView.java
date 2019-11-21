/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "CONTACTVIEW")
@Data
public class ContactView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CONTACT_ID")
    private int contactId;
    @Column(name = "CONTACT_TYPE")
    private Integer contactType;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "ORGANIZATION")
    private String organization;
    @Column(name = "CURRENCY_SYMBOL")
    private String currencySymbol;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "TELEPHONE")
    private String telephone;
    @Column(name = "NEXT_DUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextDueDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DUE_AMOUNT")
    private BigDecimal dueAmount;

    public ContactView() {
    }
}
