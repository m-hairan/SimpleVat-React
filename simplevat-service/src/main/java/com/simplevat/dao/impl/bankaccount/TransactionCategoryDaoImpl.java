package com.simplevat.dao.impl.bankaccount;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.dao.bankaccount.TransactionCategoryDao;
import com.simplevat.dao.impl.AbstractDao;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;

@Repository
public class TransactionCategoryDaoImpl extends AbstractDao implements TransactionCategoryDao {

	@Override
	public List<TransactionCategory> getTransactionCategoriesByCriteria(
			TransactionCategoryCriteria transactionCategoryCriteria)
			throws Exception {
		 try {
	            /* Basic Check */
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


	            Query query = getEntityManager().createQuery(criteriaQuery);

	            /* Paging */
	            addPaging(query, transactionCategoryCriteria.getStart(), transactionCategoryCriteria.getLimit());

	            return query.getResultList();
	        } catch (Exception ex) {
	            throw new Exception(ex.getMessage(), ex);
	        }
	}

	@Override
	public TransactionCategory updateOrCreateTransaction(
			TransactionCategory transactionCategory) {
		return getEntityManager().merge(transactionCategory);
	}

	@Override
	public TransactionCategory getTransactionCategory(Integer id) {
		return getEntityManager().find(TransactionCategory.class, id);
	}

	@Override
	public TransactionCategory getDefaultTransactionCategory() {
		
		List<TransactionCategory> transactionCategories = getEntityManager().createNamedQuery("findAllTransactionCategory",
				TransactionCategory.class).getResultList();
		
		if (CollectionUtils.isNotEmpty(transactionCategories)) {
            return transactionCategories.get(0);
        }
		return null;
		
	}

}
