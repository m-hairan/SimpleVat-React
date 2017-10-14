package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by mohsinh on 2/26/2017.
 */

@NamedQueries({
    @NamedQuery(name = "Role.FindAllRole",
            query = "SELECT r FROM Role r ORDER BY r.defaultFlag DESC, r.orderSequence ASC ")
})
@Entity
@Table(name = "ROLE")
@Data
public class Role {

    @Id
    @Column(name = "ROLE_CODE")
    private Integer roleCode;

    @Basic
    @Column(name = "ROLE_NAME")
    private String roleName;

    @Basic
    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;

    @Column(name = "DEFAULT_FLAG")
    private Character defaultFlag;

    @Column(name = "ORDER_SEQUENCE")
    private Integer orderSequence;
    

}
