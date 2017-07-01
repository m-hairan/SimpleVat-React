package com.simplevat.entity;

import javax.persistence.*;
import lombok.Data;

/**
 * Created by mohsin on 3/12/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allTitles",
            query = "SELECT t "
            + "FROM Title t ")
})

@Entity
@Table(name = "TITLE")
@Data
public class Title {

    @Id
    @Column(name = "TITLE_CODE")
    private int titleCode;
    @Basic
    @Column(name = "TITLE_NAME")
    private String titleName;
    @Basic
    @Column(name = "TITLE_DESCRIPTION")
    private String titleDescription;

}
