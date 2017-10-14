package com.simplevat.user.model;

import com.simplevat.entity.Company;
import com.simplevat.entity.Role;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class UserModel implements Serializable{

    private Integer userId;

    private String userEmailId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;
    
    private Date today = new Date();

    private Company company;

    private UploadedFile profileImage;

    private StreamedContent profileImageContent;
    
    private StreamedContent currentProfileImageContent;
   
    private Integer createdBy;
    private LocalDateTime createdDate;
    private Boolean isActive;
    private String password;
    private String profileImagePath;
    private Integer versionNumber;
    private Boolean deleteFlag ;
    private Role role;
    private byte[] profileImageBinary;





}
