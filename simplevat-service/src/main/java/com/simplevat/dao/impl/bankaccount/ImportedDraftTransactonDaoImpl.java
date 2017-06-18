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

import com.simplevat.criteria.bankaccount.ImportedDraftTransactionCriteria;
import com.simplevat.dao.bankaccount.ImportedDraftTransactonDao;
import com.simplevat.dao.impl.AbstractDao;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.ImportedDraftTransaction;

@Repository
public class ImportedDraftTransactonDaoImpl extends AbstractDao implements ImportedDraftTransactonDao {

	@Override
	public List<ImportedDraftTransaction> getImportedDraftTransactionsByCriteria(
			ImportedDraftTransactionCriteria importedDraftTransactonCriteria) throws Exception {
		  try {
	            /* Basic Check */
	            if (importedDraftTransactonCriteria == null) {
	            	importedDraftTransactonCriteria = new ImportedDraftTransactionCriteria();
	            }

	            /* Create Criteria */
	            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
	            CriteriaQuery<ImportedDraftTransaction> criteriaQuery = criteriaBuilder.createQuery(ImportedDraftTransaction.class);
	            Root<ImportedDraftTransaction> importedDraftTransactonRoot = criteriaQuery.from(ImportedDraftTransaction.class);

	            /* Add to Predicates */
	            List<Predicate> predicates = new ArrayList<>();
	            if (importedDraftTransactonCriteria.getImportedTransactionId() != null) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(importedDraftTransactonRoot.<Integer>get("importedTransactionId"), importedDraftTransactonCriteria.getImportedTransactionId())));
	            }
	            if (importedDraftTransactonCriteria.getBankAccount() != null) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(importedDraftTransactonRoot.<BankAccount>get("bankAccount"), importedDraftTransactonCriteria.getBankAccount())));
	            }
	            if (BooleanUtils.isTrue(importedDraftTransactonCriteria.getActive())) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(importedDraftTransactonRoot.<Character>get("deleteFlag"), Boolean.FALSE)));
	            }

	            /* Predicates to Criteria */
	            if (!predicates.isEmpty()) {
	                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
	            }

	            /* Sorting */
	            if (importedDraftTransactonCriteria.getOrderBy() != null && importedDraftTransactonCriteria.getSortOrder() != null) {
	                criteriaQuery.orderBy(
	                        addOrderCriteria(
	                                criteriaBuilder,
	                                importedDraftTransactonRoot.get(importedDraftTransactonCriteria.getOrderBy().getColumnName()),
	                                importedDraftTransactonCriteria.getSortOrder(),
	                                importedDraftTransactonCriteria.getOrderBy().getColumnType()
	                        )
	                );
	            }


	            Query query = getEntityManager().createQuery(criteriaQuery);

	            /* Paging */
	            addPaging(query, importedDraftTransactonCriteria.getStart(), importedDraftTransactonCriteria.getLimit());

	            return query.getResultList();
	        } catch (Exception ex) {
	            throw new Exception(ex.getMessage(), ex);
	        }
	}

	@Override
	public ImportedDraftTransaction updateOrCreateImportedDraftTransaction(ImportedDraftTransaction importedDraftTransacton) {
		 return getEntityManager().merge(importedDraftTransacton);
	}

	@Override
	public boolean deleteImportedDraftTransaction(Integer bankAcccountId) {
		
		Query updateQuery = getEntityManager().createNativeQuery("UPDATE IMPORTED_DRAFT_TRANSACTON idt SET idt.DELETE_FLAG=1 WHERE idt.BANK_ACCOUNT_ID= :bankAccountId");
		updateQuery.setParameter("bankAccountId", bankAcccountId);
		updateQuery.executeUpdate();
		
		return true;
	}

}
