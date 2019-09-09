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
    @Getter
    @Setter
    DefaultMenuModel settingModel;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        settingModel = new DefaultMenuModel();
        addHomeMenuItem(model);
        addInvoiceMenuItem(model);
        addExpenseMenuItem(model);
        addBankAccountMenuItem(model);
        addTaxMenuItem(model);
        addReportMenuItem(model);
        addImportMenuItem(model);
        addMoreMenuItem(model);
        addSettingMenuItem(model);
        addEmployeeMenuItem(model);
//        populateSettingMenuItemModel(model);
    }

    private void addHomeMenuItem(DefaultMenuModel model) {
        DefaultMenuItem item = new DefaultMenuItem("Home");
        item.setIcon("home");
        item.setOutcome("/pages/secure/home");
        model.addElement(item);
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

    private void addMoreMenuItem(DefaultMenuModel model) {
        DefaultSubMenu submenu = new DefaultSubMenu("Master");
        submenu.setIcon("insert_chart");
        addProductServiceMasterMenuItem(submenu);
        addProjectMasterMenuItem(submenu);
        addContactMasterMenuItem(submenu);
        addUserMenuItem(submenu);
        if (submenu.getElementsCount() > 0) {
            model.addElement(submenu);
        }
    }

    private void addContactMasterMenuItem(DefaultSubMenu subMenu) {
        if (PageAccessControl.hasAccess(ModuleName.CONTACT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Contact");
            item.setIcon("contacts");
            item.setOutcome("/pages/secure/contact/list");
            subMenu.addElement(item);
        }
    }

    private void addProjectMasterMenuItem(DefaultSubMenu subMenu) {
        if (PageAccessControl.hasAccess(ModuleName.PROJECT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Project");
            item.setIcon("book");
            item.setOutcome("/pages/secure/project/projects");
            subMenu.addElement(item);
        }
    }

    private void addProductServiceMasterMenuItem(DefaultSubMenu subMenu) {
        if (PageAccessControl.hasAccess(ModuleName.PRODUCT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Product");
            item.setIcon("receipt");
            item.setOutcome("/pages/secure/product-service/list");
            subMenu.addElement(item);
        }
    }

    private void addUserMenuItem(DefaultSubMenu submenu) {
        if (PageAccessControl.hasAccess(ModuleName.USER_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Users");
            item.setIcon("supervisor_account");
            item.setOutcome("/pages/secure/user/list");
            submenu.addElement(item);
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

    private void addImportMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.IMPORT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Imports");
            item.setIcon("exposure");
            item.setOutcome("/pages/secure/bankaccount/import-transactions.xhtml?faces-redirect=true");
            model.addElement(item);
        }
    }
    
     private void addEmployeeMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.IMPORT_MODULE)) {
            DefaultMenuItem item = new DefaultMenuItem("Employee");
            item.setIcon("supervisor_account");
            item.setOutcome("/pages/secure/employee/employee.xhtml?faces-redirect=true");
            model.addElement(item);
        }
    }

    private void addSettingMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.SETTING_MODULE)) {
            DefaultSubMenu submenu = new DefaultSubMenu("Setting");
            submenu.setIcon("settings");
            if (PageAccessControl.hasAccess(ModuleName.GENERALSETTING_MODULE)) {
                DefaultMenuItem item = new DefaultMenuItem("General");
                item.setIcon("build");
                item.setOutcome("/pages/secure/setting/general");
                submenu.addElement(item);
            }
            if (PageAccessControl.hasAccess(ModuleName.VATCATEGORY_MODULE)) {
                DefaultMenuItem item = new DefaultMenuItem("Vat Category");
                item.setIcon("bookmark_border");
                item.setOutcome("/pages/secure/setting/vat-list");
                submenu.addElement(item);
            }
            if (PageAccessControl.hasAccess(ModuleName.TRANSACTIONCATEGORY_MODULE)) {
                DefaultMenuItem item = new DefaultMenuItem("Transaction Category");
                item.setIcon("trending_up");
                item.setOutcome("/pages/secure/setting/transactioncategory-list");
                submenu.addElement(item);
            }
            if (submenu.getElementsCount() > 0) {
                model.addElement(submenu);
            }
        }
    }
//    private void populateSettingMenuItemModel(DefaultMenuModel model) {
//        if (PageAccessControl.hasAccess(ModuleName.SETTING_MODULE)) {
//            if (PageAccessControl.hasAccess(ModuleName.GENERALSETTING_MODULE)) {
//                DefaultMenuItem item = new DefaultMenuItem("General");
//                item.setIcon("build");
//                item.setOutcome("/pages/secure/setting/general");
//                model.addElement(item);
//            }
//            if (PageAccessControl.hasAccess(ModuleName.VATCATEGORY_MODULE)) {
//                DefaultMenuItem item = new DefaultMenuItem("Vat Category");
//                item.setIcon("bookmark_border");
//                item.setOutcome("/pages/secure/setting/vat-list");
//                model.addElement(item);
//            }
//            if (PageAccessControl.hasAccess(ModuleName.TRANSACTIONCATEGORY_MODULE)) {
//                DefaultMenuItem item = new DefaultMenuItem("Transaction Category");
//                item.setIcon("trending_up");
//                item.setOutcome("/pages/secure/setting/transactioncategory-list");
//                model.addElement(item);
//            }
//            if (PageAccessControl.hasAccess(ModuleName.USER_MODULE)) {
//                DefaultMenuItem item = new DefaultMenuItem("Users");
//                item.setIcon("supervisor_account");
//                item.setOutcome("/pages/secure/user/list");
//                model.addElement(item);
//            }
//        }
//    }

}
