/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "PATCH")
@Data
public class DBpatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EXECUTION_DATE")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date executionData;

    @Basic(optional = false)
    @Column(name = "PATCH_NO", length = 255)
    private String patchNo;
}
