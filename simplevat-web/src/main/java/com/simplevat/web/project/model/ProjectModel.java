package com.simplevat.web.project.model;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Language;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author admin
 */
@Getter
@Setter
public class ProjectModel {
    
    private Integer projectId;

    private String projectName;

    private BigDecimal projectExpenseBudget;
    
    private BigDecimal projectRevenueBudget;

    private String contractPoNumber;

    private Contact contact;

    private String vatRegistrationNumber;

    private Language invoiceLanguageCode;

    private Currency currency;

    private Integer createdBy;

    private LocalDateTime createdDate;

    private Integer lastUpdatedBy;

    private LocalDateTime lastUpdateDate;

    private Boolean deleteFlag = Boolean.FALSE;

    private Integer versionNumber;
}
