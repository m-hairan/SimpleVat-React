/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.bean;

import com.simplevat.web.constant.TransactionStatusConstant;
import com.simplevat.web.transactioncategory.controller.TranscationCategoryHelper;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author uday
 */

@Getter
@Setter
public class Transaction {
    private int id;
    private String transactionDate;
    private String description;
    private String drAmount;
    private String crAmount;
    private Boolean validData;
    private String format;
    
}
