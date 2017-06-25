package com.simplevat.dao.impl;

import com.simplevat.dao.CountryDao;
import com.simplevat.entity.Country;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

/**
 * Created by mohsinh on 3/10/2017.
 */
@Repository
public class CountryDaoImpl implements CountryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Country> getCountries() {

        List<Country> countries = entityManager.createNamedQuery("allCountries", Country.class).getResultList();
        return countries;
    }

    @Override
    public Country getCountry(Integer countryId) {
        return entityManager.find(Country.class, countryId);
    }

	@Override
	public Country getDefaultCountry() {
		List<Country> countries = getCountries();
		if(CollectionUtils.isNotEmpty(countries)){
			return countries.get(0);
		}
		return null;
	}
}
