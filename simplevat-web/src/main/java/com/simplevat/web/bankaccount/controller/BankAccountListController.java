package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Controller
@SpringScopeView
public class BankAccountListController extends BaseController implements Serializable {

    @Autowired
    private BankAccountService bankAccountService;
    @Getter
    @Setter
    private List<BankAccount> bankAccounts;

    public BankAccountListController() {
        super(ModuleName.BANK_MODULE);
    }

    @PostConstruct
    public void init() {
        bankAccounts = bankAccountService.getBankAccounts();
    }
}
