/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.setting.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Configuration;
import com.simplevat.service.ConfigurationService;
import com.simplevat.web.constant.ConfigurationConstants;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author admin
 */
@Controller
@SpringScopeView
public class GeneralController implements Serializable {

    @Autowired
    private ConfigurationService configurationService;

    private List<Configuration> configurationList;

    @Getter
    @Setter
    private Configuration configurationInvoiceRefPtrn;

    @PostConstruct
    public void init() {
        configurationList = configurationService.getConfigurationList();

        if ( configurationList!= null && configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICING_REFERENCE_PATTERN)).findAny().isPresent()) {
            configurationInvoiceRefPtrn = configurationList.stream().filter(configuration -> configuration.getName().equals(ConfigurationConstants.INVOICING_REFERENCE_PATTERN)).findFirst().get();
        } else {
            configurationInvoiceRefPtrn = new Configuration();
            configurationInvoiceRefPtrn.setName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
        }
    }

    public void saveUpdate() {
        configurationList.clear();
        configurationList.add(configurationInvoiceRefPtrn);
        configurationService.updateConfigurationList(configurationList);
    }
}
