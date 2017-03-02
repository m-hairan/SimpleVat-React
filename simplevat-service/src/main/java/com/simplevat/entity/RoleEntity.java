package com.simplevat.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "ROLE", schema = "simplevat", catalog = "")
public class RoleEntity {
    private int roleCode;
    private String roleName;
    private String roleDescription;
    private Collection<UserEntity> usersByRoleCode;

    @Id
    @Column(name = "ROLE_CODE")
    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    @Basic
    @Column(name = "ROLE_NAME")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "ROLE_DESCRIPTION")
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity that = (RoleEntity) o;

        if (roleCode != that.roleCode) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;
        if (roleDescription != null ? !roleDescription.equals(that.roleDescription) : that.roleDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleCode;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (roleDescription != null ? roleDescription.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "roleByRoleCode")
    public Collection<UserEntity> getUsersByRoleCode() {
        return usersByRoleCode;
    }

    public void setUsersByRoleCode(Collection<UserEntity> usersByRoleCode) {
        this.usersByRoleCode = usersByRoleCode;
    }
}
