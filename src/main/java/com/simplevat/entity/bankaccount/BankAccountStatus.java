package com.simplevat.entity.bankaccount;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.annotations.ColumnDefault;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allBankAccountStatuses",
            query = "SELECT status FROM BankAccountStatus status where status.deleteFlag = FALSE order by status.defaultFlag DESC, status.orderSequence,status.bankAccountStatusName ASC"),
    @NamedQuery(name = "findBankAccountStatusByName",
            query = "SELECT status "
            + "FROM BankAccountStatus status where status.bankAccountStatusName = :status")
})
@Entity
@Table(name = "BANK_ACCOUNT_STATUS")
@Data
public class BankAccountStatus implements Serializable{
	
    @Id
    @Column(name = "BANK_ACCOUNT_STATUS_CODE")
    private Integer bankAccountStatusCode;
    
    @Basic(optional = false)
    @Column(name = "BANK_ACCOUNT_STATUS_NAME")
    private String bankAccountStatusName;
    
    @Basic
    @Column(name = "BANK_ACCOUNT_STATUS_DESCRIPTION")
    private String bankAccountStatusDescription;
    
    @Column(name = "DEFAULT_FLAG")
    @ColumnDefault(value = "'N'")
    @Basic(optional = false)
    private Character defaultFlag;

    @Column(name = "ORDER_SEQUENCE")
    @Basic(optional = true)
    private Integer orderSequence;
    
    @Column(name = "CREATED_BY")
    @Basic(optional = false)
    private Integer createdBy;

    @Column(name = "CREATED_DATE")
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdateBy;

    @Column(name = "LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    @Column(name = "DELETE_FLAG")
    @ColumnDefault(value = "0")
    @Basic(optional = false)
    private boolean deleteFlag;

    @Column(name = "VERSION_NUMBER")
    @ColumnDefault(value = "1")
    @Basic(optional = false)
    @Version
    private Integer versionNumber;
}
