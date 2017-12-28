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
import org.primefaces.model.menu.DefaultSubMenu;
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
        addHomeMenuItem(model);
        addContactMenuItem(model);
        addProjectMenuItem(model);
        addProductServiceItem(model);
        addInvoiceMenuItem(model);
        addExpenseMenuItem(model);
        addBankAccountMenuItem(model);
        addTaxMenuItem(model);
        addReportMenuItem(model);
    }

    private void addHomeMenuItem(DefaultMenuModel model) {
        DefaultMenuItem item = new DefaultMenuItem("Home");
        item.setIcon("home");
        item.setOutcome("/pages/secure/home");
        model.addElement(item);
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

    private void addInvoiceMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.INVOICE_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Invoice");
            item.setIcon("receipt");
            item.setOutcome("/pages/secure/invoice/list");
            model.addElement(item);
        }
    }

    private void addBankAccountMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.BANK_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Bank Accounts");
            item.setIcon("account_balance");
            item.setOutcome("/pages/secure/bankaccount/bankaccounts");
            model.addElement(item);
        }
    }

    private void addExpenseMenuItem(DefaultMenuModel model) {
        DefaultSubMenu submenu = new DefaultSubMenu("Expense");
        submenu.setIcon("exposure");
        if (PageAccessControl.hasAccess(ModuleName.EXPENSE_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Expense");
            item.setIcon("exposure");
            item.setOutcome("/pages/secure/expense/list");
            submenu.addElement(item);
        }
        if (PageAccessControl.hasAccess(ModuleName.PURCHASE_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Purchase");
            item.setIcon("receipt");
            item.setOutcome("/pages/secure/purchase/purchase-list");
            submenu.addElement(item);
        }
        model.addElement(submenu);
    }

    private void addTaxMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.TAX_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Taxes");
            item.setIcon("exposure");
            item.setOutcome("/pages/secure/tax/index");
            model.addElement(item);
        }
    }

    private void addReportMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.REPORT_MODULE)) {
            DefaultSubMenu submenu = new DefaultSubMenu("Report");
            submenu.setIcon("insert_chart");
            addTranscationReportMenuItem(submenu);
            addInvoiceReportMenuItem(submenu);
            addExpenseReportMenuItem(submenu);
            model.addElement(submenu);
        }
    }

    private void addTranscationReportMenuItem(DefaultSubMenu subMenu) {
        if (PageAccessControl.hasAccess(ModuleName.REPORT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Transaction Report");
            item.setIcon("swap_vert");
            item.setOutcome("/pages/secure/report/transactionReport");
            subMenu.addElement(item);
        }
    }

    private void addInvoiceReportMenuItem(DefaultSubMenu subMenu) {
        if (PageAccessControl.hasAccess(ModuleName.REPORT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Invoice Report");
            item.setIcon("receipt");
            item.setOutcome("/pages/secure/report/invoiceReport");
            subMenu.addElement(item);
        }
    }

    private void addExpenseReportMenuItem(DefaultSubMenu subMenu) {
        if (PageAccessControl.hasAccess(ModuleName.REPORT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Expense Report");
            item.setIcon("assignment");
            item.setOutcome("/pages/secure/report/expenseReport");
            subMenu.addElement(item);
        }
    }

    private void addProductServiceItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.PRODUCT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Product");
            item.setIcon("receipt");
            item.setOutcome("/pages/secure/product-service/list");
            model.addElement(item);
        }
    }


}
