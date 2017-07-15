package com.simplevat.dao.impl.bankaccount;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.bankaccount.BankAccountDao;
import com.simplevat.entity.bankaccount.BankAccount;

@Repository
@Transactional
public class BankAccountDaoImpl extends AbstractDao<Integer, BankAccount>implements BankAccountDao {

	
	@Override
	public List<BankAccount> getBankAccounts() {
		return this.executeNamedQuery("allBankAccounts");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccount>  getBankAccountByUser(int userId) {
		List<BankAccount> resultList = null;
		try{
		String hql = "from BankAccount where createdBy = :userId";
		Query query = getEntityManager().createQuery(hql);
        query.setParameter("userId", userId);
         resultList = query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
        return resultList;
	
	}
	


}
