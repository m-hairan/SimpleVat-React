package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.ImportedDraftTransactionCriteria;
import com.simplevat.entity.bankaccount.ImportedDraftTransaction;
import com.simplevat.service.SimpleVatService;

public interface ImportedDraftTransactonService extends SimpleVatService<Integer, ImportedDraftTransaction> {
	
	 List<ImportedDraftTransaction> getImportedDraftTransactionsByCriteria(ImportedDraftTransactionCriteria importedDraftTransactonCriteria) throws Exception;

	 ImportedDraftTransaction updateOrCreateImportedDraftTransaction(ImportedDraftTransaction importedDraftTransacton);
	 
	 boolean deleteImportedDraftTransaction(Integer bankAcccountId);

}
