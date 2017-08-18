package com.simplevat.service.impl.invoice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.invoice.InvoiceDao;
import com.simplevat.entity.Event;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.util.ChartUtil;

/**
 *
 * @author Hiren
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    public InvoiceDao dao;
    
    @Autowired
    ChartUtil util;

    @Override
    public InvoiceDao getDao() {
        return dao;
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

}
