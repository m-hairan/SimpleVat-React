/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.common.controller;

import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.utils.PageAccessControl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author admin
 */
@Controller
@SessionScope
public class MenuController implements Serializable {

    @Getter
    @Setter
    DefaultMenuModel model;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        DefaultMenuItem item = new DefaultMenuItem("Home");
        item.setIcon("home");
        item.setOutcome("/pages/secure/home");
        model.addElement(item);
        addContactMenuItem(model);
        addProjectMenuItem(model);
        addInvoiveMenuItem(model);
        addExpenseReportMenuItem(model);
        addPurchaseMenuItem(model);
        addBankAccountMenuItem(model);
        addTranscationReportMenuItem(model);
        addTaxMenuItem(model);
    }

    private void addContactMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.CONTACT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Contact");
            item.setIcon("contacts");
            item.setOutcome("/pages/secure/contact/list");
            model.addElement(item);
        }
    }

    private void addProjectMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.PROJECT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Project");
            item.setIcon("book");
            item.setOutcome("/pages/secure/project/projects");
            model.addElement(item);
        }
    }

    private void addInvoiveMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.INVOICE_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Invoice");
            item.setIcon("receipt");
            item.setOutcome("/pages/secure/invoice/list");
            model.addElement(item);
        }
    }

    private void addPurchaseMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.PURCHASE_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Purchase");
            item.setIcon("receipt");
            item.setOutcome("/pages/secure/purchase/purchase-list");
            model.addElement(item);
        }
    }

    private void addBankAccountMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.BANK_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Bank Accounts");
            item.setIcon("store");
            item.setOutcome("/pages/secure/bankaccount/bankaccounts");
            model.addElement(item);
        }
    }

    private void addTranscationReportMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.REPORT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Transaction Report");
            item.setIcon("book");
            item.setOutcome("/pages/secure/report/transactionReport");
            model.addElement(item);
        }
    }

    private void addExpenseReportMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.EXPENSE_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Expense");
            item.setIcon("exposure");
            item.setOutcome("/pages/secure/expense/expenses");
            model.addElement(item);
        }
    }
    
    private void addTaxMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.TAX_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Taxes");
            item.setIcon("reply");
            item.setOutcome("/pages/secure/tax/index");
            model.addElement(item);
        }
    }

//                        <p:menuitem id="st_dashboard" value="Home" icon="&#xE871;"
//                                    outcome="/pages/secure/home" />
//
//                        <p:menuitem id="st_contact" update="@form contactForm"
//                                    value="Contacts" icon="contacts"
//                                    outcome="/pages/secure/contact/list" />
//
//                        <p:menuitem id="st_project" value="Projects" icon="book"
//                                    outcome="/pages/secure/project/projects" />
//
//                        <p:menuitem id="st_invoice" update="@form invoice"
//                                    value="Invoices" icon="receipt" 
//                                    outcome="/pages/secure/invoice/list" />                        
//
//                        <p:menuitem id="st_expense" value="#{messages.expenses}"
//                                    icon="exposure" outcome="#{expenseMenuLink}" />
//
//                        
//                        <p:menuitem id="st_purchase" 
//                                    value="Purchase" icon="receipt" 
//                                    outcome="/pages/secure/purchase/purchase-list" />
//
//                        <p:menuitem id="st_bankaccount" value="#{messages.bank_accounts}"
//                                    icon="store" outcome="#{bankAccountMenuLink}" />
//                        
//                        <p:menuitem id="st_transactionreport" value="#{messages.TransactionReport}"
//                                    icon="store" outcome="/pages/secure/report/transactionReport" />
}
