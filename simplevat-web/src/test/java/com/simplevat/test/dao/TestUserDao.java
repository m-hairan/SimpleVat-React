package com.simplevat.test.dao;

import com.simplevat.entity.User;
import com.simplevat.test.common.BaseManagerTest;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;

/**
 *
 * @author hiren
 */
public class TestUserDao extends BaseManagerTest {
    
    @Test
    public void testGetRoleById() {
        User user = new User();
        user.setCreatedBy(1);
        user.setCompanyId(1);
        user.setCreatedDate(LocalDateTime.now());
        user.setDateOfBirth(LocalDateTime.now());
        user.setEmailId("shethhiren93@gmail.com");
        user.setFirstName("Hiren");
        user.setLastName("Sheth");
        user.setLastUpdateDate(LocalDateTime.now());
        user.setRoleCode(1);
        user.setUserId(1);
        user.setVersionNumber(1);
        userDao.saveUser(user);
        
    }
}
