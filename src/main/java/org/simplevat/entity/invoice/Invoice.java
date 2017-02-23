package org.simplevat.entity.invoice;

import java.util.Calendar;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.simplevat.entity.domain.AbstractDomain;

/**
 *
 * @author Hiren
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Invoice extends AbstractDomain {

    private static final long serialVersionUID = 1372268008174193742L;

    @Nonnull
    private String invoiceNumber;
    
    @Nullable
    private String poNumber;

    @Nonnull
    private Calendar date;

    @Nonnull
    private Calendar dueDate;

    /**
     * To-do: Hiren 
     * It should be mapped with Company Entity with @ManyToOne later, Which have VAT number and
     * also Address entity obviously. :P
     *
     */
    @Nonnull
    private String companyUUID;

    /**
     * To-do: Hiren 
     * It should be mapped with Customer/Contact Entity with @ManyToOne later, Which have VAT number and
     * also Address entity obviously. :P
     *
     */
    @Nonnull
    private String customerUUID;

    @Nonnull
    private List<InvoiceItem> invoiceItems;
    
    @Nonnull
    private InvoiceStatus invoiceStatus;

}
