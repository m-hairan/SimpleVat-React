package com.simplevat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.simplevat.entity.Expense;
import com.simplevat.entity.Purchase;
import com.simplevat.service.report.model.BankAccountTransactionReportModel;

public abstract class PurchaseService extends SimpleVatService<Integer, Purchase> {

    public abstract List<Purchase> getAllPurchase();

    public abstract Purchase getClosestDuePurchaseByContactId(Integer contactId);

    public abstract List<Purchase> getPurchaseListByDueAmount();

}
