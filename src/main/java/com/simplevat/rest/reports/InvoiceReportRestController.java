/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.reports;

import com.simplevat.contact.model.InvoiceRestModel;
import com.simplevat.contact.model.FinancialPeriodRestModel;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.helper.InvoiceModelHelper;
import com.simplevat.service.invoice.InvoiceService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daynil
 */
@RestController
@RequestMapping("/rest/invoicereport")
public class InvoiceReportRestController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    private InvoiceModelHelper invoiceModelHelper;

    @RequestMapping(method = RequestMethod.POST, value = "/viewinvoicereport")
    public ResponseEntity<List<InvoiceRestModel>> view(@RequestBody FinancialPeriodRestModel financialPeriod) {
        try {
            double totalInvoiceAmount = 0.00;
            List<InvoiceRestModel> invoiceList = new ArrayList<>();
            invoiceList.clear();
            System.out.println("entered==" + financialPeriod.getLastDate());
            List<Invoice> invoices = invoiceService.getInvoicesForReports(financialPeriod.getStartDate(), financialPeriod.getLastDate());
            if (invoices != null && !invoices.isEmpty()) {
                for (Invoice invoice : invoices) {
                    totalInvoiceAmount = totalInvoiceAmount + invoice.getInvoiceAmount().doubleValue();
                    invoiceList.add(invoiceModelHelper.getInvoiceModel(invoice));
                }
            }
            return new ResponseEntity(invoiceList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
