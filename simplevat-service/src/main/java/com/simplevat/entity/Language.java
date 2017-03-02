package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "LANGUAGE")
@Data
public class Language {

    @Id
    @Column(name = "LANGUAGE_CODE")
    private int languageCode;
    @Basic
    @Column(name = "LANGUAGE_NAME")
    private String languageName;
    @Basic
    @Column(name = "LANGUAGE_DESCRIPTION")
    private String languageDescription;

}
