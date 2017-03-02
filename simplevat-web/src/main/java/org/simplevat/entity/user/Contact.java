package org.simplevat.entity.user;

import javax.annotation.Nonnull;

import lombok.Data;

import org.simplevat.entity.domain.AbstractDomain;

@Data
public class Contact extends AbstractDomain {

	@Nonnull
	private Integer contactId;

	@Nonnull
	private String contactFirstName;

	@Nonnull
	private String contactLastName;

	@Nonnull
	private String contactEmail;

	@Nonnull
	private String contactPhone;

	@Nonnull
	private String contactImage;


}
