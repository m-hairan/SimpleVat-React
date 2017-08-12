package com.simplevat.test.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.test.common.BaseManagerTest;

public class InvoiceDaoImplTest extends BaseManagerTest {
	
	@Autowired
	InvoiceDao invoiceDao;
	
	@Test
	public void testGetInvocePerMonth() {
		invoiceDao.getInvocePerMonth(new Date(), new Date());
		
	}

}
