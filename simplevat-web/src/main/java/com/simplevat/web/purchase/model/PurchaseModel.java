/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.purchase.model;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Project;
import com.simplevat.entity.PurchaseLineItem;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.entity.converter.DateConverter;
import com.simplevat.web.expense.model.ExpenseItemModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import java.util.Date;
/**
 *
 * @author admin
 */
@Getter
@Setter
public class PurchaseModel {

    private Integer purchaseId;
    private BigDecimal purchaseAmount;
    private Date purchaseDate;
    private String purchaseDescription;
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
    private Integer lastUpdateBy;
    private LocalDateTime lastUpdateDate;
    private Boolean deleteFlag = Boolean.FALSE;
    private byte[] receiptAttachmentBinary;
    private UploadedFile attachmentFile;
    private StreamedContent attachmentFileContent;
    private List<PurchaseItemModel> purchaseItems;
    private Integer versionNumber;
    private Contact purchaseContact;

    

    public void addPurchaseItem(@Nonnull final PurchaseItemModel purchaseItemModel) {
        if (null == this.purchaseItems) {
            purchaseItems = new ArrayList<>();
        }
        purchaseItems.add(purchaseItemModel);
    }

}
