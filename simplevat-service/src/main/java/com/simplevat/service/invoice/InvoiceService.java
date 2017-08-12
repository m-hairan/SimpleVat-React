package com.simplevat.service.invoice;

import java.util.Map;

import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.SimpleVatService;

/**
 *
 * @author Hiren
 */
public interface InvoiceService extends SimpleVatService<Integer, Invoice> {
	public Map<Object, Number> getInvoicePerMonth();
	public int getMaxValue(Map<Object, Number> data);
}
