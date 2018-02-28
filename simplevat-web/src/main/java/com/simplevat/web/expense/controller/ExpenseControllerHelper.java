package com.simplevat.web.expense.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import com.simplevat.entity.Expense;
import com.simplevat.entity.ExpenseLineItem;
import com.simplevat.web.constant.RecurringNameValueMapping;
import com.simplevat.web.expense.model.ExpenseItemModel;
import com.simplevat.web.expense.model.ExpenseModel;
import com.simplevat.web.utils.RecurringUtility;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

public class ExpenseControllerHelper {

    private RecurringUtility recurringUtility = new RecurringUtility();

    public Expense getExpense(ExpenseModel model) {
        Expense expense = new Expense();
        expense.setExpenseId(model.getExpenseId());
        expense.setUser(model.getUser());
        expense.setCreatedBy(model.getCreatedBy());
        expense.setCreatedDate(model.getCreatedDate());
        expense.setCurrency(model.getCurrency());
        expense.setDeleteFlag(model.isDeleteFlag());
        expense.setExpenseAmount(model.getExpenseAmount());
        if (model.getExpenseDate() != null) {
            LocalDateTime expenseDate = Instant.ofEpochMilli(model.getExpenseDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            expense.setExpenseDate(expenseDate);
        }
        expense.setExpenseDescription(model.getExpenseDescription());
        expense.setLastUpdateDate(model.getLastUpdateDate());
        expense.setLastUpdateBy(model.getLastUpdatedBy());
        expense.setProject(model.getProject());
        expense.setReceiptAttachmentDescription(model.getReceiptAttachmentDescription());
        expense.setReceiptAttachmentPath(model.getReceiptAttachmentPath());
        expense.setReceiptNumber(model.getReceiptNumber());
        expense.setExpenseContact(model.getExpenseContact());
        expense.setTransactionCategory(model.getTransactionCategory());
        expense.setTransactionType(model.getTransactionType());
        expense.setVersionNumber(model.getVersionNumber());
//        expense.setRecurringFlag(model.getRecurringFlag());
//        expense.setRecurringInterval(model.getRecurringInterval().getValue());
//        expense.setRecurringMonth(model.getRecurringMonth().getValue());
//        expense.setRecurringWeekDays(model.getRecurringWeekDays().getValue());
//        expense.setRecurringFistToLast(model.getRecurringFistToLast().getValue());
//        expense.setRecurringDays(model.getRecurringDays().getValue());
//        expense.setRecurringByAfter(model.getRecurringByAfter().getValue());
        final Collection<ExpenseLineItem> items = model
                .getExpenseItem()
                .stream()
                .map((item) -> convertToLineItem(item, expense))
                .collect(Collectors.toList());

        expense.setExpenseLineItems(items);

        return expense;
    }

    @Nonnull
    private ExpenseLineItem convertToLineItem(@Nonnull final ExpenseItemModel model,
            @Nonnull final Expense expense) {
        final ExpenseLineItem item = new ExpenseLineItem();
        if (model.getId() > 0) {
            item.setExpenseLineItemId(model.getId());
        }
        item.setExpenseLineItemDescription(model.getDescription());
        item.setExpenseLineItemQuantity(model.getQuatity());
        item.setExpenseLineItemUnitPrice(model.getUnitPrice());
        item.setExpenseLineItemVat(model.getVatId());
        item.setVersionNumber(model.getVersionNumber());
        item.setExpenseLineItemProductService(model.getExpenseLineItemProductService());
        item.setExpense(expense);
        
        return item;
    }

    public ExpenseModel getExpenseModel(Expense entity) {
        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.setExpenseId(entity.getExpenseId());
        expenseModel.setUser(entity.getUser());
        expenseModel.setCreatedBy(entity.getCreatedBy());
        expenseModel.setCreatedDate(entity.getCreatedDate());
        expenseModel.setCurrency(entity.getCurrency());
        expenseModel.setDeleteFlag(entity.getDeleteFlag());
        expenseModel.setExpenseAmount(entity.getExpenseAmount());
        if (entity.getExpenseDate() != null) {
            Date expenseDate = Date.from(entity.getExpenseDate().atZone(ZoneId.systemDefault()).toInstant());
            expenseModel.setExpenseDate(expenseDate);
        }
        expenseModel.setExpenseDescription(entity.getExpenseDescription());
        expenseModel.setLastUpdateDate(entity.getLastUpdateDate());
        expenseModel.setLastUpdatedBy(entity.getLastUpdateBy());
        expenseModel.setProject(entity.getProject());
        expenseModel.setReceiptAttachmentDescription(entity.getReceiptAttachmentDescription());
        expenseModel.setReceiptAttachmentPath(entity.getReceiptAttachmentPath());
        expenseModel.setReceiptNumber(entity.getReceiptNumber());
        expenseModel.setTransactionCategory(entity.getTransactionCategory());
        expenseModel.setExpenseContact(entity.getExpenseContact());
        expenseModel.setTransactionType(entity.getTransactionType());
        expenseModel.setVersionNumber(entity.getVersionNumber());
        expenseModel.setReceiptAttachmentBinary(entity.getReceiptAttachmentBinary());

        final List<ExpenseItemModel> items = entity
                .getExpenseLineItems()
                .stream()
                .map((lineItem) -> convertToItemModel(lineItem))
                .collect(Collectors.toList());
        expenseModel.setExpenseItem(items);
//        expenseModel.setRecurringFlag(entity.getRecurringFlag());
//        for (RecurringNameValueMapping nameValueMapping : recurringUtility.completeRecurringInterval()) {
//            expenseModel.setRecurringInterval(entity.getRecurringInterval().getValue());
//        }
//        expenseModel.setRecurringMonth(entity.getRecurringMonth().getValue());
//        expenseModel.setRecurringWeekDays(entity.getRecurringWeekDays().getValue());
//        expenseModel.setRecurringFistToLast(entity.getRecurringFistToLast().getValue());
//        expenseModel.setRecurringDays(entity.getRecurringDays().getValue());
//        expenseModel.setRecurringByAfter(entity.getRecurringByAfter().getValue());
        return expenseModel;
    }

    @Nonnull
    public ExpenseItemModel convertToItemModel(@Nonnull final ExpenseLineItem expenseLineItem) {

        final ExpenseItemModel model = new ExpenseItemModel();
        model.setId(expenseLineItem.getExpenseLineItemId());
        model.setDescription(expenseLineItem.getExpenseLineItemDescription());
        model.setQuatity(expenseLineItem.getExpenseLineItemQuantity());
        model.setUnitPrice(expenseLineItem.getExpenseLineItemUnitPrice());
        model.setVatId(expenseLineItem.getExpenseLineItemVat());
        model.setVersionNumber(expenseLineItem.getVersionNumber());
        model.setExpenseLineItemProductService(expenseLineItem.getExpenseLineItemProductService());
        this.updateSubTotal(model);
        return model;
    }

    private void updateSubTotal(@Nonnull final ExpenseItemModel expenseItemModel) {
        final int quantity = expenseItemModel.getQuatity();
        final BigDecimal unitPrice = expenseItemModel.getUnitPrice();
        BigDecimal vatPer = new BigDecimal(BigInteger.ZERO);
        if (expenseItemModel.getVatId() != null) {
            vatPer = expenseItemModel.getVatId().getVat();
        }
        if (null != unitPrice) {
            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
            expenseItemModel.setSubTotal(amountWithoutTax);

            if (vatPer != null && vatPer.compareTo(BigDecimal.ZERO) >= 1) {
                final BigDecimal amountWithTax = amountWithoutTax
                        .add(amountWithoutTax.multiply(vatPer).multiply(new BigDecimal(0.01)));
                expenseItemModel.setSubTotal(amountWithTax);
            }
        }

    }

}
