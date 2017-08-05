package com.simplevat.entity.bankaccount;

import com.simplevat.entity.converter.DateConverter;

import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "findAllTransactionCategory",
            query = "SELECT t "
            + "FROM TransactionCategory t where t.deleteFlag='false' ORDER BY t.defaltFlag DESC , t.orderSequence ASC")
})
@Entity
@Table(name = "TRANSACTION_CATEGORY")
@Data
public class TransactionCategory {
	
    @Id
    @Column(name = "TRANSACTION_CATEGORY_CODE")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int transactionCategoryCode;
    @Basic
    @Column(name = "TRANSACTION_CATEGORY_NAME")
    private String transactionCategoryName;
    @Basic
    @Column(name = "TRANSACTION_CATEGORY_DESCRIPTION")
    private String transactionCategoryDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTION_TYPE_CODE")
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_TRANSACTION_CATEGORY_CODE")
    private TransactionCategory parentTransactionCategory;
    
    @Column(name = "DEFAULT_FLAG")
    private Character defaltFlag;

    @Column(name = "ORDER_SEQUENCE")
    private Integer orderSequence;
    
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


}
