/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.financialreport;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.invoice.controller.InvoiceModelHelper;
import com.simplevat.web.invoice.model.InvoiceModel;
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
    private InvoiceModelHelper invoiceModelHelper;
    private FinancialPeriod financialPeriod;
    @Getter
    @Setter
    double totalTransactionAmount;
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
        invoiceList.clear();
        List<Invoice> invoices = new ArrayList<>();
        System.out.println("entered=="+financialPeriod.getLastDate());
        invoices = invoiceService.getInvoicesForReports(financialPeriod.getStartDate(), financialPeriod.getLastDate());
        if (invoices != null && !invoices.isEmpty()) {
            for (Invoice invoice : invoices) {
                invoiceList.add(invoiceModelHelper.getInvoiceModel(invoice));
            }
        }
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
