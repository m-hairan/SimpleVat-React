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

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.dao.impl.AbstractDao;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;

@Repository
public class TransactionDaoImpl extends AbstractDao implements TransactionDao {

	@Override
	public List<Transaction> getTransactionsByCriteria(
			TransactionCriteria transactionCriteria) throws Exception {
		  try {
	            /* Basic Check */
	            if (transactionCriteria == null) {
	            	transactionCriteria = new TransactionCriteria();
	            }

	            /* Create Criteria */
	            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
	            CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
	            Root<Transaction> transactionRoot = criteriaQuery.from(Transaction.class);

	            /* Add to Predicates */
	            List<Predicate> predicates = new ArrayList<>();
	            if (transactionCriteria.getTransactionId() != null) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(transactionRoot.<Integer>get("transactionId"), transactionCriteria.getTransactionId())));
	            }
	            if (transactionCriteria.getBankAccount() != null) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(transactionRoot.<BankAccount>get("bankAccount"), transactionCriteria.getBankAccount())));
	            }
	            if (BooleanUtils.isTrue(transactionCriteria.getActive())) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(transactionRoot.<Boolean>get("deleteFlag"), Boolean.FALSE)));
	            }

	            /* Predicates to Criteria */
	            if (!predicates.isEmpty()) {
	                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
	            }

	            /* Sorting */
	            if (transactionCriteria.getOrderBy() != null && transactionCriteria.getSortOrder() != null) {
	                criteriaQuery.orderBy(
	                        addOrderCriteria(
	                                criteriaBuilder,
	                                transactionRoot.get(transactionCriteria.getOrderBy().getColumnName()),
	                                transactionCriteria.getSortOrder(),
	                                transactionCriteria.getOrderBy().getColumnType()
	                        )
	                );
	            }


	            Query query = getEntityManager().createQuery(criteriaQuery);

	            /* Paging */
	            addPaging(query, transactionCriteria.getStart(), transactionCriteria.getLimit());

	            return query.getResultList();
	        } catch (Exception ex) {
	            throw new Exception(ex.getMessage(), ex);
	        }
	}

	@Override
	public Transaction updateOrCreateTransaction(Transaction transaction) {
		 return getEntityManager().merge(transaction);
	}

}
