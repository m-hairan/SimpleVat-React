package com.simplevat.web.contact.model;

import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Language;
import com.simplevat.entity.Title;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Hiren
 */
@Getter
@Setter
public class ContactModel implements Serializable {

    private static final long serialVersionUID = -7492170073928262949L;

 
    private Integer contactId;
   
    private String firstName;
 
    private String middleName;

    private String lastName;
    
    private String organization;
    
    private String email;

    private String billingEmail;
    
    private String telephone;

    private String mobileNumber;
    
    private String invoicingAddressLine1;

    private String invoicingAddressLine2;
   
    private String invoicingAddressLine3;
   
    private String city;
    
    private String stateRegion;
  
    private String postZipCode;
    
    private String poBoxNumber;

    private String contractPoNumber;
  
    private String vatRegistrationNumber;

    private Integer createdBy;
   
    private LocalDateTime createdDate;

    private Integer lastUpdatedBy;

    private LocalDateTime lastUpdateDate;

    private Boolean deleteFlag = Boolean.FALSE;

    private Integer versionNumber;

    private Country country;

    private Language language;

    private Currency currency;

    private Title title;

}
