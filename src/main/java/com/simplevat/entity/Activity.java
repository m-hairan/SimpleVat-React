package com.simplevat.entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.simplevat.entity.converter.DateConverter;
import java.time.format.DateTimeFormatter;
import javax.persistence.PostLoad;

import lombok.Data;

@NamedQueries({
    @NamedQuery(name = "allActivity",
            query = "SELECT a FROM Activity a where a.lastUpdateDate > :startDate order by a.lastUpdateDate desc")
})
@Entity
@Table(name = "ACTIVITY")
@Data
public class Activity {

    @Id
    @Column(name = "ACTIVITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;

    @Basic
    @Column(name = "MODULE_CODE")
    private String moduleCode;

    @Basic
    @Column(name = "ACTIVITY_CODE")
    private String activityCode;

    @Basic
    @Column(name = "FIELD_1")
    private String field1;

    @Basic
    @Column(name = "FIELD_2")
    private String field2;

    @Basic
    @Column(name = "FIELD_3")
    private String field3;

    @Basic
    @Column(name = "UPDATED_BY")
    private int updatedBy;

    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;

    @Transient
    private boolean loggingRequired = false;

    @Transient
    private String strLastUpdateDate;

    @PostLoad
    public void updateLastUploadDateTime() {
        if (lastUpdateDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd hh:mm a");
            strLastUpdateDate = lastUpdateDate.format(formatter);
        }
    }
}
