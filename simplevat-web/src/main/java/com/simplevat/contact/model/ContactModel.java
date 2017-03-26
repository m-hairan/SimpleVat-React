package com.simplevat.contact.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Hiren
 */
@Getter
@Setter
public class ContactModel implements Serializable {

    private static final long serialVersionUID = -7492170073928262949L;

    private String firstName;

    private String lastName;

    private String organizationName;

    private String emailAddress;

}
