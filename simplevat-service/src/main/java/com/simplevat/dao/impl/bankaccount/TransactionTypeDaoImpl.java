package com.simplevat.dao.impl.bankaccount;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.dao.bankaccount.TransactionTypeDao;
import com.simplevat.dao.impl.AbstractDao;
import com.simplevat.entity.bankaccount.TransactionType;

@Repository
public class TransactionTypeDaoImpl extends AbstractDao implements TransactionTypeDao {

	@Override
	public List<TransactionType> getTransactionTypesByCriteria(
			TransactionTypeCriteria transactionTypeCriteria) throws Exception {
		 try {
	            /* Basic Check */
	            if (transactionTypeCriteria == null) {
	            	transactionTypeCriteria = new TransactionTypeCriteria();
	            }

	            /* Create Criteria */
	            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
	            CriteriaQuery<TransactionType> criteriaQuery = criteriaBuilder.createQuery(TransactionType.class);
	            Root<TransactionType> transactionTypeRoot = criteriaQuery.from(TransactionType.class);

	            /* Add to Predicates */
	            List<Predicate> predicates = new ArrayList<>();
	            if (transactionTypeCriteria.getTransactionTypeCode() != null) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(transactionTypeRoot.<Integer>get("transactionTypeCode"), transactionTypeCriteria.getTransactionTypeCode())));
	            }
	          
	            if (BooleanUtils.isTrue(transactionTypeCriteria.getActive())) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(transactionTypeRoot.<Boolean>get("deleteFlag"), Boolean.FALSE)));
	            }

	            /* Predicates to Criteria */
	            if (!predicates.isEmpty()) {
	                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
	            }

	            /* Sorting */
	            if (transactionTypeCriteria.getOrderBy() != null && transactionTypeCriteria.getSortOrder() != null) {
	                criteriaQuery.orderBy(
	                        addOrderCriteria(
	                                criteriaBuilder,
	                                transactionTypeRoot.get(transactionTypeCriteria.getOrderBy().getColumnName()),
	                                transactionTypeCriteria.getSortOrder(),
	                                transactionTypeCriteria.getOrderBy().getColumnType()
	                        )
	                );
	            }


	            Query query = getEntityManager().createQuery(criteriaQuery);

	            /* Paging */
	            addPaging(query, transactionTypeCriteria.getStart(), transactionTypeCriteria.getLimit());

	            return query.getResultList();
	        } catch (Exception ex) {
	            throw new Exception(ex.getMessage(), ex);
	        }
	}

	@Override
	public TransactionType updateOrCreateTransaction(
			TransactionType transactionType) {
		return getEntityManager().merge(transactionType);
	}

	@Override
	public TransactionType getTransactionType(Integer id) {
		return getEntityManager().find(TransactionType.class, id);
	}

}
