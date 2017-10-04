package com.simplevat.entity.bankaccount;

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
import javax.persistence.Version;

import lombok.Data;

import com.simplevat.entity.converter.DateConverter;

@NamedQueries({
    @NamedQuery(name = "findAllTransactionStatues",
            query = "SELECT t "
            + "FROM TransactionStatus t where t.deleteFlag = FALSE order by t.defaltFlag DESC, t.orderSequence ASC")
})
@Entity
@Table(name = "EXPLANATION_STATUS")
@Data
public class TransactionStatus {

	@Id
	@Column(name = "EXPLANATION_STATUS_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int explainationStatusCode;
	
	@Basic
	@Column(name = "EXPLANATION_STATUS_NAME")
	private String explainationStatusName;
	
	@Basic
	@Column(name = "EXPLANATION_STATUS_DESCRIPTION")
	private String explainationStatusDescriptions;

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
	private Integer versionNumber;

}
