package com.simplevat.dao.impl.bankaccount;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.bankaccount.BankAccountDao;
import com.simplevat.entity.bankaccount.BankAccount;

@Repository
@Transactional
public class BankAccountDaoImpl implements BankAccountDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<BankAccount> getBankAccounts() {
		List<BankAccount> bankAccounts = entityManager.createNamedQuery("allBankAccounts",
				BankAccount.class).getResultList();
		return bankAccounts;
	}

	@Override
	public BankAccount createOrUpdateBankAccount(BankAccount bankAccount) {
		return entityManager.merge(bankAccount);
	}

	@Override
	public BankAccount getBankAccount(Integer id) {
		return entityManager.find(BankAccount.class, id);
	}

}
