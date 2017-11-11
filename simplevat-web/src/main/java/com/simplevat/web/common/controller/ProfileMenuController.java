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
public class ProfileMenuController implements Serializable {

    @Getter
    @Setter
    DefaultMenuModel model;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        DefaultMenuItem item = new DefaultMenuItem("Account");
        item.setIcon("home");
        item.setOutcome("/pages/secure/account/index");
        model.addElement(item);

        DefaultMenuItem item1 = new DefaultMenuItem("Privacy");
        item1.setIcon("home");
        item1.setOutcome("/pages/secure/home");
        model.addElement(item1);

        DefaultMenuItem item2 = new DefaultMenuItem("Users");
        item2.setIcon("home");
        item2.setOutcome("/pages/secure/user/list");
        model.addElement(item2);

        addSettingMenuItem(model);
        
        DefaultMenuItem item4 = new DefaultMenuItem("Logout");
        item4.setIcon("home");
        item4.setCommand("#{request.contextPath}/j_spring_security_logout");
        model.addElement(item4);

       
    }

    private void addSettingMenuItem(DefaultMenuModel model) {
        if (PageAccessControl.hasAccess(ModuleName.SETTING_MODULE)) {
            DefaultMenuItem item3 = new DefaultMenuItem("Settings");
            item3.setIcon("home");
            item3.setOutcome("/pages/secure/setting/index");
            model.addElement(item3);
        }

    }

//                     <li role="menuitem"><p:link
//                                    outcome="/pages/secure/account/index" styleClass="ripplelink">
//                                    <i class="material-icons">&#xE7FD;</i>
//                                    <span>Account</span>
//                                </p:link>
//                            </li>
//                            <li role="menuitem"><a href="#" class="ripplelink"> <i
//                                        class="material-icons">&#xE32A;</i> <span>Privacy</span>
//                                </a></li>
//                            <li role="menuitem"><p:link outcome="/pages/secure/setting/index" styleClass="ripplelink">
//                                    <i class="material-icons">&#xE8B9;</i> 
//                                    <span>Settings</span>
//                                </p:link>
//                            </li>
//                            <li role="menuitem"><p:link outcome="/pages/secure/user/list" styleClass="ripplelink">
//                                    <i class="material-icons">&#xE7FB;</i>
//                                    <span>Users</span>
//                                </p:link>
//                            </li>
//                            <li role="menuitem"><a
//                                    href="#{request.contextPath}/j_spring_security_logout"
//                                    class="ripplelink"> <i class="material-icons">&#xE8AC;</i> <span>Logout</span>
//                                </a></li>
//
}
