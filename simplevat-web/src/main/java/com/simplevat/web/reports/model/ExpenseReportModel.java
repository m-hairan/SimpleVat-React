package com.simplevat.web.reports.model;

import java.util.Date;

import lombok.Data;

@Data
public class ExpenseReportModel {

    private Integer expenseId;
    private Integer expenseAmount;
    private Date expenseDate;
    private String expenseDescription;
    private String receiptNumber;
    private String transactionType;
    private String transactionCategory;
    private String currency;
    private String project;
}
