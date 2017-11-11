/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.utils;

import com.simplevat.entity.User;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.constant.RoleCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author uday
 */
public class PageAccessControl {

    private final static Map<Integer, List<String>> accessMap = new HashMap<>();

    static {
        List<String> managerRoleAccessList = new ArrayList<>();
        managerRoleAccessList.add(ModuleName.INVOICE_MODULE.getName());
        managerRoleAccessList.add(ModuleName.EXPENSE_MODULE.getName());
        managerRoleAccessList.add(ModuleName.CONTACT_MODULE.getName());
        accessMap.put(RoleCode.MANGER.getCode(), managerRoleAccessList);

        List<String> adminRoleAccessList = new ArrayList<>();
        adminRoleAccessList.add(ModuleName.EXPENSE_MODULE.getName());
        adminRoleAccessList.add(ModuleName.PURCHASE_MODULE.getName());
        adminRoleAccessList.add(ModuleName.INVOICE_MODULE.getName());
        adminRoleAccessList.add(ModuleName.BANK_MODULE.getName());
        adminRoleAccessList.add(ModuleName.REPORT_MODULE.getName());
        adminRoleAccessList.add(ModuleName.PROJECT_MODULE.getName());
        adminRoleAccessList.add(ModuleName.SETTING_MODULE.getName());
        adminRoleAccessList.add(ModuleName.CONTACT_MODULE.getName());
        accessMap.put(RoleCode.ADMIN.getCode(), adminRoleAccessList);

        List<String> shareHolderRoleAccessList = new ArrayList<>();
        shareHolderRoleAccessList.add(ModuleName.EXPENSE_MODULE.getName());
        shareHolderRoleAccessList.add(ModuleName.PURCHASE_MODULE.getName());
        shareHolderRoleAccessList.add(ModuleName.INVOICE_MODULE.getName());
        shareHolderRoleAccessList.add(ModuleName.BANK_MODULE.getName());
        shareHolderRoleAccessList.add(ModuleName.REPORT_MODULE.getName());
        shareHolderRoleAccessList.add(ModuleName.PROJECT_MODULE.getName());
        shareHolderRoleAccessList.add(ModuleName.SETTING_MODULE.getName());
        shareHolderRoleAccessList.add(ModuleName.CONTACT_MODULE.getName());
        accessMap.put(RoleCode.SHARE_HOLDER.getCode(), shareHolderRoleAccessList);

        List<String> directorRoleAccessList = new ArrayList<>();
        directorRoleAccessList.add(ModuleName.EXPENSE_MODULE.getName());
        directorRoleAccessList.add(ModuleName.PURCHASE_MODULE.getName());
        directorRoleAccessList.add(ModuleName.INVOICE_MODULE.getName());
        directorRoleAccessList.add(ModuleName.BANK_MODULE.getName());
        directorRoleAccessList.add(ModuleName.REPORT_MODULE.getName());
        directorRoleAccessList.add(ModuleName.PROJECT_MODULE.getName());
        directorRoleAccessList.add(ModuleName.SETTING_MODULE.getName());
        directorRoleAccessList.add(ModuleName.CONTACT_MODULE.getName());
        accessMap.put(RoleCode.DIRECTOR.getCode(), directorRoleAccessList);

        List<String> employeeRoleAccessList = new ArrayList<>();
        employeeRoleAccessList.add(ModuleName.EXPENSE_MODULE.getName());
        employeeRoleAccessList.add(ModuleName.PURCHASE_MODULE.getName());
        employeeRoleAccessList.add(ModuleName.INVOICE_MODULE.getName());
        employeeRoleAccessList.add(ModuleName.BANK_MODULE.getName());
        employeeRoleAccessList.add(ModuleName.REPORT_MODULE.getName());
        employeeRoleAccessList.add(ModuleName.PROJECT_MODULE.getName());
        employeeRoleAccessList.add(ModuleName.SETTING_MODULE.getName());
        employeeRoleAccessList.add(ModuleName.CONTACT_MODULE.getName());
        accessMap.put(RoleCode.EMPLOYEE.getCode(), employeeRoleAccessList);

        List<String> accountantRoleAccessList = new ArrayList<>();
        accountantRoleAccessList.add(ModuleName.EXPENSE_MODULE.getName());
        accountantRoleAccessList.add(ModuleName.PURCHASE_MODULE.getName());
        accountantRoleAccessList.add(ModuleName.INVOICE_MODULE.getName());
        accountantRoleAccessList.add(ModuleName.BANK_MODULE.getName());
        accountantRoleAccessList.add(ModuleName.REPORT_MODULE.getName());
        accountantRoleAccessList.add(ModuleName.PROJECT_MODULE.getName());
        accountantRoleAccessList.add(ModuleName.SETTING_MODULE.getName());
        accountantRoleAccessList.add(ModuleName.CONTACT_MODULE.getName());
        accessMap.put(RoleCode.ACCOUNTANT.getCode(), adminRoleAccessList);

    }

    public static boolean hasAccess(ModuleName moduleName) {
        User user = FacesUtil.getLoggedInUser();
        List<String> roleAccessList = accessMap.get(user.getRole().getRoleCode());
        System.out.println("Display=========");
        if (roleAccessList != null && !roleAccessList.isEmpty()) {
            if (roleAccessList.contains(moduleName.getName())) {
                return true;
            }
        }
        return false;
    }

}
