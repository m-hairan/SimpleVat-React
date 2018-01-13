/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
 * @author admin
 */
@NamedQueries({
    @NamedQuery(name = "allProductWarehouse",
            query = "SELECT w FROM ProductWarehouse w where w.deleteFlag = false order by w.warehouseName ASC")
})

@Entity
@Table(name = "PRODUCT_WAREHOUSE")
@Data
public class ProductWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "WAREHOUSE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer warehouseId;
    
    @Basic(optional = false)
    @Column(name = "WAREHOUSE_NAME")
    private String warehouseName;

    @Column(name = "DELETE_FLAG")
    @ColumnDefault(value = "0")
    private Boolean deleteFlag = Boolean.FALSE;

    @Column(name = "VERSION_NUMBER")
    @ColumnDefault(value = "1")
    @Basic(optional = false)
    @Version
    private Integer versionNumber;
}
