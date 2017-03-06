package com.simplevat.entity;

import com.simplevat.entity.Language;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */

@NamedQueries({
        @NamedQuery(name="allContacts",
                query="SELECT c " +
                        "FROM Contact c ")
})

@Entity
@Table(name = "CONTACT")
@Data
public class Contact {
    @Id
    @Column(name = "CONTACT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;
    @Basic
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Basic
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Basic
    @Column(name = "LAST_NAME")
    private String lastName;
    @Basic
    @Column(name = "ORGANIZATION")
    private String organization;
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @Basic
    @Column(name = "BILLING_EMAIL")
    private String billingEmail;
    @Basic
    @Column(name = "TELEPHONE")
    private String telephone;
    @Basic
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE1")
    private String invoicingAddressLine1;
    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE2")
    private String invoicingAddressLine2;
    @Basic
    @Column(name = "INVOICING_ADDRESS_LINE3")
    private String invoicingAddressLine3;
    @Basic
    @Column(name = "CITY")
    private String city;
    @Basic
    @Column(name = "STATE_REGION")
    private String stateRegion;
    @Basic
    @Column(name = "POST_ZIP_CODE")
    private String postZipCode;
    @Basic
    @Column(name = "PO_BOX_NUMBER")
    private String poBoxNumber;
    @Basic
    @Column(name = "COUNTRY_CODE")
    private Integer countryCode;
    @Basic
    @Column(name = "CONTRACT_PO_NUMBER")
    private String contractPoNumber;
    @Basic
    @Column(name = "VAT_REGISTRATION_NUMBER")
    private String vatRegistrationNumber;
    @Basic
    @Column(name = "INVOICE_LANGUAGE_CODE")
    private Integer invoiceLanguageCode;
    @Basic
    @Column(name = "CURRENCY_CODE")
    private Integer currencyCode;
    @Basic
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Basic
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Date lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Character deleteFlag;
    @Basic
    @Column(name = "VERSION_NUMBER")
    private int versionNumber;
//    private Country countryByCountryCode;
//    private Language languageByInvoiceLanguageCode;
//    private Currency currencyByCurrencyCode;
//    private Collection<Invoice> invoicesByContactId;
//    private Collection<Project> projectsByContactId;



}
