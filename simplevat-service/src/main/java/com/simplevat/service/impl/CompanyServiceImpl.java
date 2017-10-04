/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.service.impl;

import com.simplevat.dao.CompanyDao;
import com.simplevat.dao.Dao;
import com.simplevat.entity.Company;
import com.simplevat.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Service("companyService")
@Transactional
public class CompanyServiceImpl extends CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    protected Dao<Integer, Company> getDao() {
        return this.companyDao;
    }

}
