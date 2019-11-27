package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.service.SimpleVatService;

public abstract class TransactionStatusService  extends SimpleVatService<Integer, TransactionStatus> {
	
	public abstract  List<TransactionStatus> findAllTransactionStatues();

}
