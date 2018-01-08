package com.simplevat.web.invoice.model;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.web.constant.RecurringNameValueMapping;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author hiren
 */
@Getter
@Setter
public class InvoiceModel {

    private Integer invoiceId;

    private Contact invoiceContact;

    private Project project;

    private String invoiceReferenceNumber;

    private Date invoiceDate;

    private Date invoiceDueDate;

    private int invoiceDueOn;

    private Currency currencyCode;

    private String invoiceNotes;

    private List<InvoiceItemModel> invoiceLineItems;

    private DiscountType discountType;

    private BigDecimal discount;

    private Integer createdBy;

    private Integer lastUpdatedBy;

    private String contractPoNumber;

    private BigDecimal invoiceAmount;

    private BigDecimal dueAmount;

    private Integer status;

    private String statusName;

    private Boolean recurringFlag;

    private Integer paymentMode;
    
    private RecurringNameValueMapping recurringInterval;

    private RecurringNameValueMapping recurringWeekDays;

    private RecurringNameValueMapping recurringMonth;

    private RecurringNameValueMapping recurringDays;

    private RecurringNameValueMapping recurringFistToLast;

    private RecurringNameValueMapping recurringByAfter;

    public void addInvoiceItem(@Nonnull final InvoiceItemModel invoiceItemModel) {
        if (null == this.invoiceLineItems) {
            invoiceLineItems = new ArrayList<>();
        }
        invoiceLineItems.add(invoiceItemModel);
    }

}
