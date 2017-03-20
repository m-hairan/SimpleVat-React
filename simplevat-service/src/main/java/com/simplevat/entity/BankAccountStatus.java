package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "BANK_ACCOUNT_STATUS")
@Data
public class BankAccountStatus implements Serializable {
    @Id
    @Column(name = "BANK_ACCOUNT_STATUS_CODE")
    private int bankAccountStatusCode;
    @Id
    @Column(name = "BANK_ACCOUNT_STATUS_NAME")
    private String bankAccountStatusName;
    @Basic
    @Column(name = "BANK_ACCOUNT_STATUS_DESCRIPTION")
    private String bankAccountStatusDescription;
}
