package com.simplevat.service;

import com.simplevat.entity.Role;
import java.util.List;

/**
 *
 * @author Uday
 */
public interface RoleService extends SimpleVatService<Integer,Role> {

    public List<Role> getRoles();

    public Role getRoleById(Integer roleCode);
}
