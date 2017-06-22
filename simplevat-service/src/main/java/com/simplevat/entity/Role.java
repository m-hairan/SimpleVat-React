package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by mohsinh on 2/26/2017.
 */

@NamedQueries({
    @NamedQuery(name = "Role.FindAllRole",
            query = "SELECT r FROM Role r")
})
@Entity
@Table(name = "ROLE")
@Data
public class Role {

    @Id
    @Column(name = "ROLE_CODE")
    private int roleCode;

    @Basic
    @Column(name = "ROLE_NAME")
    private String roleName;

    @Basic
    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;

//    @Basic
//    private Collection<User> usersByRoleCode;

}
