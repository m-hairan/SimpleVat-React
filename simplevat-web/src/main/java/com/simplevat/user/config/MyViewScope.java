/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.user.config;

import com.github.javaplugs.jsf.ViewScope;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author uday
 */
    
@Configuration
public class MyViewScope extends CustomScopeConfigurer {

    public MyViewScope() {
        Map<String, Object> map = new HashMap<>();
        map.put("view", new ViewScope());
        super.setScopes(map);
    }
}
