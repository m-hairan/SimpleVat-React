package com.simplevat.service;

import com.simplevat.entity.Country;
import java.util.List;

/**
 * Created by mohsinh on 3/10/2017.
 */
public interface CountryService extends SimpleVatService<Integer, Country> {

    List<Country> getCountries();
    
    Country getCountry(Integer countryId);
    
    Country getDefaultCountry();
}
