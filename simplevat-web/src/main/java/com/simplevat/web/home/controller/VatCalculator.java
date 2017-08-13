package com.simplevat.web.home.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.invoice.InvoiceService;

import lombok.Getter;

@Controller
@SpringScopeView
public class VatCalculator implements Serializable {
	

	@Getter
	private int vatIn;
	@Getter
	private int vatOut;
	@Getter
	private int vatBalance;
	@Getter
	private int numberOfTransaction;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@PostConstruct
	public void init() {
		vatIn = invoiceService.getVatInQuartly();
		vatOut = expenseService.getVatOutQuartly();
		vatBalance = vatIn - vatOut;
	}
	


}
