package com.simplevat.entity.bankaccount;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

import com.simplevat.entity.converter.DateConverter;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allBankAccountStatuses",
            query = "SELECT status "
            + "FROM BankAccountStatus status where status.deleteFlag = FALSE order by status.orderSequence ASC")
})
@Entity
@Table(name = "BANK_ACCOUNT_STATUS")
@Data
public class BankAccountStatus {
	
    @Id
    @Column(name = "BANK_ACCOUNT_STATUS_CODE")
    private Integer bankAccountStatusCode;
    
    @Basic
    @Column(name = "BANK_ACCOUNT_STATUS_NAME")
    private String bankAccountStatusName;
    @Basic
    @Column(name = "BANK_ACCOUNT_STATUS_DESCRIPTION")
    private String bankAccountStatusDescription;
    
    @Basic
    @Column(name = "DEFAULT_FLAG", length = 1, columnDefinition = "CHAR")
    private String defaultFlag = "N";
    
    @Basic
    @Column(name = "ORDER_SEQUENCE")
    private int orderSequence = 1;
    
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
    private Boolean deleteFlag = false;
    @Basic
    @Version
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber;
}
