package com.simplevat.web.productservice.model;

import com.simplevat.entity.VatCategory;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModel {

    private Integer productID;
    private String productName;
    private String productDescription;
    private VatCategory vatCategory;
    private String productCode;
    private Integer createdBy;
    private LocalDateTime createdDate;
    private Integer lastUpdatedBy;
    private LocalDateTime lastUpdateDate;
    private Boolean deleteFlag = Boolean.FALSE;
    private Integer versionNumber;
}
