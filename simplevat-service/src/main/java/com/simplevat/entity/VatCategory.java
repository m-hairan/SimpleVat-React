package com.simplevat.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Data;

/**
 * Created by Uday on 9/28/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allVatCategory",
            query = "SELECT v FROM VatCategory v ORDER BY v.defaultFlag DESC, v.orderSequence ASC ")
})
@Entity
@Table(name = "VAT_CATEGORY")
@Data
public class VatCategory implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "vat")
    private BigDecimal vat;
 	
	@Column(name = "DEFAULT_FLAG")
    private Character defaultFlag;

    @Column(name = "ORDER_SEQUENCE")
    private Integer orderSequence;
    
}
