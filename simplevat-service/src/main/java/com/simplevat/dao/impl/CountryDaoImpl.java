package com.simplevat.dao.impl;

import com.simplevat.dao.CountryDao;
import com.simplevat.entity.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
}
