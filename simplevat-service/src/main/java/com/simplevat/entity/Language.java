package com.simplevat.entity;

import javax.persistence.*;
import lombok.Data;

/**
 * Created by mohsinh on 2/26/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allLanguages",
            query = "SELECT l "
            + "FROM Language l  ")
})

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
