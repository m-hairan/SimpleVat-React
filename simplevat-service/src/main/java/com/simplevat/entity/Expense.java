package com.simplevat.entity;

import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.entity.converter.DateConverter;

import lombok.Data;

import javax.persistence.*;
import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allExpenses",
            query = "SELECT e "
            + "FROM Expense e where e.deleteFlag = FALSE")
})

@Entity
@Table(name = "EXPENSE")
@Data
public class Expense{

    @Id
    @Column(name = "EXPENSE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expenseId;
    @Basic
    @Column(name = "EXPENSE_AMOUNT")
    private BigDecimal expenseAmount;
    @Basic
    @Column(name = "EXPENSE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime expenseDate;
    @Basic
    @Column(name = "EXPENSE_DESCRIPTION")
    private String expenseDescription;
    @Basic
    @Column(name = "RECEIPT_NUMBER")
    private String receiptNumber;
    
    @Basic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLAIMANT_ID")
    private User user;
    
    @Basic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTION_TYPE_CODE")
    private TransactionType transactionType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTION_CATEGORY_CODE")
    private TransactionCategory transactionCategory;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_CODE")
    private Currency currency;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;
    
    @Basic
    @Column(name = "RECEIPT_ATTACHMENT_PATH")
    private String receiptAttachmentPath;
    @Basic
    @Column(name = "RECEIPT_ATTACHMENT_DESCRIPTION")
    private String receiptAttachmentDescription;
    @Basic
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Basic
    @Column(name = "CREATED_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag = Boolean.FALSE;
    
    @Basic
    @Version
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber;
    
    @Basic
    @Lob
    @Column(name = "RECEIPT_ATTACHMENT")
    private byte[] receiptAttachmentBinary;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "expense", orphanRemoval = true)
    private Collection<ExpenseLineItem> expenseLineItems;
    
  
    @Nonnull
    public Collection<ExpenseLineItem> getExpenseLineItems() {
        return (expenseLineItems == null) ? (expenseLineItems = new ArrayList<>()) : expenseLineItems;
    }
    
     public void setExpenseLineItems(@Nonnull final Collection<ExpenseLineItem> expenseLineItems) {

        final Collection<ExpenseLineItem> thisExpenseLineItems = getExpenseLineItems();
        thisExpenseLineItems.clear();
        thisExpenseLineItems.addAll(expenseLineItems);
    }
    
    @PrePersist
    public void updateDates() {
        createdDate = LocalDateTime.now();
        lastUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void updateLastUpdatedDate() {
        lastUpdateDate = LocalDateTime.now();
    }


}
