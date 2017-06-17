package com.simplevat.dao.bankaccount;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.AbstractDao;
import com.simplevat.entity.bankaccount.TransactionStatus;

@Repository(value = "transactionStatusDao")
public class TransactionStatusDao extends AbstractDao<Integer, TransactionStatus> {

}
