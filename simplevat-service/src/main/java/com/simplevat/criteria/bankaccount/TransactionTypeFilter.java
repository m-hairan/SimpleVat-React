package com.simplevat.criteria.bankaccount;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.BooleanUtils;

import com.simplevat.criteria.AbstractCriteria;
import com.simplevat.criteria.SortOrder;
import com.simplevat.dao.AbstractFilter;
import com.simplevat.entity.bankaccount.TransactionType;

public class TransactionTypeFilter extends AbstractFilter<TransactionType> {

	private TransactionTypeCriteria transactionTypeCriteria;
	
    public static final long MAX_RESULTS = 250L;

    public static final int DEFAULT_MAX_SIZE = 25;

    protected static final int START = 0;

	public TransactionTypeFilter(TransactionTypeCriteria transactionTypeCriteria_) {
		transactionTypeCriteria = transactionTypeCriteria_;
		if (transactionTypeCriteria == null) {
			transactionTypeCriteria = new TransactionTypeCriteria();
		}
	}

	@Override
	protected void buildPredicates(Root<TransactionType> transactionTypeRoot, CriteriaBuilder criteriaBuilder) {
		if (transactionTypeCriteria.getTransactionTypeCode() != null) {
			add(criteriaBuilder.and(criteriaBuilder.equal(transactionTypeRoot.get("transactionTypeCode"),
					transactionTypeCriteria.getTransactionTypeCode())));
		}

		if (BooleanUtils.isTrue(transactionTypeCriteria.getActive())) {
			add(criteriaBuilder.and(criteriaBuilder.equal(transactionTypeRoot.get("deleteFlag"), Boolean.FALSE)));
		}

	}

	@Override
	public void addOrderCriteria(Root<TransactionType> transactionTypeRoot, CriteriaBuilder criteriaBuilder) {
		if (transactionTypeCriteria.getOrderBy() != null && transactionTypeCriteria.getSortOrder() != null) {
			addOrder(addOrderCriteria(criteriaBuilder,
					transactionTypeRoot.get(transactionTypeCriteria.getOrderBy().getColumnName()),
					transactionTypeCriteria.getSortOrder(), transactionTypeCriteria.getOrderBy().getColumnType()));
		}
	}

	@Override
    public void addPagination(TypedQuery<TransactionType> query) {
		Long start = transactionTypeCriteria.getStart();
		Long limit = transactionTypeCriteria.getLimit();
        if (query != null) {
            long _start = (start == null || start < START) ? START : start;
            long _limit = (limit == null || limit < 1) ? DEFAULT_MAX_SIZE : (limit > MAX_RESULTS) ? MAX_RESULTS : limit;
            query.setMaxResults((int) _limit);
            query.setFirstResult((int) _start);
        }
    }
    
	private Order addOrderCriteria(CriteriaBuilder criteriaBuilder, Path path, SortOrder sortOrder,
			AbstractCriteria.OrderByType orderByType) {
		Order order;
		if (SortOrder.DESC.equals(sortOrder)) {
			order = AbstractCriteria.OrderByType.NUMBER.equals(orderByType) ? criteriaBuilder.desc(path)
					: criteriaBuilder.desc(criteriaBuilder.lower(path));
		} else {
			order = AbstractCriteria.OrderByType.NUMBER.equals(orderByType) ? criteriaBuilder.asc(path)
					: criteriaBuilder.asc(criteriaBuilder.lower(path));
		}
		return order;
	}

}
