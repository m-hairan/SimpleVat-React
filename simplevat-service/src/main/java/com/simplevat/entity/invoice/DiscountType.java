package com.simplevat.entity.invoice;

import com.simplevat.entity.converter.DateConverter;
import java.io.Serializable;
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
import org.hibernate.annotations.ColumnDefault;

/**
 *
 * @author Hiren
 */
@Data
@Entity
@Table(name = "DISCOUNT_TYPE")
@NamedQueries({
    @NamedQuery(name = "DiscountType.discountTypes",query = "from DiscountType dt where dt.deleteFlag = false order by dt.defaultFlag DESC, dt.orderSequence,dt.discountTypeName ASC")
})
public class DiscountType implements Serializable {

    private static final long serialVersionUID = -611337212691841460L;

    @Id
    @Column(name = "DISCOUNT_TYPE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer discountTypeCode;

    @Basic(optional = false)
    @Column(name = "DISCOUNT_TYPE_NAME")
    private String discountTypeName;

    @Column(name = "DISCOUNT_TYPE_DESCRIPTION")
    private String discountTypeDesc;

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
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;

    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdateBy;

    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;

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
