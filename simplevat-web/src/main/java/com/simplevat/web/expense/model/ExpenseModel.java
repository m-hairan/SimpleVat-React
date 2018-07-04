package com.simplevat.web.expense.model;

import com.simplevat.entity.Contact;
import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.simplevat.entity.Currency;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.web.constant.RecurringNameValueMapping;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.faces.bean.SessionScoped;

@Getter
@Setter
@SessionScoped
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
    private String receiptAttachmentName;
    private String receiptAttachmentContentType;
    private StreamedContent attachmentFileContent;
    private Integer versionNumber;
    private Integer paymentMode;
    byte[] receiptAttachmentBinary;
    private List<ExpenseItemModel> expenseItem;
    private Contact expenseContact;
    private BigDecimal expenseSubtotal;
    private BigDecimal expenseVATAmount;
    private BigDecimal expenseAmountCompanyCurrency;
    private Integer flagView;
//    private Boolean recurringFlag;
//    private RecurringNameValueMapping recurringInterval;
//    private RecurringNameValueMapping recurringWeekDays;
//    private RecurringNameValueMapping recurringMonth;
//    private RecurringNameValueMapping recurringDays;
//    private RecurringNameValueMapping recurringFistToLast;
//    private RecurringNameValueMapping recurringByAfter;

    public void addExpenseItem(@Nonnull final ExpenseItemModel expenseItemModel) {
        if (null == this.expenseItem) {
            expenseItem = new ArrayList<>();
        }
        expenseItem.add(expenseItemModel);
    }

}
