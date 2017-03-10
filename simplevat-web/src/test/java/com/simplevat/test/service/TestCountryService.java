package com.simplevat.test.service;

import com.simplevat.entity.Country;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mohsinh on 3/10/2017.
 */
public class TestCountryService extends BaseManagerTest {

    @Test
    public void testGetCountries()
    {
        List<Country> countries = this.countryService.getCountries();

        Iterator<Country> countryIterator = countries.iterator();


        while (countryIterator.hasNext())
        {
            Country country = countryIterator.next();

            System.out.println(" Code :"+country.getIsoAlpha3Code());

         }


        System.out.println(" Size :"+countries.size());
    }


}
