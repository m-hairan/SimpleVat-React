package com.simplevat.service.impl.bankaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.criteria.bankaccount.ImportedDraftTransactionCriteria;
import com.simplevat.dao.bankaccount.ImportedDraftTransactonDao;
import com.simplevat.entity.bankaccount.ImportedDraftTransaction;
import com.simplevat.service.bankaccount.ImportedDraftTransactonService;

@Service("importedDraftTransactonService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ImportedDraftTransactonServiceImpl implements
		ImportedDraftTransactonService {
	
	@Autowired
	private ImportedDraftTransactonDao importedDraftTransactonDao;

	@Override
	public List<ImportedDraftTransaction> getImportedDraftTransactionsByCriteria(
			ImportedDraftTransactionCriteria importedDraftTransactonCriteria)
			throws Exception {
		return importedDraftTransactonDao.getImportedDraftTransactionsByCriteria(importedDraftTransactonCriteria);
	}

	@Override
	public ImportedDraftTransaction updateOrCreateImportedDraftTransaction(
			ImportedDraftTransaction importedDraftTransacton) {
		return importedDraftTransactonDao.updateOrCreateImportedDraftTransaction(importedDraftTransacton);
	}

}
