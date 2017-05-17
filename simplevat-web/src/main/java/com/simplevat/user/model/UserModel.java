package com.simplevat.user.model;

import com.simplevat.entity.Company;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Hiren
 */
@Getter
@Setter
public class UserModel {

    private Integer userId;

    private String userEmailId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;
    
    private Date today = new Date();

    private Integer companyId;

    private UploadedFile profileImage;

    private StreamedContent profileImageContent;
    
    private StreamedContent currentProfileImageContent;

}
