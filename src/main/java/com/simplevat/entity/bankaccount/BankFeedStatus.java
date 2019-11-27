package com.simplevat.entity.bankaccount;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "BANK_FEED_STATUS")
@Data
public class BankFeedStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "BANK_FEED_STATUS_CODE")
    private int bankFeedStatusCode;
    
    @Basic(optional = false)
    @Column(name = "BANK_FEED_STATUS_NAME")
    private String bankFeedStatusName;
    
    @Basic
    @Column(name = "BANK_FEED_STATUS_DESCRIPTION")
    private String bankFeedStatusDescription;
    
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
