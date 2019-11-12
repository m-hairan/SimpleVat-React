package com.simplevat.enums;

/**
 *
 * @author Hiren
 */
public enum InvoiceStatusEnum {

    SAVED("Saved"),
    PENDING("Pending"),
    APPROVED("Approved"),
    PARTIALLY_PAID("Partially Paid"),
    PAID("Paid");

    private String desc;

    InvoiceStatusEnum(final String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
