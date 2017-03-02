package com.simplevat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TEST")
public class TestEntity {
	
	private int testId;
	private String testName;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "TEST_ID")
	public int getTestId() {
		return testId;
	}
	
	public void setTestId(int testId) {
		this.testId = testId;
	}
	
	@Column(name = "TEST_NAME")
	public String getTestName() {
		return testName;
	}
	
	public void setTestName(String testName) {
		this.testName = testName;
	}

}
