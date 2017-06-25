package com.simplevat.service.impl;

import com.simplevat.dao.CountryDao;
import com.simplevat.entity.Country;
import com.simplevat.service.CountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryDao countryDao;

    @Override
    public List<Country> getCountries() {
        List<Country> countries = countryDao.getCountries();
        return countries;
    }

    @Override
    public Country getCountry(Integer countryId) {
        return countryDao.getCountry(countryId);
    }

	@Override
	public Country getDefaultCountry() {
		return countryDao.getDefaultCountry();
	}
}
