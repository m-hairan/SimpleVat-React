package com.simplevat.test.dao;

import com.simplevat.entity.Role;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

/**
 * Created by mohsin on 3/3/2017.
 */
public class TestRoleDao extends BaseManagerTest {

    @Test
    public void testGetRoleById()
    {
        Role role = roleDao.getRoleById(new Integer(1));

        System.out.println("Role Name is :"+role.getRoleName());
    }
}
