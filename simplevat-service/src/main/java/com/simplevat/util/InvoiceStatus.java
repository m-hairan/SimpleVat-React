package com.simplevat.util;

/**
 *
 * @author Hiren
 */
public enum InvoiceStatus {

    SAVED("Saved"),
    PENDING("Pending"),
    APPROVED("Approved"),
    PARTIALLY_PAID("Partially Paid"),
    PAID("Paid");

    private String desc;

    InvoiceStatus(final String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
