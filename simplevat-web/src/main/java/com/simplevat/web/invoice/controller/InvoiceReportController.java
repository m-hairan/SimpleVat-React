/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.invoice.controller;

import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.invoice.model.InvoiceItemModel;
import com.simplevat.web.invoice.model.InvoiceModel;
import com.simplevat.web.reports.AbstractReportBean;
import com.simplevat.web.reports.ReportConfigUtil;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author daynil
 */
@Named
public class InvoiceReportController extends AbstractReportBean {

    private final String COMPILE_FILE_NAME = "Invoice";
    private int invoiceId;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }

    @PostConstruct
    public void init() {
        setCompileDir("/invoice/default");

    }

    @Override
    protected Map<String, Object> getReportParameters() {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        InvoiceModel invoiceModel = null;

        if (invoiceId > 0) {
            Invoice invoice = invoiceService.findByPK(invoiceId);
            invoiceModel = new InvoiceModelHelper().getInvoiceModel(invoice);

            try {
                BigDecimal totalVat = new BigDecimal(0);
                BigDecimal netSubtotal = new BigDecimal(0);
                int quantity = 0;
                List<InvoiceItemModel> invoiceItemModelList = invoiceModel.getInvoiceItems();
                for (InvoiceItemModel invoiceItem : invoiceItemModelList) {
                    if (invoiceItem.getVatId().compareTo(new BigDecimal(0)) > 0) {
                        totalVat = totalVat.add((invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuatity()))).divide(invoiceItem.getVatId()));
                    }
                    netSubtotal = netSubtotal.add(invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuatity())));
                    quantity += invoiceItem.getQuatity();
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                parameters.put("companyName", "Company Name");
                parameters.put("companyAddress", "Company Address");
                String country = "";
                if (invoiceModel.getContact().getCountry() != null) {
                    country = invoiceModel.getContact().getCountry().getCountryName();
                }
                parameters.put("invoiceHolderAddress", country);
                parameters.put("invoiceNumber", String.valueOf(invoiceModel.getInvoiceId()));
                parameters.put("invoiceDueDate", String.valueOf(dateFormat.format(invoiceModel.getInvoiceDate())));
                parameters.put("paymentDueDate", String.valueOf(dateFormat.format(invoiceModel.getInvoiceDate())));
                parameters.put("quantity", String.valueOf(quantity));
                parameters.put("details", "");
                parameters.put("paymentReference", String.valueOf(0));
                parameters.put("vat", String.valueOf(totalVat));
                parameters.put("netSubtotal", String.valueOf(netSubtotal));
                parameters.put("netTotal", String.valueOf(netSubtotal.add(totalVat)));
                parameters.put("paymentDetails", String.valueOf(invoiceModel.getInvoiceText()));
                parameters.put("accountNumber", String.valueOf(invoiceModel.getContractPoNumber()));
                parameters.put("otherInformation", String.valueOf(0));

            } catch (Exception ex) {
                Logger.getLogger(InvoiceReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parameters;
    }

    public ArrayList<InvoiceDataSourceModel> getDataBeanList() {

        ArrayList<InvoiceDataSourceModel> dataBeanList = new ArrayList<InvoiceDataSourceModel>();

        InvoiceModel invoiceModel = null;

        if (invoiceId > 0) {

            Invoice invoice = invoiceService.findByPK(invoiceId);
            invoiceModel = new InvoiceModelHelper().getInvoiceModel(invoice);
            try {
                BigDecimal totalVat = new BigDecimal(0);
                BigDecimal netSubtotal = new BigDecimal(0);
                int quantity = 0;
                Double unitprice;
                Double vat = 0.0;
                Double subTotal;
                List<InvoiceItemModel> invoiceItemModelList = invoiceModel.getInvoiceItems();
                for (InvoiceItemModel invoiceItem : invoiceItemModelList) {
                    unitprice = Double.parseDouble((invoiceItem.getUnitPrice().toString()));
                    if (invoiceItem.getVatId().compareTo(new BigDecimal(0)) > 0) {
                        totalVat = (invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuatity())).divide(invoiceItem.getVatId()));
                        vat = Double.parseDouble((totalVat.toString()));
                    }
                    netSubtotal = invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuatity()));
                    subTotal = Double.parseDouble((netSubtotal.toString()));
                    quantity = invoiceItem.getQuatity();
                    dataBeanList.add(new InvoiceDataSourceModel(String.valueOf(quantity), invoiceItem.getDescription(), unitprice, vat, subTotal));
                }

            } catch (Exception ex) {
                Logger.getLogger(InvoiceReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dataBeanList;
    }

    public void prepareReport(HttpServletRequest request, HttpServletResponse response, int invoiceId) throws JRException, IOException {

        response.reset();
        response.setHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"fileName.pdf\"");
        this.invoiceId = invoiceId;
        Collection<InvoiceDataSourceModel> dataList = getDataBeanList();
        JRDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        String jasperFilePath = ReportConfigUtil.compileReport(getCompileDir(), getCompileFileName());
        File reportFile = new File(jasperFilePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), getReportParameters(), new JRBeanCollectionDataSource(dataList));
        request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

}
