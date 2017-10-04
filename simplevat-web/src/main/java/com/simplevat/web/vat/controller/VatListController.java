/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.vat.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.web.vat.model.VatListModel;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

/**
 *
 * @author daynil
 */
@Controller
@SpringScopeView

public class VatListController {
    
    @Getter
    @Setter
    VatListModel vatListModel;
    
     @PostConstruct
    public void init() {

        vatListModel.setTransactionType(new TransactionType());

    }
    
    
    

}
