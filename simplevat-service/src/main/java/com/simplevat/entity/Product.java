package com.simplevat.entity;

import com.simplevat.entity.converter.DateConverter;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.ColumnDefault;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allProduct",
            query = "SELECT p FROM Product p  where p.deleteFlag = FALSE ")
})
@Entity
@Table(name = "PRODUCT")
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Integer productID;
    @Basic
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Basic
    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VAT")
    private VatCategory vatCategory;
    @Basic
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Column(name = "CREATED_BY")
    @Basic(optional = false)
    private Integer createdBy;
    @Column(name = "CREATED_DATE")
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @Basic(optional = false)
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;
    @Column(name = "DELETE_FLAG")
    @ColumnDefault(value = "0")
    private Boolean deleteFlag = Boolean.FALSE;
    @Column(name = "VERSION_NUMBER")
    @ColumnDefault(value = "1")
    @Version
    private Integer versionNumber = 1;
}
