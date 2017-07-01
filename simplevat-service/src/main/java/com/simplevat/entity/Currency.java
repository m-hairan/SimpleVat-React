package com.simplevat.entity;

import javax.persistence.*;
import lombok.Data;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allCurrencies",
            query = "SELECT c "
            + "FROM Currency c ORDER BY c.defaultFlag DESC, c.orderSequence ASC ")
})

@Entity
@Table(name = "CURRENCY")
@Data
public class Currency {

    @Id
    @Column(name = "CURRENCY_CODE")
    private Integer currencyCode;
    @Basic
    @Column(name = "CURRENCY_NAME")
    private String currencyName;
    @Basic
    @Column(name = "CURRENCY_DESCRIPTION")
    private String currencyDescription;
    @Basic
    @Column(name = "CURRENCY_ISO_CODE", length = 3, columnDefinition = "CHAR")
    private String currencyIsoCode;
    @Basic
    @Column(name = "CURRENCY_SYMBOL")
    private String currencySymbol;

    @Column(name = "DEFAULT_FLAG")
    private Character defaultFlag;

    @Column(name = "ORDER_SEQUENCE")
    private Integer orderSequence;

}
