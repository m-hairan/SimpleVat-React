package com.simplevat.dao.invoice;

import com.simplevat.dao.AbstractDao;
import com.simplevat.entity.invoice.Invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Hiren
 */
@Repository
public class InvoiceDaoImpl extends AbstractDao<Integer, Invoice> implements InvoiceDao{

	@Override
	public List<Object[]> getInvocePerMonth(Date startDate, Date endDate) {
		List<Object[]> invoices = new ArrayList<>(0);
		try {
			String queryString = "select "
					+ "sum(li.invoiceLineItemUnitPrice*li.invoiceLineItemQuantity) as invoiceTotal, "
					//+ "sum(li.invoiceLineItemVat*li.invoiceLineItemQuantity) as vatIn, "
					+ "CONCAT(MONTH(i.invoiceDate),' ' , Year(i.invoiceDate)) as month "
					+ "from Invoice i JOIN i.invoiceLineItems li "
					+ "where i.deleteFlag = 'false' and li.deleteFlag= 'false' "
					+ "and i.invoiceDate BETWEEN :startDate AND :endDate "
					+ "group by CONCAT(MONTH(i.invoiceDate),' ' , Year(i.invoiceDate))";

			Query query = getEntityManager().createQuery(queryString)
					.setParameter("startDate", startDate, TemporalType.DATE)
					.setParameter("endDate", endDate, TemporalType.DATE);
			invoices = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invoices;
	}

    
}
