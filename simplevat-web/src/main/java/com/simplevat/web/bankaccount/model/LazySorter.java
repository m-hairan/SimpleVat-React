/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.bankaccount.model;

import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author HP
 */
public class LazySorter implements Comparator<TransactionViewModel> {

    private String sortField;

    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(TransactionViewModel model1, TransactionViewModel model2) {
        try {
            Field field1 = TransactionViewModel.class.getDeclaredField(this.sortField);
            field1.setAccessible(true);
            Field field2 = TransactionViewModel.class.getDeclaredField(this.sortField);
            field2.setAccessible(true);

            Object value1 = field1.get(model1);
            Object value2 = field2.get(model2);

            int value = ((Comparable) value1).compareTo(value2);
            return sortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
