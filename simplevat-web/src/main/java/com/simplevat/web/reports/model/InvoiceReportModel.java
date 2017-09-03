package com.simplevat.web.reports.model;

import java.util.Date;

import lombok.Data;

@Data
public class InvoiceReportModel {

    private Integer invoiceId;

    private String invoiceReferenceNumber;

    private Date invoiceDate;

    private Integer invoiceDueOn;

    private String invoiceText;

    private String discountType;

    private Integer invoiceDiscount;

    private String currency;

    private String project;
    
    private Integer invoiceAmount;
}
