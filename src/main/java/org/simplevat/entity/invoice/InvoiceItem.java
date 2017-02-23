package org.simplevat.entity.invoice;

import java.math.BigDecimal;
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
public class InvoiceItem extends AbstractDomain {

    private static final long serialVersionUID = -4135754309945831599L;

    /**
     * To-do: Hiren Here require currency for all Amount related fields.
     *
     */
    @Nonnull
    private BigDecimal unitPrice;

    private int quantity;

    @Nonnull
    private BigDecimal vatRate;

    /**
     * To-do: Hiren annotate this with @Enumarated(STRING) annotation after
     * implementing JPA/hibernate.
     *
     */
    @Nullable
    private DiscountType discountType;

    /**
     * either in % or absolute.
     * It will be decided based on discountType
     * @see org.simplevat.domain.user.InvoiceItem#getDiscountType().
     *
     */
    @Nullable
    private BigDecimal discountValue;

}
