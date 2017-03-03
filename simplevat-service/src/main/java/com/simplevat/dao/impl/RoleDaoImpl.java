package com.simplevat.dao.impl;

import com.simplevat.dao.RoleDao;
import com.simplevat.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;


    public List<Role> getRoles() {
        return null;
    }

    public Role getRoleById(Integer roleCode) {
        return entityManager.find(Role.class,roleCode);
    }
}
