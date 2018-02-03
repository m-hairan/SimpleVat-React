/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.utils;

import com.simplevat.entity.Configuration;
import com.simplevat.web.constant.InvoiceTemplateTypeConstant;
import com.simplevat.web.setting.controller.InvoiceTemplateModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author h
 */
public class GeneralSettingUtil {

    public static List<InvoiceTemplateModel> invoiceTemplateList() {
        List<InvoiceTemplateModel> configurationList = new ArrayList<>();
        configurationList.add(new InvoiceTemplateModel("Grey", InvoiceTemplateTypeConstant.GREY));
        configurationList.add(new InvoiceTemplateModel("Orange", InvoiceTemplateTypeConstant.ORANGE));
        configurationList.add(new InvoiceTemplateModel("Green", InvoiceTemplateTypeConstant.GREEN));
        configurationList.add(new InvoiceTemplateModel("Blue", InvoiceTemplateTypeConstant.BLUE));
        configurationList.add(new InvoiceTemplateModel("Cyan", InvoiceTemplateTypeConstant.CYAN));
        configurationList.add(new InvoiceTemplateModel("Red", InvoiceTemplateTypeConstant.RED));
        return configurationList;
    }
}
