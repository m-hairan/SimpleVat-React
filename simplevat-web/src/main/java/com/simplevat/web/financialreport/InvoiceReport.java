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
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.CompanyService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
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
public class InvoiceReport extends BaseController {

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private InvoiceModelHelper invoiceModelHelper;
    private FinancialPeriod financialPeriod;
    @Getter
    @Setter
    double totalInvoiceAmount;
    private List<InvoiceModel> invoiceList = new ArrayList<>();
    public InvoiceReport() {
        super(ModuleName.REPORT_MODULE);
    }

    @PostConstruct
    public void init() {

    }

    public List<FinancialPeriod> completeFinancialPeriods() {
        return FinancialPeriodHolder.getFinancialPeriodList();
    }
    public void view() {
        totalInvoiceAmount=0.00;
        invoiceList.clear();
        List<Invoice> invoices = new ArrayList<>();
        System.out.println("entered==" + financialPeriod.getLastDate());
        invoices = invoiceService.getInvoicesForReports(financialPeriod.getStartDate(), financialPeriod.getLastDate());
        if (invoices != null && !invoices.isEmpty()) {
            for (Invoice invoice : invoices) {
                totalInvoiceAmount=totalInvoiceAmount+invoice.getInvoiceAmount().doubleValue();
                invoiceList.add(invoiceModelHelper.getInvoiceModel(invoice));
            }
        }
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

    public FinancialPeriod getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(FinancialPeriod financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    public List<InvoiceModel> getInvoicelist() {
        return invoiceList;
    }

    public void setInvoicelist(List<InvoiceModel> invoiceList) {
        this.invoiceList = invoiceList;
    }

}
