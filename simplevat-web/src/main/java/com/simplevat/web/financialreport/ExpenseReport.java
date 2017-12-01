/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.financialreport;

import com.github.javaplugs.jsf.SpringScopeView;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.simplevat.entity.Company;
import com.simplevat.entity.Expense;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.expense.controller.ExpenseControllerHelper;
import com.simplevat.web.expense.model.ExpenseModel;
import com.simplevat.web.invoice.controller.InvoiceModelHelper;
import com.simplevat.web.invoice.model.InvoiceModel;
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
public class ExpenseReport extends BaseController {

    @Autowired
    ExpenseService expenseService;
    @Autowired
    private CompanyService companyService;

    @Getter
    @Setter
    ExpenseControllerHelper controllerHelper;

    private FinancialPeriod financialPeriod;
    @Getter
    @Setter
    double totalExpenseAmount;
    
    private List<ExpenseModel> expenseList = new ArrayList<>();

    public ExpenseReport() {
        super(ModuleName.REPORT_MODULE);
    }

    @PostConstruct
    public void init() {
        controllerHelper = new ExpenseControllerHelper();
    }

    public List<FinancialPeriod> completeFinancialPeriods() {
        return FinancialPeriodHolder.getFinancialPeriodList();
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

        cell = new PdfPCell(new Paragraph("Financial Period : " + financialPeriod.getName(),
                new Font(Font.HELVETICA, 11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        cell.setPaddingBottom(10);
        table.addCell(cell);
        pdf.add(table);
    }

    public void view() {
        totalExpenseAmount=0.0;
        expenseList.clear();
        List<Expense> expenses = new ArrayList<>();
        System.out.println("entered==" + financialPeriod.getLastDate());
        expenses = expenseService.getExpenseForReports(financialPeriod.getStartDate(), financialPeriod.getLastDate());
        if (expenses != null && !expenses.isEmpty()) {
            for (Expense expense : expenses) {
                totalExpenseAmount=totalExpenseAmount+expense.getExpenseAmount().doubleValue();
                expenseList.add(controllerHelper.getExpenseModel(expense));
            }
        }
    }

    public FinancialPeriod getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(FinancialPeriod financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    public List<ExpenseModel> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<ExpenseModel> expenseList) {
        this.expenseList = expenseList;
    }
}
