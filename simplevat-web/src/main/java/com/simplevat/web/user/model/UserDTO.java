/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.user.model;

import com.simplevat.entity.User;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Uday
 */
public class UserDTO extends User {

    @Getter
    @Setter
    private UploadedFile profileImage;
    private String name;
    private Date displayDOB;

    public Date getDisplayDOB() {
        if (getDateOfBirth() != null) {
            displayDOB = Date.from(getDateOfBirth().atZone(ZoneId.systemDefault()).toInstant());
        }
        return displayDOB;
    }

    public void setDisplayDOB(Date dob) {
        this.displayDOB = dob;
        if (dob != null) {
            LocalDateTime transactionDate = Instant.ofEpochMilli(dob.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            setDateOfBirth(transactionDate);
        }
    }

    public String getName() {
        name = getFirstName() + " " + getLastName();
        return name;
    }

}
