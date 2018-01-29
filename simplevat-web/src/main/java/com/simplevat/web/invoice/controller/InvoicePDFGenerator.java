/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.invoice.controller;

import com.github.javaplugs.jsf.SpringScopeSession;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.simplevat.entity.Company;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.User;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.invoice.model.InvoiceModel;
import com.simplevat.web.utils.FacesUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@SpringScopeSession
public class InvoicePDFGenerator implements Serializable {

    @Autowired
    private InvoiceService invoiceService;
//    @Autowired
//    private InvoiceController invoiceController;
//    private StreamedContent invoicepdf;
    private ByteArrayOutputStream invoicepdfOutputStream;
    private Invoice invoice;
    private InvoiceModel invoiceModel;
    private User user;
    private Currency currency;
    private BaseFont baseFont;
    @Autowired
    private InvoiceModelHelper invoiceModelHelper;

    public void init() {
        Integer invoiceId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("invoiceId").toString());
        System.out.println("invoiceId in pdf generator :" + invoiceId);
        invoice = invoiceService.findByPK(invoiceId);
        invoiceModel = invoiceModelHelper.getInvoiceModel(invoice);
        user = FacesUtil.getLoggedInUser();
        currency = invoice.getCurrency();
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            baseFont = BaseFont.createFont(servletContext.getRealPath("/resources/ultima-layout/fonts/Roboto-Regular.ttf"), BaseFont.WINANSI, BaseFont.EMBEDDED);
            generatePDF();
        } catch (IOException ex) {
            Logger.getLogger(InvoicePDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(InvoicePDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultStreamedContent getInvoicepdf() {
        return new DefaultStreamedContent(new ByteArrayInputStream(invoicepdfOutputStream.toByteArray()), "application/pdf");
    }

    public void generatePDF() throws IOException, DocumentException {
//        OutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        document.setMargins(-30, -50, 10, 10);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        canvas.setRGBColorStroke(139, 195, 74);
        canvas.moveTo(0, 0);
        canvas.lineTo(0, 842);
        canvas.setLineWidth(60);
        canvas.closePathStroke();
        document.add(createFormHeaderBlock());
        document.add(createHeaderDetails());
        document.add(createInvoiceToAndShipToAddress());
        document.add(createInvoiceTable());
        document.add(createFooterText());
        document.close();
        invoicepdfOutputStream = byteArrayOutputStream;
    }

    private PdfPTable createFormHeaderBlock() {
        PdfPTable table = new PdfPTable(2);
        Image img = null;
        try {
            img = Image.getInstance(user.getCompany().getCompanyLogo());
            img.scaleAbsolute(120f, 60f);
        } catch (Exception ex) {
            Logger.getLogger(InvoicePDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell = new PdfPCell(new Phrase(""));
        if (img != null) {
            cell = new PdfPCell(img);
        }
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setPaddingTop(4);
        table.addCell(cell);
        PdfPTable innerTable = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("TAX INVOICE", new Font(baseFont, 20, Font.BOLD, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        innerTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("INVOICE " + invoiceModel.getInvoiceReferenceNumber(), new Font(baseFont, 15, Font.BOLD, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        innerTable.addCell(cell);
        cell = new PdfPCell(innerTable);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPaddingTop(20);
        cell.setBorder(0);
        table.addCell(cell);
        return table;
    }

    private PdfPTable createHeaderDetails() {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell = new PdfPCell(createCompanySubTitle());
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setPaddingTop(4);
        cell.setPaddingBottom(10);
        table.addCell(cell);
        cell = new PdfPCell(createCompanyAddressAndInvoiceDetails());
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setPaddingBottom(10);
        table.addCell(cell);
        return table;
    }

    private PdfPTable createCompanySubTitle() {
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Paragraph(user.getCompany().getCompanyName(),
                new Font(baseFont, 15, Font.BOLD, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setPaddingTop(4);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("",
                new Font(baseFont, 12, Font.BOLD, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setPaddingTop(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(getComapnyAddress(user.getCompany()),
                new Font(baseFont, 10, Font.NORMAL, new BaseColor(122, 120, 120))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(new BaseColor(139, 195, 74));
        cell.setPaddingTop(4);
        cell.setPaddingBottom(10);
        table.addCell(cell);
        return table;
    }

    private PdfPTable createCompanyAddressAndInvoiceDetails() {
        PdfPTable table = new PdfPTable(1);
        PdfPTable innerTable = new PdfPTable(1);
        innerTable.addCell(createLabel("Invoice Date:", new SimpleDateFormat("MM/dd/yyyy").format(Date.from(invoice.getInvoiceDate().atZone(ZoneId.systemDefault()).toInstant()))));
        innerTable.addCell(createLabel("Customer ID:", invoiceModel.getInvoiceContact().getContactId().toString()));
        innerTable.addCell(createLabel("Total Due Amount:", currency.getCurrencySymbol() + invoiceModel.getDueAmount().toString()));
        innerTable.addCell(createLabel("Payment Due By:", new SimpleDateFormat("MM/dd/yyyy").format(Date.from(invoice.getInvoiceDueDate().atZone(ZoneId.systemDefault()).toInstant()))));
        PdfPCell cell = new PdfPCell(innerTable);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(new BaseColor(139, 195, 74));
        cell.setPaddingBottom(10);
        table.addCell(cell);
        return table;
    }

    public PdfPCell createLabel(String label, String value) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(label, new Font(baseFont, 9, Font.BOLD, new BaseColor(122, 120, 120))));
        paragraph.add(" ");
        paragraph.add(new Chunk(value, new Font(baseFont, 9, Font.NORMAL, new BaseColor(122, 120, 120))));
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBorder(0);
        return cell;
    }

    private PdfPTable createInvoiceToAndShipToAddress() {
        Contact invoiceToContact = invoiceModel.getInvoiceContact();
        Contact shipToContact = invoiceModel.getShippingContact();
        if (shipToContact == null) {
            shipToContact = invoiceToContact;
        }
        PdfPTable table = new PdfPTable(2);

        PdfPCell cell = new PdfPCell(createInvoiceToAddress(invoiceToContact));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setPaddingLeft(0);
        table.addCell(cell);

        cell = new PdfPCell(createShipToAddress(shipToContact));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        cell.setPaddingLeft(0);
        cell.setPaddingBottom(20);
        table.addCell(cell);
        return table;
    }

    private PdfPTable createInvoiceToAddress(Contact invoiceToContact) {
        PdfPTable innerTable = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Paragraph("INVOICE TO:", new Font(baseFont, 15, Font.BOLD, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        innerTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(getContactFullName(invoiceToContact), new Font(baseFont, 12, Font.NORMAL, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        innerTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(getInvoiceHolderAddress(invoiceToContact), new Font(baseFont, 9, Font.NORMAL, new BaseColor(122, 120, 120))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        innerTable.addCell(cell);
        return innerTable;
    }

    private PdfPTable createShipToAddress(Contact shipToContact) {
        PdfPTable innerTable = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Paragraph("SHIP TO:", new Font(baseFont, 15, Font.BOLD, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        innerTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(getContactFullName(shipToContact), new Font(baseFont, 12, Font.NORMAL, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        innerTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(getInvoiceHolderAddress(shipToContact), new Font(baseFont, 9, Font.NORMAL, new BaseColor(122, 120, 120))));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        innerTable.addCell(cell);
        return innerTable;
    }

    private PdfPTable createInvoiceTable() {
        PdfPTable table = new PdfPTable(6);
        try {
            table.setWidths(new float[]{22, 30, 13, 16, 13, 16});
        } catch (DocumentException ex) {
            Logger.getLogger(InvoicePDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        String headCellValues[] = {"PRODUCT/SERVICE", "DESCRIPTION", "QUANTITY", "UNIT PRICE (" + currency.getCurrencyIsoCode() + ")", "VAT(%)", "SUB TOTAL (" + currency.getCurrencyIsoCode() + ")"};
        for (String value : headCellValues) {
            table.addCell(getTableHeader(value));
        }
        for (InvoiceLineItem invoiceLineItem : invoice.getInvoiceLineItems()) {
            table.addCell(getTableContent(invoiceLineItem.getInvoiceLineItemProductService().getProductName(), 1, Element.ALIGN_LEFT, 10));
            table.addCell(getTableContent(invoiceLineItem.getInvoiceLineItemProductService().getProductDescription(), 2, Element.ALIGN_LEFT, 10));
            table.addCell(getTableContent(invoiceLineItem.getInvoiceLineItemQuantity().toString(), 3, Element.ALIGN_CENTER, 0));
            table.addCell(getTableContent(currency.getCurrencySymbol() + invoiceLineItem.getInvoiceLineItemUnitPrice().toString(), 4, Element.ALIGN_RIGHT, 10));
            table.addCell(getTableContent(invoiceLineItem.getInvoiceLineItemVat() != null ? invoiceLineItem.getInvoiceLineItemVat().getVat().toString() : "0" + "%", 5, Element.ALIGN_RIGHT, 10));
            table.addCell(getTableContent(currency.getCurrencySymbol() + (invoiceLineItem.getInvoiceLineItemUnitPrice().multiply(new BigDecimal(invoiceLineItem.getInvoiceLineItemQuantity()))).toString(), 6, Element.ALIGN_RIGHT, 10));
        }
        table.addCell(getTableFooterBlankCell());
        table.addCell(getTableFooterLabelCell("Discount Percent:", 1));
        table.addCell(getTableFooterValueCell(getDecimalValue(invoiceModel.getDiscount()) + "%", 1));
        table.addCell(getTableFooterBlankCell());
        table.addCell(getTableFooterLabelCell("Total Discount:", 2));
        table.addCell(getTableFooterValueCell(currency.getCurrencySymbol() + getDecimalValue(invoiceModel.getCalculatedDiscountAmount()), 2));
        table.addCell(getTableFooterBlankCell());
        table.addCell(getTableFooterLabelCell("Total Net:", 3));
        table.addCell(getTableFooterValueCell(currency.getCurrencySymbol() + getDecimalValue(invoiceModel.getInvoiceSubtotal()), 3));
        table.addCell(getTableFooterBlankCell());
        table.addCell(getTableFooterLabelCell("Total VAT:", 4));
        table.addCell(getTableFooterValueCell(currency.getCurrencySymbol() + getDecimalValue(invoiceModel.getInvoiceVATAmount()), 4));
        table.addCell(getTableFooterBlankCell());
        table.addCell(getTableFooterLabelCell("Total Due:", 5));
        table.addCell(getTableFooterValueCell(currency.getCurrencySymbol() + getDecimalValue(invoiceModel.getDueAmount()), 5));
        return table;
    }

    private BigDecimal getDecimalValue(BigDecimal value) {
        if (value == null) {
            value = BigDecimal.ZERO;
        }
        return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private PdfPCell getTableHeader(String cellValue) {
        PdfPCell cell = new PdfPCell(new Paragraph(cellValue, new Font(baseFont, 9, Font.BOLD, new BaseColor(255, 255, 255))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setUseVariableBorders(true);
        cell.setBackgroundColor(new BaseColor(139, 195, 74));
        cell.setBorderColor(new BaseColor(255, 255, 255));
        cell.setPaddingBottom(5);
        cell.setPaddingTop(3);
        return cell;
    }

    private PdfPCell getTableContent(String cellValue, int cellPosition, int alignValue, int paddingValue) {
        PdfPCell cell = new PdfPCell(new Paragraph(cellValue, new Font(baseFont, 9, Font.NORMAL, new BaseColor(122, 120, 120))));
        cell.setUseVariableBorders(true);
        cell.setBackgroundColor(new BaseColor(232, 243, 219));
        cell.setBorderColorBottom(new BaseColor(255, 255, 255));
        cell.setBorderColorTop(new BaseColor(255, 255, 255));
        if (cellPosition == 1) {
            cell.setBorderColorLeft(new BaseColor(255, 255, 255));
            cell.setBorderColorRight(new BaseColor(232, 243, 219));
        } else if (cellPosition == 6) {
            cell.setBorderColorLeft(new BaseColor(232, 243, 219));
            cell.setBorderColorRight(new BaseColor(232, 243, 219));
        } else {
            cell.setBorderColorLeft(new BaseColor(232, 243, 219));
            cell.setBorderColorRight(new BaseColor(232, 243, 219));
        }
        cell.setHorizontalAlignment(alignValue);
        cell.setPaddingRight(alignValue == Element.ALIGN_RIGHT ? paddingValue : 0);
        cell.setPaddingLeft(alignValue == Element.ALIGN_LEFT ? paddingValue : 0);
        cell.setPaddingBottom(5);
        cell.setPaddingTop(3);
        return cell;
    }

    private PdfPCell getTableFooterBlankCell() {
        PdfPCell cell = new PdfPCell(new Paragraph());
        cell.setColspan(3);
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorderColor(new BaseColor(255, 255, 255));
        cell.setPaddingBottom(5);
        cell.setPaddingTop(3);
        return cell;
    }

    private PdfPCell getTableFooterLabelCell(String cellValue, int rowindex) {
        PdfPCell cell = new PdfPCell(new Paragraph(cellValue, new Font(baseFont, 9, Font.NORMAL, new BaseColor(122, 120, 120))));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setUseVariableBorders(true);
        cell.setBackgroundColor(new BaseColor(232, 243, 219));
        cell.setBorderColor(new BaseColor(232, 243, 219));
        if (rowindex == 1) {
            cell.setBorderColorTop(new BaseColor(255, 255, 255));
        }
        cell.setPaddingBottom(5);
        cell.setPaddingTop(3);
        return cell;
    }

    private PdfPCell getTableFooterValueCell(String cellValue, int rowindex) {
        PdfPCell cell = new PdfPCell(new Paragraph(cellValue, new Font(baseFont, 9, Font.NORMAL, new BaseColor(122, 120, 120))));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setUseVariableBorders(true);
        cell.setBackgroundColor(new BaseColor(232, 243, 219));
        cell.setBorderColor(new BaseColor(232, 243, 219));
        if (rowindex == 1) {
            cell.setBorderColorTop(new BaseColor(255, 255, 255));
        }
        cell.setPaddingRight(10);
//        cell.setPaddingTop(5);
        return cell;
    }

    private PdfPTable createFooterText() {
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Paragraph("Thank You!", new Font(baseFont, 15, Font.BOLD, new BaseColor(139, 195, 74))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setPaddingBottom(30);
        cell.setPaddingTop(20);
        table.addCell(cell);
        if (invoice.getInvoiceNotes() != null && !invoice.getInvoiceNotes().isEmpty()) {
            cell = new PdfPCell(new Paragraph("COMMENTS OR SPECIAL INSTRUCTIONS:", new Font(baseFont, 12, Font.BOLD, new BaseColor(122, 120, 120))));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            cell.setPaddingBottom(2);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(invoice.getInvoiceNotes(), new Font(baseFont, 10, Font.NORMAL, new BaseColor(122, 120, 120))));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            cell.setPaddingBottom(10);
            table.addCell(cell);
        }
        cell = new PdfPCell(new Paragraph("Invoice was created on a computer and is valid without the signature and seal.", new Font(baseFont, 10, Font.NORMAL, new BaseColor(122, 120, 120))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.TOP);
        cell.setBorderColor(new BaseColor(139, 195, 74));
        cell.setPaddingBottom(10);
        table.addCell(cell);
        return table;
    }

    private String getComapnyAddress(Company company) {
        StringBuilder companyAddress = new StringBuilder();
        if (company.getCompanyAddressLine1() != null && !company.getCompanyAddressLine1().isEmpty()) {
            companyAddress.append(company.getCompanyAddressLine1()).append("\n");
        }
        if (company.getCompanyAddressLine2() != null && !company.getCompanyAddressLine2().isEmpty()) {
            companyAddress.append(company.getCompanyAddressLine2()).append("\n");
        }
        if (company.getCompanyAddressLine3() != null && !company.getCompanyAddressLine3().isEmpty()) {
            companyAddress.append(company.getCompanyAddressLine3()).append("\n");
        }
        if (company.getCompanyCity() != null && !company.getCompanyCity().isEmpty()) {
            companyAddress.append(company.getCompanyCity()).append(", ");
        }
        if (company.getCompanyStateRegion() != null && !company.getCompanyStateRegion().isEmpty()) {
            companyAddress.append(company.getCompanyStateRegion()).append("\n");
        }
        if (company.getCompanyCountryCode() != null) {
            companyAddress.append(company.getCompanyCountryCode().getCountryName()).append(", ");
        }
        if (company.getCompanyPostZipCode() != null && !company.getCompanyPostZipCode().isEmpty()) {
            companyAddress.append(company.getCompanyPostZipCode()).append("\nVAT: ");
        }
        if (company.getVatRegistrationNumber() != null && !company.getVatRegistrationNumber().isEmpty()) {
            companyAddress.append(company.getVatRegistrationNumber());
        }
        return companyAddress.toString();
    }

    private String getInvoiceHolderAddress(Contact contact) {
        StringBuilder invoiceHolderAddress = new StringBuilder();
        if (contact.getInvoicingAddressLine1() != null && !contact.getInvoicingAddressLine1().isEmpty()) {
            invoiceHolderAddress.append(contact.getInvoicingAddressLine1()).append("\n");
        }
        if (contact.getInvoicingAddressLine2() != null && !contact.getInvoicingAddressLine2().isEmpty()) {
            invoiceHolderAddress.append(contact.getInvoicingAddressLine2()).append("\n");
        }
        if (contact.getInvoicingAddressLine3() != null && !contact.getInvoicingAddressLine3().isEmpty()) {
            invoiceHolderAddress.append(contact.getInvoicingAddressLine3()).append("\n");
        }
        if (contact.getCity() != null && !contact.getCity().isEmpty()) {
            invoiceHolderAddress.append(contact.getCity()).append(", ");
        }
        if (contact.getStateRegion() != null && !contact.getStateRegion().isEmpty()) {
            invoiceHolderAddress.append(contact.getStateRegion()).append("\n");
        }
        if (contact.getCountry() != null) {
            invoiceHolderAddress.append(contact.getCountry().getCountryName()).append(", ");
        }
        if (contact.getPostZipCode() != null && !contact.getPostZipCode().isEmpty()) {
            invoiceHolderAddress.append(contact.getPostZipCode());
        }
        return invoiceHolderAddress.toString();
    }

    private String getContactFullName(Contact contact) {
        StringBuilder fullName = new StringBuilder();
        if (contact.getFirstName() != null) {
            fullName.append(contact.getFirstName()).append(" ");
        }
        if (contact.getMiddleName() != null) {
            fullName.append(contact.getMiddleName()).append(" ");
        }
        if (contact.getLastName() != null) {
            fullName.append(contact.getLastName()).append("\n");
        } else {
            fullName.append("\n");
        }
        return fullName.toString();
    }

    public ByteArrayOutputStream getInvoicepdfOutputStream() {
        return invoicepdfOutputStream;
    }

    public void setInvoicepdfOutputStream(ByteArrayOutputStream invoicepdfOutputStream) {
        this.invoicepdfOutputStream = invoicepdfOutputStream;
    }

}
