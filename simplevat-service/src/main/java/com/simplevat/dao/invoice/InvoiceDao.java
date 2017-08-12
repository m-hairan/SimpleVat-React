package com.simplevat.dao.invoice;

import java.util.Date;
import java.util.List;

import com.simplevat.dao.Dao;
import com.simplevat.entity.invoice.Invoice;

/**
 *
 * @author hiren
 * @since 20 May, 2017 7:34:45 PM
 */
public interface InvoiceDao extends Dao<Integer, Invoice> {
	
	public List<Object[]> getInvocePerMonth(Date startDate, Date endDate);

}
