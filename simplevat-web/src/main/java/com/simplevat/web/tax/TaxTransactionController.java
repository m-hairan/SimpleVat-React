/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.tax;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Purchase;
import com.simplevat.entity.PurchaseLineItem;
import com.simplevat.entity.TaxTransaction;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.service.PurchaseService;
import com.simplevat.service.TaxTransactionService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.constant.TaxTransactionStatusConstant;
import com.simplevat.web.constant.TransactionCreditDebitConstant;
import com.simplevat.web.constant.TransactionRefrenceTypeConstant;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TaxTransactionController implements Serializable {

    @Autowired
    private TaxTransactionService taxTransactionService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private InvoiceService invoiceService;

    @Getter
    private Currency currency;

    @Getter
    @Setter
    private List<TaxTransaction> taxTransactionList = new ArrayList<>();

    @Getter
    @Setter
    private TaxTransaction taxTransaction;

    private List<Transaction> creditTransactionList = new ArrayList<>();
    private List<Transaction> debitTransactionList = new ArrayList<>();

    private BigDecimal vatIn = new BigDecimal(0);
    private BigDecimal vatOut = new BigDecimal(0);

    @PostConstruct
    public void init() {
        currency = FacesUtil.getLoggedInUser().getCompany().getCompanyCountryCode().getCurrencyCode();
        taxTransactionList = taxTransactionService.getOpenTaxTransactionList();
        Date startDate = getStartDate();
        Date endDate = getEndDate();
        if (!isTaxTransactionExist(startDate, endDate)) {
            separateTransactionCrediTAndDebit();
            calculateTaxPerMonth(startDate, endDate);
        }
        taxTransaction = new TaxTransaction();
    }

    public boolean isTaxTransactionExist(Date startDate, Date endDate) {
        boolean matched = false;
        for (TaxTransaction tax : taxTransactionList) {
            if (tax.getStartDate().compareTo(startDate) == 0 && tax.getEndDate().compareTo(endDate) == 0) {
                matched = true;
            }
        }
        for (TaxTransaction tax : taxTransactionService.getClosedTaxTransactionList()) {
            if (tax.getStartDate().compareTo(startDate) == 0 && tax.getEndDate().compareTo(endDate) == 0) {
                if (tax.getDueAmount().doubleValue() == 0) {
                    matched = true;
                }
            }
        }
        return matched;
    }

    private void separateTransactionCrediTAndDebit() {
        List<Transaction> transactionList = transactionService.getAllTransactions();
        if (transactionList != null) {
            List<Transaction> parentList = new ArrayList<>();

            for (Transaction transaction : transactionList) {
                if (transaction.getParentTransaction() != null) {
                    parentList.add(transaction);
                }
            }
            transactionList.removeAll(parentList);
            getCreditTransactionList(transactionList);
            getDebitTransactionList(transactionList);
        }
    }

    private void calculateTaxPerMonth(Date startDate, Date endDate) {
        TaxTransaction taxTransaction = new TaxTransaction();
        taxTransaction.setStartDate(startDate);
        taxTransaction.setEndDate(endDate);
        for (Transaction transaction : creditTransactionList) {
            Date transDate = Date.from(transaction.getTransactionDate().atZone(ZoneId.systemDefault()).toInstant());
            if (transDate.compareTo(startDate) >= 0 && transDate.compareTo(endDate) <= 0) {
                if (transaction.getReferenceId() != null) {
                    vatIn = vatIn.add(getVatFromTransaction(transaction));
                }
            }
        }
        for (Transaction transaction : debitTransactionList) {
            Date transactionDate = Date.from(transaction.getTransactionDate().atZone(ZoneId.systemDefault()).toInstant());
            if (transactionDate.compareTo(startDate) >= 0 && transactionDate.compareTo(endDate) <= 0) {
                if (transaction.getReferenceId() != null) {
                    vatOut = vatOut.add(getVatFromTransaction(transaction));
                }
            }
        }

        taxTransaction.setVatIn(vatIn);
        taxTransaction.setVatOut(vatOut);
        taxTransaction.setStatus(TaxTransactionStatusConstant.OPEN);
        taxTransactionList.add(taxTransaction);
        // TODO Invoice Vat Calculation Depending on Vat percentage
    }

    private BigDecimal getVatFromTransaction(Transaction transaction) {
        Integer refId = transaction.getReferenceId();
        BigDecimal totalVat = BigDecimal.ZERO;
        BigDecimal vatPercent = BigDecimal.ZERO;;
        if (transaction.getReferenceType() == TransactionRefrenceTypeConstant.INVOICE) {
            Invoice invoice = invoiceService.findByPK(refId);
            for (InvoiceLineItem invoiceLineItem : invoice.getInvoiceLineItems()) {
                BigDecimal totalAmount = invoiceLineItem.getInvoiceLineItemUnitPrice().multiply(new BigDecimal(invoiceLineItem.getInvoiceLineItemQuantity()));
                if (invoiceLineItem.getInvoiceLineItemVat() != null) {
                    vatPercent = invoiceLineItem.getInvoiceLineItemVat().getVat();
                }
                totalVat = (totalAmount.multiply(vatPercent)).divide(new BigDecimal(100));
            }
        }
        if (transaction.getReferenceType() == TransactionRefrenceTypeConstant.PURCHASE) {
            Purchase purchase = purchaseService.findByPK(refId);
            for (PurchaseLineItem purchaseLineItem : purchase.getPurchaseLineItems()) {
                BigDecimal totalAmount = purchaseLineItem.getPurchaseLineItemUnitPrice().multiply(new BigDecimal(purchaseLineItem.getPurchaseLineItemQuantity()));
                if (purchaseLineItem.getPurchaseLineItemVat() != null) {
                    vatPercent = purchaseLineItem.getPurchaseLineItemVat().getVat();
                }
                totalVat = (totalAmount.multiply(vatPercent)).divide(new BigDecimal(100));
            }
        }
        return totalVat;
    }

    private void getCreditTransactionList(List<Transaction> transactionList) {
        for (Transaction transaction : transactionList) {
            if (transaction.getDebitCreditFlag() == TransactionCreditDebitConstant.CREDIT) {
                creditTransactionList.add(transaction);
            }
        }
    }

    private void getDebitTransactionList(List<Transaction> transactionList) {
        for (Transaction transaction : transactionList) {
            if (transaction.getDebitCreditFlag() == TransactionCreditDebitConstant.DEBIT) {
                debitTransactionList.add(transaction);
            }
        }
    }

    public String save() {
        TaxTransaction taxTransaction1 = null;
        BigDecimal dueAmountBeforePayment = null;
        if (taxTransaction.getDueAmount() == null) {
            dueAmountBeforePayment = taxTransaction.getVatIn().subtract(taxTransaction.getVatOut());
        } else {
            dueAmountBeforePayment = taxTransaction.getDueAmount();
        }
        if (taxTransaction.getPaidAmount().doubleValue() < dueAmountBeforePayment.doubleValue()) {
            taxTransaction1 = new TaxTransaction();
            BigDecimal dueAmount = dueAmountBeforePayment.subtract(taxTransaction.getPaidAmount());
            createNewTaxTransaction(taxTransaction1, dueAmount);
        } else {
            taxTransaction.setStatus(TaxTransactionStatusConstant.CLOSE);
            taxTransaction.setDueAmount(new BigDecimal(0));
            taxTransaction.setPaymentDate(new Date());
        }
        if (taxTransaction1 != null) {
            taxTransactionService.persist(taxTransaction1);
        }
        if (taxTransaction.getTaxTransactionId() == null) {
            taxTransaction.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
            taxTransaction.setCreatedDate(LocalDateTime.now());
            taxTransactionService.persist(taxTransaction);
        } else {
            taxTransactionService.update(taxTransaction);
        }
        return "index?faces-redirect=true";
    }

    private void createNewTaxTransaction(TaxTransaction taxTransaction1, BigDecimal dueAmount) {
        taxTransaction1.setStartDate(taxTransaction.getStartDate());
        taxTransaction1.setEndDate(taxTransaction.getEndDate());
        taxTransaction1.setVatIn(taxTransaction.getVatIn());
        taxTransaction1.setVatOut(taxTransaction.getVatOut());
        taxTransaction1.setStatus(TaxTransactionStatusConstant.CLOSE);
        taxTransaction1.setDueAmount(dueAmount);
        taxTransaction1.setPaidAmount(taxTransaction.getPaidAmount());
        taxTransaction1.setPaymentDate(new Date());
        taxTransaction1.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
        taxTransaction1.setCreatedDate(LocalDateTime.now());
        taxTransaction.setDueAmount(dueAmount);
        taxTransaction.setPaymentDate(new Date());
        taxTransaction.setStatus(TaxTransactionStatusConstant.OPEN);
    }

    public Date getStartDate() {
        try {
            Calendar prevYear = Calendar.getInstance();
            prevYear.set(Calendar.DAY_OF_MONTH, 1);
            prevYear.set(Calendar.HOUR_OF_DAY, 0);
            prevYear.set(Calendar.MINUTE, 0);
            prevYear.set(Calendar.SECOND, 0);
            prevYear.set(Calendar.MILLISECOND, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateWithoutTime = sdf.parse(sdf.format(prevYear.getTime()));
            return dateWithoutTime;
        } catch (ParseException ex) {
            Logger.getLogger(TaxTransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }

    public Date getEndDate() {
        try {
            Calendar preMonth = Calendar.getInstance();
            preMonth.set(Calendar.DAY_OF_MONTH, 1);
            preMonth.add(Calendar.MONTH, 1);
            preMonth.add(Calendar.DAY_OF_MONTH, -1);
            preMonth.set(Calendar.HOUR_OF_DAY, 0);
            preMonth.set(Calendar.MINUTE, 0);
            preMonth.set(Calendar.SECOND, 0);
            preMonth.set(Calendar.MILLISECOND, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateWithoutTime = sdf.parse(sdf.format(preMonth.getTime()));
            return dateWithoutTime;
        } catch (ParseException ex) {
            Logger.getLogger(TaxTransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }

}
