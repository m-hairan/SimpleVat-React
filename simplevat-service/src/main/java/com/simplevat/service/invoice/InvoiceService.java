package com.simplevat.service.invoice;

import java.util.List;
import java.util.Map;

import com.simplevat.entity.Event;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.SimpleVatService;

/**
 *
 * @author Hiren
 */
public interface InvoiceService extends SimpleVatService<Integer, Invoice> {
	
	public Map<Object, Number> getInvoicePerMonth();
	
	public int getMaxValue(Map<Object, Number> data);
	
	public Map<Object, Number> getVatInPerMonth();
	
	public int getVatInQuartly();
	
	public List<Event> getInvoiceAsEvent();
}
