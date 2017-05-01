package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.ImportedDraftTransactionCriteria;
import com.simplevat.entity.bankaccount.ImportedDraftTransaction;

public interface ImportedDraftTransactonService {
	
	 List<ImportedDraftTransaction> getImportedDraftTransactionsByCriteria(ImportedDraftTransactionCriteria importedDraftTransactonCriteria) throws Exception;

	 ImportedDraftTransaction updateOrCreateImportedDraftTransaction(ImportedDraftTransaction importedDraftTransacton);

}
