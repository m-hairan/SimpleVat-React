/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.tax;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.TaxTransaction;
import com.simplevat.service.TaxTransactionService;
import java.io.Serializable;
import java.util.ArrayList;
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
public class TaxHistoryController implements Serializable {

    @Autowired
    private TaxTransactionService taxTransactionService;

    @Getter
    @Setter
    private List<TaxTransaction> taxTransactionList;

    @PostConstruct
    public void init() {
        taxTransactionList = taxTransactionService.getClosedTaxTransactionList();
        if (taxTransactionList == null) {
            taxTransactionList = new ArrayList<>();
        }
    }
}
