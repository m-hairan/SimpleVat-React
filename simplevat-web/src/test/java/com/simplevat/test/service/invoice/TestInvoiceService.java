package com.simplevat.test.service.invoice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.service.report.model.BankAccountTransactionReportModel;
import com.simplevat.test.common.BaseManagerTest;
import com.simplevat.util.ChartUtil;

/**
 *
 * @author hiren
 */
public class TestInvoiceService extends BaseManagerTest {
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	ExpenseService expenseService;
    
    @Autowired
    ChartUtil util;
    
    
    @Test
    @Ignore
    public void testSaveInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCreatedDate(LocalDateTime.now());
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceDueOn(7);
//        invoice.setContactFullName("Hiren");
        invoice.setContractPoNumber("PO-2342");
        invoice.setInvoiceDiscount(new BigDecimal(50));
//        invoice.setInvoiceDiscountType(DiscountType.ABSOLUTE);
        invoice.setInvoiceReferenceNumber("INV2323");
        invoice.setInvoiceLineItems(getLineItems(invoice.getInvoiceId()));
        invoice.setDeleteFlag(Boolean.FALSE);
        invoiceService.persist(invoice,invoice.getInvoiceId());
    }
    
    private List<InvoiceLineItem> getLineItems(int invoiceId) {
        List<InvoiceLineItem> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            InvoiceLineItem item = new InvoiceLineItem();
            item.setInvoiceLineItemDescription("desc" + i);
            item.setInvoiceLineItemQuantity(5 + i);
            item.setInvoiceLineItemUnitPrice(new BigDecimal(50 * i));
            item.setInvoiceLineItemVat(new BigDecimal(10));
            items.add(item);
        }
        return items;
    }
    @Ignore
    @Test
    public void testGetInvoicesForReport() {
    	List<BankAccountTransactionReportModel> list = 
    			invoiceService.getInvoicesForRepots(util.getStartDate(Calendar.YEAR,-1).getTime(),util.getEndDate().getTime());
    	assertNotNull(list);
    	assertTrue("Size should be greater than zero ", list.size() > 0);
    }
    @Ignore
    @Test
    public void testGetExpenseForReport() {
    	List<BankAccountTransactionReportModel> list = 
    			expenseService.getExpensesForReport(util.getStartDate(Calendar.YEAR,-1).getTime(),util.getEndDate().getTime());
    	assertNotNull(list);
    	assertTrue("Size should be greater than zero ", list.size() > 0);
    }
    
}
