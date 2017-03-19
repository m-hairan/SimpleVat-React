package com.simplevat.expense.model;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.UploadedFile;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ExpenseModel {

    private int expenseId;
    private BigDecimal expenseAmount;
    private Date expenseDate;
    private String expenseDescription;
    private String receiptNumber;
    private Integer claimantId;
    private Integer transactionTypeCode;
    private Integer transactionCategoryCode;
    private Integer currencyCode;
    private Integer projectId;
    private String receiptAttachmentPath;
    private String receiptAttachmentDescription;
    private Integer createdBy;
    private Date createdDate;
    private Integer lastUpdatedBy;
    private Date lastUpdateDate;
    private Character deleteFlag;
    private UploadedFile attachmentFile;

}
