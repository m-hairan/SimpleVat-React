package com.simplevat.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allCurrencies",
            query = "SELECT c "
            + "FROM Currency c ORDER BY c.defaultFlag DESC, c.orderSequence,c.currencyName ASC ")
})

@Entity
@Table(name = "CURRENCY")
@Data
public class Currency implements Serializable{

    @Id
    @Column(name = "CURRENCY_CODE")
    private Integer currencyCode;
    
    @Basic(optional = false)
    @Column(name = "CURRENCY_NAME")
    private String currencyName;
    
    @Basic
    @Column(name = "CURRENCY_DESCRIPTION")
    private String currencyDescription;
    
    @Basic
    @Column(name = "CURRENCY_ISO_CODE", length = 3)
    private String currencyIsoCode;
    
    @Basic
    @Column(name = "CURRENCY_SYMBOL")
    private String currencySymbol;

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
