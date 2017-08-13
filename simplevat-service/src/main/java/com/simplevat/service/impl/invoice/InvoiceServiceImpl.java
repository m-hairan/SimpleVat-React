package com.simplevat.service.impl.invoice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.invoice.InvoiceDao;
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
		List<Object[]> rows = dao.getInvocePerMonth(util.getStartDate().getTime(),util.getEndDate().getTime());
		return util.getCashMap(rows);
	}
	@Override
	public int getMaxValue(Map<Object, Number> data) {
		return util.getMaxValue(data);
	}

	@Override
	public Map<Object, Number> getVatInPerMonth() {
		List<Object[]> rows = dao.getVatInPerMonth(util.getStartDate().getTime(),util.getEndDate().getTime());
		return util.getCashMap(rows);
	}

}
