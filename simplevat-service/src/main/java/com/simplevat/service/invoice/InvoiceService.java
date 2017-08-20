package com.simplevat.service.invoice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.entity.Event;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.SimpleVatService;

/**
 *
 * @author Hiren
 */
public abstract class InvoiceService extends SimpleVatService<Integer, Invoice> {
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	public abstract  Map<Object, Number> getInvoicePerMonth();
	
	public abstract  int getMaxValue(Map<Object, Number> data);
	
	public abstract  Map<Object, Number> getVatInPerMonth();
	
	public abstract  int getVatInQuartly();
	
	public abstract  List<Event> getInvoiceAsEvent();
	
	@Override
	protected InvoiceDao getDao() {
		return invoiceDao;
	}
}
