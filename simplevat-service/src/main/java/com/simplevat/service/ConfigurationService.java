/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.service;

import com.simplevat.entity.Configuration;

/**
 *
 * @author daynil
 */
public abstract class ConfigurationService extends SimpleVatService<Integer, Configuration>{

   public abstract Configuration getConfigurationByName(String cofigurationName);

}
