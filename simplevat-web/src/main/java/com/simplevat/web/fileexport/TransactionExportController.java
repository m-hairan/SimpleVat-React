/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.fileexport;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.TransactionView;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Ajax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author admin
 */
@Controller
@SpringScopeView
public class TransactionExportController implements Serializable {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;
    @Getter
    @Setter
    private List<TransactionView> transactionList = new ArrayList<>();
    private BankAccount bankAccount;
    @Getter
    @Setter
    private Date startDate;
    @Getter
    @Setter
    private Date endDate;

    @PostConstruct
    public void init() {
        Integer bankAccountId = FacesUtil.getSelectedBankAccountId();
        bankAccount = bankAccountService.findByPK(bankAccountId);

    }

    public void handleFileDownload() {
        transactionList = transactionService.getTransactionViewListByDateRang(bankAccount.getBankAccountId(), startDate, endDate);
        Ajax.oncomplete("exportTransaction()");
    }
}
