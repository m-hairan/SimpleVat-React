package com.simplevat.expense.model;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.simplevat.entity.Currency;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ExpenseModel {

	public ExpenseModel(){
		this.expenseId = 0;
	}
    private int expenseId;
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
    private boolean deleteFlag =  false;
    private UploadedFile attachmentFile;
    private StreamedContent attachmentFileContent;
    private Integer versionNumber;

}
