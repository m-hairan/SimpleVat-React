package com.simplevat.service.impl;

import com.simplevat.dao.CountryDao;
import com.simplevat.entity.Country;
import com.simplevat.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryDao countryDao;

    @Override
    public List<Country> getCountries() {
        List<Country> countries = countryDao.getCountries();
        return countries;
    }
}
