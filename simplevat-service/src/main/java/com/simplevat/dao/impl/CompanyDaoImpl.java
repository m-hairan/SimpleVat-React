/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.dao.impl;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.CompanyDao;
import com.simplevat.entity.Company;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository(value = "companyDao")
public class CompanyDaoImpl extends AbstractDao<Integer, Company> implements CompanyDao {

}
