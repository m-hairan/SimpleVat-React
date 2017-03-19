package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "BANK_FEED_STATUS")
@Data
public class BankFeedStatus {
    @Id
    @Column(name = "BANK_FEED_STATUS_CODE")
    private int bankFeedStatusCode;
    @Basic
    @Column(name = "BANK_FEED_STATUS_NAME")
    private String bankFeedStatusName;
    @Basic
    @Column(name = "BANK_FEED_STATUS_DESCRIPTION")
    private String bankFeedStatusDescription;

}
