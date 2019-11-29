package com.simplevat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.BankAccountTypeDao;
import com.simplevat.entity.bankaccount.BankAccountType;

@Repository(value = "bankAccountTypeDao")
public class BankAccountTypeDaoImpl extends AbstractDao<Integer, BankAccountType> implements BankAccountTypeDao {

    @Override
    public List<BankAccountType> getBankAccountTypeList() {
        List<BankAccountType> BankAccountTypeList = this.executeNamedQuery("allBankAccountType");
        return BankAccountTypeList;
    }

    @Override
    public BankAccountType getBankAccountType(int id) {
        return this.findByPK(id);
    }

    @Override
    public BankAccountType getDefaultBankAccountType() {
        if (getBankAccountTypeList() != null && !getBankAccountTypeList().isEmpty()) {
            BankAccountType bankAccountType = getBankAccountTypeList().get(0);
            return bankAccountType;
        }
        return null;
    }

}
