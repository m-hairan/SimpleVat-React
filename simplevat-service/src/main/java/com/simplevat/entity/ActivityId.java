package com.simplevat.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class ActivityId implements Serializable {
	
	
	public ActivityId() {
		
	}
	
	public ActivityId(String moduleCode, String activityCode) {
		this.moduleCode = moduleCode;
		this.activityCode = activityCode;
	}

    @Basic
    @Column(name = "MODULE_CODE")
    private String moduleCode;
    
    @Basic
    @Column(name = "ACTIVITY_CODE")
    private String activityCode;
    
    
    @Override
	public int hashCode() {
    	final int prime = 31;
    	int result = 1;
    	result = result*prime + this.getModuleCode().hashCode();
    	result = result*prime + this.getActivityCode().hashCode();
    	return result;
	}
    
    
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityId other = (ActivityId) obj;
		
		if(this.getModuleCode().equals(other.getModuleCode()) && this.getActivityCode().equals(other.getActivityCode())) {
			return true;
		}
		return false;
	}
	
}
