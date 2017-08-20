package com.simplevat.service.impl.invoice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.entity.Activity;
import com.simplevat.entity.ActivityId;
import com.simplevat.entity.Event;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.entity.invoice.InvoiceLineItem;
import com.simplevat.service.ActivityService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.util.ChartUtil;

/**
 *
 * @author Hiren
 */
@Service
@Transactional
public class InvoiceServiceImpl extends InvoiceService {

	
	private static final String MODULE_CODE = "Invoice";
	
    @Autowired
    public InvoiceDao dao;
    
    @Autowired
    ChartUtil util;
    
/*    @Autowired
    ActivityService activityService;*/

    @Override
    public InvoiceDao getDao() {
        return dao;
    }
    
    @Override
    public Invoice update(Invoice invoice) {
    	Invoice updatedInvoice = getDao().update(invoice);
    	//activityService.logActivity(getActivityInstance(invoice));
    	return updatedInvoice;
    }

	@Override
	public Map<Object, Number> getInvoicePerMonth() {
		List<Object[]> rows = dao.getInvocePerMonth(util.getStartDate(Calendar.YEAR,-1).getTime(),util.getEndDate().getTime());
		return util.getCashMap(rows);
	}
	@Override
	public int getMaxValue(Map<Object, Number> data) {
		return util.getMaxValue(data);
	}

	@Override
	public Map<Object, Number> getVatInPerMonth() {
		List<Object[]> rows = dao.getVatInPerMonth(util.getStartDate(Calendar.YEAR,-1).getTime(),util.getEndDate().getTime());
		return util.getCashMap(rows);
	}

	@Override
	public int getVatInQuartly() {
		List<Object[]> rows =  dao.getVatInPerMonth(util.getStartDate(Calendar.MONTH,-4).getTime(),util.getEndDate().getTime());
		return util.addAmount(rows);
	}

	@Override
	public List<Event> getInvoiceAsEvent() {
		List<Object[]> rows =  dao.getInvoiceDue(util.getStartDate(Calendar.MONTH,-6).getTime(), util.getStartDate(Calendar.MONTH,6).getTime());
		return convertEvents(rows);
	}
	
	private List<Event> convertEvents(List<Object[]> rows) {
		List<Event> events = new ArrayList<Event>();
		for(Object[] object : rows) {
			String invoiceRefNumber = (String)object[0];
			String invoiceText = (String)object[1];
			Date invoiceDate = util.localeDateTimeToDate((LocalDateTime)object[2]);
			Date invoiceDueDate = new Date(invoiceDate.getTime());
			int invoiceDue = (Integer)object[3];
			Event event = new Event();
			event.setAllDay(false);
			event.setTitle("Invoice Due Start Date " + invoiceRefNumber);
			event.setDescription(invoiceText);
			event.setStartDate(invoiceDate);
			event.setEndDate(util.modifyDate(invoiceDate, Calendar.MINUTE, 30));
			events.add(event);
			
			event = new Event();
			event.setAllDay(false);
			event.setTitle("Invoice Due End Date " + invoiceRefNumber);
			event.setDescription(invoiceText);
			Date temp1 = util.modifyDate(invoiceDueDate, Calendar.DAY_OF_YEAR, invoiceDue);
			Date temp2 = util.modifyDate(temp1, Calendar.MINUTE, 30);
			event.setStartDate(temp1);
			event.setEndDate(temp2);
			events.add(event);
		}
		
		return events;
	}
	
	
	private Activity getActivityInstance(Invoice invoice) {
		Activity activity = new Activity();
		activity.setActivityId(new ActivityId(MODULE_CODE, "UPDATE"));
		Collection<InvoiceLineItem> lineItems = invoice.getInvoiceLineItems();
		long amount = 0;
		for(InvoiceLineItem lineItem : lineItems) {
			amount = lineItem.getInvoiceLineItemQuantity()*lineItem.getInvoiceLineItemUnitPrice().longValue();
		}
		String currency = invoice.getCurrency().getCurrencySymbol();
		String field1 = currency + amount;
		
		String field2 = "Invoice Updated ";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
		String field3 = "Invoice Date " + invoice.getInvoiceDate().format(formatter);
		activity.setField1(field1);
		activity.setField2(field2);
		activity.setField3(field3);
		return activity;
	}

}
