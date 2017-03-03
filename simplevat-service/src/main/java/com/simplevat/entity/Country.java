package com.simplevat.entity;

import com.simplevat.entity.BankAccount;
import com.simplevat.entity.Company;
import com.simplevat.entity.Contact;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "COUNTRY")
@Data
public class Country {
    @Id
    @Column(name = "COUNTRY_CODE")
    private int countryCode;
    @Basic
    @Column(name = "COUNTRY_NAME")
    private String countryName;
    @Basic
    @Column(name = "COUNTRY_DESCRIPTION")
    private String countryDescription;
    @Basic
    @Column(name = "ISO_ALPHA3_CODE")
    private Character isoAlpha3Code;

}
