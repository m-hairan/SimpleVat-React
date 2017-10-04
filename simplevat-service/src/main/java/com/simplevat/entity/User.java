package com.simplevat.entity;

import com.simplevat.entity.converter.DateConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "findAllUsers",
            query = "SELECT u "
            + "FROM User u where u.deleteFlag = FALSE and u.isActive = TRUE ")
})
@Entity
@Table(name = "USER", schema = "simplevat", catalog = "")
@Data
public class User {

    @Id
    @Column(name = "USER_ID")
    private Integer userId;

    @Basic
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Basic
    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Basic
    @Column(name = "LAST_NAME")
    private String lastName;

    @Basic
    @Column(name = "DATE_OF_BIRTH")
    @Convert(converter = DateConverter.class)
    private LocalDateTime dateOfBirth;

    @Basic
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    @Basic
    @Column(name = "ROLE_CODE")
    private Integer roleCode;

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
    @Convert(converter = DateConverter.class)
    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag = Boolean.FALSE;

    @Basic
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Version
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_CODE", insertable = false, updatable = false)
    private Role role;

    @Basic
    @Column(name = "USER_PASSWORD")
    private String password;

    @Basic
    @Column(name = "PROFILE_IMAGE_URL")
    private String profileImagePath;

    @Basic
    @Column(name = "PROFILE_IMAGE")
    private byte[] profileImageBinary;

    @PrePersist
    public void updateDates() {
        createdDate = LocalDateTime.now();
        lastUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void updateLastUpdatedDate() {
        lastUpdateDate = LocalDateTime.now();
    }
}
