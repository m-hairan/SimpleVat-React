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
	
	/**
	 * This methods take date duration and calculate Invoices per month wise
	 * @param startDate
	 * @param endDate
	 * @return List Of Invoice, month wise
	 */
	public List<Object[]> getInvocePerMonth(Date startDate, Date endDate);
	
	/**
	 * This methods take date duration and calculate VatIn per month wise
	 * @param startDate
	 * @param endDate
	 * @return List Of Invoice, month wise
	 */
	public List<Object[]> getVatInPerMonth(Date startDate, Date endDate);
	

}
