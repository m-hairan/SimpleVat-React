package com.simplevat.entity.invoice;

import com.simplevat.entity.converter.DateConverter;
import java.io.Serializable;
import java.time.LocalDateTime;
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

/**
 *
 * @author Hiren
 */
@Data
@Entity
@Table(name = "DISCOUNT_TYPE")
@NamedQueries({
    @NamedQuery(name = "DiscountType.discountTypes",
            query = "from DiscountType dt where dt.deleteFlag = 'N' order by dt.defaultFlag DESC, dt.orderSequence ASC")
})
public class DiscountType implements Serializable {

    private static final long serialVersionUID = -611337212691841460L;

    @Id
    @Column(name = "DISCOUNT_TYPE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer discountTypeCode;

    @Column(name = "DISCOUNT_TYPE_NAME")
    private String discountTypeName;

    @Column(name = "DISCOUNT_TYPE_DESCRIPTION")
    private String discountTypeDesc;

    @Column(name = "DEFAULT_FLAG")
    private Character defaultFlag;

    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag;

    @Column(name = "ORDER_SEQUENCE")
    private Integer orderSequence;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;

    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;

    @Column(name = "CREATED_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;

    @Version
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber = 0;

}
