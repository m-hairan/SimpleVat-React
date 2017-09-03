package com.simplevat.web.reports.model;

import lombok.Data;

@Data
public class ExpenseMonthWise {
	
	private String month;
	private int 	amountExpense;
	private int amountInvoice;
	
	public ExpenseMonthWise() {
		
	}
	
	public ExpenseMonthWise(String month, int amountInvoice, int amountExpense) {
		this.month = month;
		this.amountInvoice = amountInvoice;
		this.amountExpense = amountExpense;
	}

}
