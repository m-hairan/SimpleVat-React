/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.report;

import com.github.javaplugs.jsf.SpringScopeView;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.simplevat.entity.Company;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.service.CompanyService;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.web.bankaccount.controller.TransactionControllerHelper;
import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.web.utils.FacesUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author admin
 */
@Controller
@SpringScopeView
public class TransactionReport extends TransactionControllerHelper {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BankAccountService bankAccountService;

    private BankAccount bankAccount;

    private FinancialPeriod financialPeriod;

//    private PDFOptions pdfOpt;
    private List<TransactionModel> transactionList = new ArrayList<>();

    @PostConstruct
    public void init() {
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        Company company = companyService.findByPK(FacesUtil.getLoggedInUser().getCompany().getCompanyId());
        pdf.setMargins(30, 0, 50, 20);
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Paragraph("Company Name : " + company.getCompanyName(),
                new Font(Font.HELVETICA, 16, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        cell.setPaddingTop(4);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("Account : " + bankAccount.getBankAccountName(),
                new Font(Font.HELVETICA, 11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        cell.setPaddingBottom(10);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("Financial Period : " + financialPeriod.getName(),
                new Font(Font.HELVETICA, 11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        cell.setPaddingBottom(10);
        table.addCell(cell);
        pdf.add(table);
    }

    public List<BankAccount> completeBankAccounts() {
        List<BankAccount> bankAccounts = new ArrayList<>();
        if (bankAccountService.getBankAccounts() != null && !bankAccountService.getBankAccounts().isEmpty()) {
            bankAccounts = bankAccountService.getBankAccounts();
        }
        return bankAccounts;
    }

    public List<FinancialPeriod> completeFinancialPeriods() {
        return FinancialPeriodHolder.getFinancialPeriodList();
    }

    public void view() {
        transactionList.clear();
        for (Transaction transaction : transactionService.getTransactionsByDateRangeAndBankAccountId(bankAccount, financialPeriod.getStartDate(), financialPeriod.getLastDate())) {
            transactionList.add(getTransactionModel(transaction));
        }
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public FinancialPeriod getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(FinancialPeriod financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    public List<TransactionModel> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionModel> transactionList) {
        this.transactionList = transactionList;
    }

}
