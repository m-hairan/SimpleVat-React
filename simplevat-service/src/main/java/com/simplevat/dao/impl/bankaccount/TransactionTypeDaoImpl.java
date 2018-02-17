package com.simplevat.dao.impl.bankaccount;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.dao.bankaccount.TransactionTypeDao;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.dao.AbstractDao;
import javax.persistence.TypedQuery;

@Repository
public class TransactionTypeDaoImpl extends AbstractDao<Integer, TransactionType> implements TransactionTypeDao {

    @Override
    public TransactionType updateOrCreateTransaction(
            TransactionType transactionType) {
        return this.update(transactionType);
    }

    @Override
    public TransactionType getTransactionType(Integer id) {
        return this.findByPK(id);
    }

    @Override
    public List<TransactionType> findAll() {
        List<TransactionType> result = this.executeNamedQuery("findAllTransactionType");
        return result;
    }

    @Override
    public List<TransactionType> findByText(String transactionTxt) {
        TypedQuery<TransactionType> query = getEntityManager().createQuery("SELECT t FROM TransactionType t WHERE t.deleteFlag=false AND t.transactionTypeName LIKE '%'||:searchToken||'%' ORDER BY t.defaltFlag DESC , t.orderSequence ASC", TransactionType.class);
        query.setParameter("searchToken", transactionTxt);
        return query.getResultList();
    }

    @Override
    public TransactionType getDefaultTransactionType() {
        List<TransactionType> transactoinTypes = findAll();
        if (CollectionUtils.isNotEmpty(transactoinTypes)) {
            return transactoinTypes.get(0);
        }
        return null;
    }

    @Override
    public List<TransactionType> findAllChild() {
        List<TransactionType> result = this.executeNamedQuery("findAllChildTransactionType");
        return result;
    }

}
