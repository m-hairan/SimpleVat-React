/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.financialreport;

import com.github.javaplugs.jsf.SpringScopeView;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Document;
import com.simplevat.entity.Company;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.CompanyService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.bankaccount.controller.TransactionControllerHelper;
import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.utils.FacesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author admin
 */
@Controller
@SpringScopeView
public class TransactionReport extends BaseController {

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BankAccountService bankAccountService;

    private BankAccount bankAccount;

    private FinancialPeriod financialPeriod;

    @Getter
    @Setter
    private TransactionType transactionType;

    @Getter
    @Setter
    private TransactionCategory transactionCategory;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Getter
    @Setter
    List<TransactionType> transactionTypeList = new ArrayList<>();

    List<TransactionCategory> categorys = new ArrayList<>();

    @Getter
    @Setter
    TransactionControllerHelper transactionControllerHelper;

    @Getter
    @Setter
    double totalTransactionAmount;

//    private PDFOptions pdfOpt;
    private List<TransactionModel> transactionList = new ArrayList<>();

    public TransactionReport() {
        super(ModuleName.REPORT_MODULE);
    }

    @PostConstruct
    public void init() {
        transactionTypeList = transactionTypeService.findAll();
        transactionControllerHelper = new TransactionControllerHelper();
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
//        cell = new PdfPCell(new Paragraph("Account : " + bankAccount.getBankAccountName(),
//                new Font(Font.HELVETICA, 11)));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setBorder(0);
//        cell.setPaddingBottom(10);
//        table.addCell(cell);
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

    public List<TransactionType> transactionTypes(String type) throws Exception {
        return transactionTypeList;
    }

    public void view() {
        transactionList.clear();
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions = transactionService.getTransactionsByDateRangeAndTranscationTypeAndTranscationCategory(transactionType, transactionCategory, financialPeriod.getStartDate(), financialPeriod.getLastDate());
        //   System.out.println("dataa========="+transactionService.getTransactionsByDateRangeAndTranscationTypeAndTranscationCategory(transactionType, transactionCategory, financialPeriod.getStartDate(), financialPeriod.getLastDate()));
        if (transactions != null && !transactions.isEmpty()) {
            for (Transaction transaction : transactions) {
                totalTransactionAmount=totalTransactionAmount+transaction.getTransactionAmount().doubleValue();
                transactionList.add(transactionControllerHelper.getTransactionModel(transaction));
            }
        }
    }

    
      public List<TransactionCategory> transactionCategories(TransactionType transactionType) throws Exception {
        List<TransactionCategory> transactionCategoryParentList = new ArrayList<>();
        List<TransactionCategory> transactionCategoryList = new ArrayList<>();
        if (transactionType != null) {
            transactionCategoryList = transactionCategoryService.findAllTransactionCategoryByTransactionType(transactionType.getTransactionTypeCode());
        }
        for (TransactionCategory transactionCategory : transactionCategoryList) {
            if (transactionCategory.getParentTransactionCategory() != null) {
                transactionCategoryParentList.add(transactionCategory.getParentTransactionCategory());
            }
        }
        transactionCategoryList.removeAll(transactionCategoryParentList);
        return transactionCategoryList;
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
