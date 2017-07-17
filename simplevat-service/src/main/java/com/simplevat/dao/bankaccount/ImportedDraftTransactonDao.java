package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.ImportedDraftTransactionCriteria;
import com.simplevat.dao.Dao;
import com.simplevat.entity.bankaccount.ImportedDraftTransaction;

public interface ImportedDraftTransactonDao extends Dao<Integer, ImportedDraftTransaction> {

	 ImportedDraftTransaction updateOrCreateImportedDraftTransaction(ImportedDraftTransaction ismportedDraftTransacton);
	 
	 boolean deleteImportedDraftTransaction(Integer bankAcccountId);
	    
}
