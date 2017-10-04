package com.simplevat.web.expense.model;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.simplevat.entity.Currency;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;

@Getter
@Setter
public class ExpenseModel {

    private Integer expenseId;
    private BigDecimal expenseAmount;
    private Date expenseDate;
    private String expenseDescription;
    private String receiptNumber;
    private User user;
    private TransactionType transactionType;
    private TransactionCategory transactionCategory;
    private Currency currency;
    private Project project;
    private String receiptAttachmentPath;
    private String receiptAttachmentDescription;
    private Integer createdBy;
    private LocalDateTime createdDate;
    private Integer lastUpdatedBy;
    private LocalDateTime lastUpdateDate;
    private boolean deleteFlag = false;
    private UploadedFile attachmentFile;
    private StreamedContent attachmentFileContent;
    private Integer versionNumber;
    byte[] receiptAttachmentBinary;
    private List<ExpenseItemModel> expenseItem;

    public void addExpenseItem(@Nonnull final ExpenseItemModel expenseItemModel) {
        if (null == this.expenseItem) {
            expenseItem = new ArrayList<>();
        }
        expenseItem.add(expenseItemModel);
    }

}
