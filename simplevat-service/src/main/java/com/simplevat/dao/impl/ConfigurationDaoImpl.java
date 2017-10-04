/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.dao.impl;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.ConfigurationDao;
import com.simplevat.entity.Configuration;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daynil
 */
@Repository
public class ConfigurationDaoImpl extends AbstractDao<Integer, Configuration> implements ConfigurationDao {

    @Override
    public Configuration getConfigurationByName(String cofigurationName) {
        
    TypedQuery<Configuration> query = getEntityManager().createNamedQuery("Configuration.findByName", Configuration.class);
        query.setParameter("name", cofigurationName);
        if(query.getResultList() != null && !query.getResultList().isEmpty()){
            return query.getResultList().get(0);
        }
        return null;
    }

}
