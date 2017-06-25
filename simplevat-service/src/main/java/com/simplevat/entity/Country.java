package com.simplevat.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

import com.simplevat.entity.converter.DateConverter;

/**
 * Created by mohsinh on 2/26/2017.
 */

@NamedQueries({
        @NamedQuery(name = "allCountries",
                query = "SELECT c " +
                        "FROM Country c where c.deleteFlag='false' ORDER BY c.defaltFlag DESC , c.orderSequence ASC")
})

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
    @Column(name = "ISO_ALPHA3_CODE", length = 3, columnDefinition = "CHAR")
    private String isoAlpha3Code;
    
    @Column(name = "DEFAULT_FLAG")
    private Character defaltFlag;

    @Column(name = "ORDER_SEQUENCE")
    private Integer orderSequence;
    
    @Basic
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Basic
    @Column(name = "CREATED_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag = Boolean.FALSE;
    
    @Basic
    @Version
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber = 1;

    @Transient
    @Setter
    private String countryFullName;

    public String getCountryFullName() {
        countryFullName = countryName + " - (" + isoAlpha3Code + ")";
        return countryFullName;
    }

}
