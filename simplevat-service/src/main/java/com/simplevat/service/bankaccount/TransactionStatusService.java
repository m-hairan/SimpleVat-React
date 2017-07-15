package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.service.SimpleVatService;

public interface TransactionStatusService  extends SimpleVatService<Integer, TransactionStatus> {
	
	public List<TransactionStatus> findAllTransactionStatues();

}
