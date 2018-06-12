/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.dao;

import com.simplevat.entity.Activity;
import com.simplevat.entity.Company;

/**
 *
 * @author admin
 */
public interface CompanyDao extends Dao<Integer, Company>{
    public Company getCompany();
}
