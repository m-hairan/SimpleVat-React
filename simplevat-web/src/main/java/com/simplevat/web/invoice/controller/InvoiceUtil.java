/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.invoice.controller;

import com.simplevat.entity.Company;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.CompanyService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.invoice.model.InvoiceItemModel;
import com.simplevat.web.invoice.model.InvoiceModel;
import com.simplevat.web.reports.AbstractReportBean;
import com.simplevat.web.reports.ReportConfigUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author uday
 */
@Component
public class InvoiceUtil extends AbstractReportBean {

    private final String COMPILE_FILE_NAME = "Invoice";
    private int invoiceId;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserServiceNew userServiceNew;

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
            Currency currency = currencyService.findByPK(invoice.getCurrency().getCurrencyCode());
            invoiceModel = new InvoiceModelHelper().getInvoiceModel(invoice);
            Company company = companyService.findByPK(userServiceNew.findByPK(invoice.getCreatedBy()).getCompany().getCompanyId());
            try {
                BigDecimal totalVat = new BigDecimal(0);
                BigDecimal netSubtotal = new BigDecimal(0);
                BigDecimal vatPercentage = new BigDecimal(0);//changes
                int quantity = 0;
                List<InvoiceItemModel> invoiceItemModelList = invoiceModel.getInvoiceLineItems();
                for (InvoiceItemModel invoiceItem : invoiceItemModelList) {
                    if (invoiceItem.getVatId() != null) {  //changes
                        vatPercentage = invoiceItem.getVatId().getVat();  //changes
                    }
                    if (vatPercentage.compareTo(new BigDecimal(0)) > 0) {
                        totalVat = totalVat.add((invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuatity()))).divide(new BigDecimal(vatPercentage.doubleValue())));
                    }
                    netSubtotal = netSubtotal.add(invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuatity())));
                    quantity += invoiceItem.getQuatity();
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                parameters.put("companyName", company.getCompanyName());
                if (company.getCompanyLogo() != null) {
                    parameters.put("CompanyImage", new ByteArrayInputStream(company.getCompanyLogo()));
                }
                parameters.put("companyAddress", getComapnyAddress(company));
                parameters.put("invoiceHolderAddress", getInvoiceHolderAddress(invoiceModel.getInvoiceContact()));
                parameters.put("invoiceNumber", "INVOICE " + invoiceModel.getInvoiceReferenceNumber());
                parameters.put("invoiceDate", String.valueOf(dateFormat.format(invoiceModel.getInvoiceDate())));
                if (invoiceModel.getInvoiceDueDate() != null) {
                    parameters.put("paymentDueDate", String.valueOf(dateFormat.format(invoiceModel.getInvoiceDueDate())));
                } else {
                    parameters.put("paymentDueDate", "");
                }
                parameters.put("notes", invoiceModel.getInvoiceNotes());
                parameters.put("CurrencySymbol", currency.getCurrencySymbol());
                parameters.put("CurrencyISOCode", currency.getCurrencyIsoCode());
                parameters.put("quantity", String.valueOf(quantity));
                parameters.put("details", "");
                parameters.put("paymentReference", invoiceModel.getInvoiceReferenceNumber());
                parameters.put("vat", String.valueOf(totalVat));
                parameters.put("netSubtotal", String.valueOf(netSubtotal));
                parameters.put("netTotal", String.valueOf(netSubtotal.add(totalVat)));
                parameters.put("paymentDetails", "");
                if (invoiceModel.getContractPoNumber() != null) {
                    parameters.put("accountNumber", String.valueOf(invoiceModel.getContractPoNumber()));
                } else {
                    parameters.put("accountNumber", "");
                }
                parameters.put("otherInformation", company.getCompanyRegistrationNumber());

            } catch (Exception ex) {
                Logger.getLogger(InvoiceUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parameters;
    }

    private String getComapnyAddress(Company company) {
        StringBuilder companyAddress = new StringBuilder();
        if (company.getCompanyAddressLine1() != null) {
            companyAddress.append(company.getCompanyAddressLine1()).append("\n");
        }
        if (company.getCompanyAddressLine2() != null) {
            companyAddress.append(company.getCompanyAddressLine2()).append("\n");
        }
        if (company.getCompanyAddressLine3() != null) {
            companyAddress.append(company.getCompanyAddressLine3()).append(", ");
        }
        if (company.getCompanyCity() != null) {
            companyAddress.append(company.getCompanyCity()).append("\n");
        }
        if (company.getCompanyStateRegion() != null) {
            companyAddress.append(company.getCompanyStateRegion()).append(", ");
        }
        if (company.getCompanyCountryCode() != null) {
            companyAddress.append(company.getCompanyCountryCode().getCountryName()).append(", ");
        }
        if (company.getCompanyPostZipCode() != null) {
            companyAddress.append(company.getCompanyPostZipCode()).append("\nVAT: ");
        }
        if (company.getVatRegistrationNumber() != null) {
            companyAddress.append(company.getVatRegistrationNumber());
        }
        return companyAddress.toString();
    }

    private String getInvoiceHolderAddress(Contact contact) {
        StringBuilder invoiceHolderAddress = new StringBuilder();
        if (contact.getFirstName() != null) {
            invoiceHolderAddress.append(contact.getFirstName()).append(" ");
        }
        if (contact.getMiddleName() != null) {
            invoiceHolderAddress.append(contact.getMiddleName()).append(" ");
        }
        if (contact.getLastName() != null) {
            invoiceHolderAddress.append(contact.getLastName()).append("\n");
        } else {
            invoiceHolderAddress.append("\n");
        }
        if (contact.getInvoicingAddressLine1() != null) {
            invoiceHolderAddress.append(contact.getInvoicingAddressLine1()).append(", ");
        }
        if (contact.getInvoicingAddressLine2() != null) {
            invoiceHolderAddress.append(contact.getInvoicingAddressLine2()).append(", ");
        }
        if (contact.getInvoicingAddressLine3() != null) {
            invoiceHolderAddress.append(contact.getInvoicingAddressLine3()).append("\n");
        } else {
            invoiceHolderAddress.append("\n");
        }
        if (contact.getCity() != null) {
            invoiceHolderAddress.append(contact.getCity()).append(", ");
        }
        if (contact.getStateRegion() != null) {
            invoiceHolderAddress.append(contact.getStateRegion()).append("\n");
        } else {
            invoiceHolderAddress.append("\n");
        }
        if (contact.getCountry() != null) {
            invoiceHolderAddress.append(contact.getCountry().getCountryName()).append(", ");
        }
        if (contact.getPostZipCode() != null) {
            invoiceHolderAddress.append(contact.getPostZipCode());
        }
        return invoiceHolderAddress.toString();
    }

    public ArrayList<InvoiceDataSourceModel> getDataBeanList() {

        ArrayList<InvoiceDataSourceModel> dataBeanList = new ArrayList<InvoiceDataSourceModel>();

        InvoiceModel invoiceModel = null;

        if (invoiceId > 0) {

            Invoice invoice = invoiceService.findByPK(invoiceId);
            invoiceModel = new InvoiceModelHelper().getInvoiceModel(invoice);
            try {
                int quantity = 0;
                BigDecimal unitprice = new BigDecimal(0);
                BigDecimal vat = new BigDecimal(0);
                BigDecimal subTotal = new BigDecimal(0);
                BigDecimal vatPercentage = new BigDecimal(0);//changes
                String productServiceName = "";//changes

                List<InvoiceItemModel> invoiceItemModelList = invoiceModel.getInvoiceLineItems();
                for (InvoiceItemModel invoiceItem : invoiceItemModelList) {
                    unitprice = invoiceItem.getUnitPrice();
                    if (invoiceItem.getVatId() != null) {  //changes
                        vatPercentage = invoiceItem.getVatId().getVat();  //changes
                    }
                    if (vatPercentage.compareTo(new BigDecimal(0)) > 0) {
                        vat = (invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuatity()))).divide(new BigDecimal(vatPercentage.doubleValue()));   //changes
                    }
                    subTotal = invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuatity()));
                    quantity = invoiceItem.getQuatity();
                    if (invoiceItem.getProductService() != null) { //changes
                        productServiceName = invoiceItem.getProductService().getProductName(); //changes
                    }
                    dataBeanList.add(new InvoiceDataSourceModel(String.valueOf(quantity), productServiceName, invoiceItem.getDescription(), unitprice, vat, subTotal));
                }

            } catch (Exception ex) {
                Logger.getLogger(InvoiceUtil.class.getName()).log(Level.SEVERE, null, ex);
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

    public void prepareHtmlReport(HttpServletRequest request, HttpServletResponse response, int invoiceId) throws JRException, IOException {
        response.reset();
        response.setHeader("Content-Type", "application/html");
        response.setHeader("Content-Disposition", "inline; filename=\"fileName.html\"");
        this.invoiceId = invoiceId;
        Collection<InvoiceDataSourceModel> dataList = getDataBeanList();
        JRDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        String jasperFilePath = ReportConfigUtil.compileReport(getCompileDir(), getCompileFileName());
        File reportFile = new File(jasperFilePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), getReportParameters(), new JRBeanCollectionDataSource(dataList));
        BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, 0, 2f);
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    public ByteArrayOutputStream prepareMailReport(ByteArrayOutputStream outputStream, int invoiceId) throws JRException, IOException {
        this.invoiceId = invoiceId;
        Collection<InvoiceDataSourceModel> dataList = getDataBeanList();
        JRDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        String jasperFilePath = ReportConfigUtil.compileReport(getCompileDir(), getCompileFileName());
        File reportFile = new File(jasperFilePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), getReportParameters(), new JRBeanCollectionDataSource(dataList));
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream;
    }
}
