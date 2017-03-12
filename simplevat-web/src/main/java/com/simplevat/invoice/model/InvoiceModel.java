package com.simplevat.invoice.model;

import com.simplevat.entity.Currency;
import com.simplevat.entity.invoice.DiscountType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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

    private String contactId;

    private String projectId;

    private String invoiceRefNo;

    private Date invoiceDate;

    private int invoiceDueOn;

    private Currency currencyCode;

    private String invoiceText;

    private List<InvoiceItemModel> invoiceItems;

    private DiscountType discountType;

    private BigDecimal discount;

    private String contractPoNumber;

    public void addInvoiceItem(@Nonnull final InvoiceItemModel invoiceItemModel) {
        if (null == this.invoiceItems) {
            invoiceItems = new ArrayList<>();
        }
        invoiceItems.add(invoiceItemModel);
    }

}
