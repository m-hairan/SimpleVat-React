package com.simplevat.web.reports.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class BankAccountTransactionReportModel {

	private Date date;
	private String transaction;
	private String type;
	private String reference;
	private BigDecimal debit;
	private BigDecimal credit;
}
