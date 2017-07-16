package com.simplevat.test.dao;

import com.simplevat.entity.User;
import com.simplevat.test.common.BaseManagerTest;

import java.time.LocalDateTime;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author hiren
 */
public class TestUserDao extends BaseManagerTest {
    
    @Test
    @Ignore
    public void testGetRoleById() {
        User user = new User();
        user.setCreatedBy(1);
        user.setCompanyId(1);
        user.setCreatedDate(LocalDateTime.now());
        user.setDateOfBirth(LocalDateTime.now());
        user.setUserEmail("shethhiren93@gmail.com");
        user.setFirstName("Hiren");
        user.setLastName("Sheth");
        user.setLastUpdateDate(LocalDateTime.now());
        user.setRoleCode(1);
        user.setUserId(1);
        user.setVersionNumber(1);
        userDao.persist(user);
        
    }
}
