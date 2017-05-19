package com.simplevat.dao.bankaccount;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.criteria.AbstractCriteria;
import com.simplevat.criteria.SortOrder;
import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.AbstractFilter;
import com.simplevat.entity.bankaccount.TransactionCategory;

@Repository
public class TransactionCategoryNewDao extends AbstractDao<Integer, TransactionCategory> {

	@Override
	public CriteriaQuery<TransactionCategory> getCriteria(int criteriaType) {
		if(criteriaType==1) {
			return criteria(new TransactionCategoryCriteria());
		}
		throw new RuntimeException("No such criteria type specified.");
	}

	
	private CriteriaQuery<TransactionCategory> criteria(TransactionCategoryCriteria transactionCategoryCriteria) {
        if (transactionCategoryCriteria == null) {
        	transactionCategoryCriteria = new TransactionCategoryCriteria();
        }

        /* Create Criteria */
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TransactionCategory> criteriaQuery = criteriaBuilder.createQuery(TransactionCategory.class);
        Root<TransactionCategory> transactionCategoryRoot = criteriaQuery.from(TransactionCategory.class);

        /* Add to Predicates */
        List<Predicate> predicates = new ArrayList<>();
        if (transactionCategoryCriteria.getTransactionCategoryCode() != null) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(transactionCategoryRoot.<Integer>get("transactionCategoryCode"), transactionCategoryCriteria.getTransactionCategoryCode())));
        }
        if (BooleanUtils.isTrue(transactionCategoryCriteria.getActive())) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(transactionCategoryRoot.<Boolean>get("deleteFlag"), Boolean.FALSE)));
        }

        /* Predicates to Criteria */
        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        /* Sorting */
        if (transactionCategoryCriteria.getOrderBy() != null && transactionCategoryCriteria.getSortOrder() != null) {
            criteriaQuery.orderBy(
                    addOrderCriteria(
                            criteriaBuilder,
                            transactionCategoryRoot.get(transactionCategoryCriteria.getOrderBy().getColumnName()),
                            transactionCategoryCriteria.getSortOrder(),
                            transactionCategoryCriteria.getOrderBy().getColumnType()
                    )
            );
        }
        
        return criteriaQuery;
		
	}
	
    private Order addOrderCriteria(CriteriaBuilder criteriaBuilder, Path path, SortOrder sortOrder, AbstractCriteria.OrderByType orderByType) {
        Order order;
        if (SortOrder.DESC.equals(sortOrder)) {
            order = AbstractCriteria.OrderByType.NUMBER.equals(orderByType)
                    ? criteriaBuilder.desc(path)
                    : criteriaBuilder.desc(criteriaBuilder.lower(path));
        } else {
            order = AbstractCriteria.OrderByType.NUMBER.equals(orderByType)
                    ? criteriaBuilder.asc(path)
                    : criteriaBuilder.asc(criteriaBuilder.lower(path));
        }
        return order;
    }

}
