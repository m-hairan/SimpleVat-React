package com.simplevat.user.model;

import com.simplevat.entity.Company;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Hiren
 */
@Getter
@Setter
public class UserModel {

    private String userEmailId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private Company company;
    
}
