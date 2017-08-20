package com.simplevat.entity;


import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.simplevat.entity.converter.DateConverter;

import lombok.Data;

@NamedQueries({
    @NamedQuery(name = "allActivity",
            query = "SELECT a FROM Activity a order by a.lastUpdateDate desc")
})
/*@Entity
@Table(name = "ACTIVITY")*/
@Data
public class Activity {
	
	@EmbeddedId
	ActivityId activityId;
	
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
}
