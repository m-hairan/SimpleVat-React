package com.simplevat.test.dao;

import com.simplevat.entity.Country;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

import java.util.List;

/**
 * Created by mohsinh on 3/10/2017.
 */
public class TestCountryDao extends BaseManagerTest {

    @Test
    public void testGetCountries()
    {
        List<Country> countries = this.countryDao.getContries();

        System.out.println("Size :"+ countries.size());
    }
}
