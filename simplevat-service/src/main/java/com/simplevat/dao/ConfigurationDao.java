/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.dao;

import com.simplevat.entity.Configuration;

/**
 *
 * @author daynil
 */
public interface ConfigurationDao extends Dao<Integer, Configuration> {

    public Configuration getConfigurationByName(String cofigurationName);

}
