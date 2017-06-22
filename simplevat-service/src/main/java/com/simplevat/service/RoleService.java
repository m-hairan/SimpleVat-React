package com.simplevat.service;

import com.simplevat.entity.Role;
import java.util.List;

/**
 *
 * @author Uday
 */
public interface RoleService {

    public List<Role> getRoles();

    public Role getRoleById(Integer roleCode);
}
