package org.simplevat.domain.user;

import java.io.Serializable;

public class Contact implements Serializable {
	
	private Integer contactId;
	private String contactFirstName;
	private String contactLastName;
	private String contactEmail;
	private String contactPhone;
	private String contactImage;
	private String contactFullName;

	
	
	/**
	 * 
	 */
	public Contact() {
		super();
	}
	/**
	 * @param contactId
	 * @param contactFirstName
	 * @param contactLastName
	 * @param contactEmail
	 * @param contactPhone
	 * @param contactImage
	 */
	public Contact(Integer contactId, String contactFirstName, String contactLastName, String contactEmail,
			String contactPhone, String contactImage) {
		super();
		this.contactId = contactId;
		this.contactFirstName = contactFirstName;
		this.contactLastName = contactLastName;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.contactImage = contactImage;
	}
	/**
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the contactFirstName
	 */
	public String getContactFirstName() {
		return contactFirstName;
	}
	/**
	 * @param contactFirstName the contactFirstName to set
	 */
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	/**
	 * @return the contactLastName
	 */
	public String getContactLastName() {
		return contactLastName;
	}
	/**
	 * @param contactLastName the contactLastName to set
	 */
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}
	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}
	/**
	 * @param contactPhone the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	/**
	 * @return the contactImage
	 */
	public String getContactImage() {
		return contactImage;
	}
	/**
	 * @param contactImage the contactImage to set
	 */
	public void setContactImage(String contactImage) {
		this.contactImage = contactImage;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contactEmail == null) ? 0 : contactEmail.hashCode());
		result = prime * result + ((contactFirstName == null) ? 0 : contactFirstName.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((contactImage == null) ? 0 : contactImage.hashCode());
		result = prime * result + ((contactLastName == null) ? 0 : contactLastName.hashCode());
		result = prime * result + ((contactPhone == null) ? 0 : contactPhone.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (contactEmail == null) {
			if (other.contactEmail != null)
				return false;
		} else if (!contactEmail.equals(other.contactEmail))
			return false;
		if (contactFirstName == null) {
			if (other.contactFirstName != null)
				return false;
		} else if (!contactFirstName.equals(other.contactFirstName))
			return false;
		if (contactId == null) {
			if (other.contactId != null)
				return false;
		} else if (!contactId.equals(other.contactId))
			return false;
		if (contactImage == null) {
			if (other.contactImage != null)
				return false;
		} else if (!contactImage.equals(other.contactImage))
			return false;
		if (contactLastName == null) {
			if (other.contactLastName != null)
				return false;
		} else if (!contactLastName.equals(other.contactLastName))
			return false;
		if (contactPhone == null) {
			if (other.contactPhone != null)
				return false;
		} else if (!contactPhone.equals(other.contactPhone))
			return false;
		return true;
	}

	public String getContactFullName() {
		contactFullName = this.contactFirstName+" "+this.contactLastName;
		return contactFullName;
	}
}
