package org.simplevat.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "LANGUAGE", schema = "simplevat", catalog = "")
public class LanguageEntity {
    private int languageCode;
    private String languageName;
    private String languageDescription;
    private Collection<ContactEntity> contactsByLanguageCode;
    private Collection<InvoiceEntity> invoicesByLanguageCode;
    private Collection<ProjectEntity> projectsByLanguageCode;

    @Id
    @Column(name = "LANGUAGE_CODE")
    public int getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
    }

    @Basic
    @Column(name = "LANGUAGE_NAME")
    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Basic
    @Column(name = "LANGUAGE_DESCRIPTION")
    public String getLanguageDescription() {
        return languageDescription;
    }

    public void setLanguageDescription(String languageDescription) {
        this.languageDescription = languageDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageEntity that = (LanguageEntity) o;

        if (languageCode != that.languageCode) return false;
        if (languageName != null ? !languageName.equals(that.languageName) : that.languageName != null) return false;
        if (languageDescription != null ? !languageDescription.equals(that.languageDescription) : that.languageDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = languageCode;
        result = 31 * result + (languageName != null ? languageName.hashCode() : 0);
        result = 31 * result + (languageDescription != null ? languageDescription.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "languageByInvoiceLanguageCode")
    public Collection<ContactEntity> getContactsByLanguageCode() {
        return contactsByLanguageCode;
    }

    public void setContactsByLanguageCode(Collection<ContactEntity> contactsByLanguageCode) {
        this.contactsByLanguageCode = contactsByLanguageCode;
    }

    @OneToMany(mappedBy = "languageByLanguageCode")
    public Collection<InvoiceEntity> getInvoicesByLanguageCode() {
        return invoicesByLanguageCode;
    }

    public void setInvoicesByLanguageCode(Collection<InvoiceEntity> invoicesByLanguageCode) {
        this.invoicesByLanguageCode = invoicesByLanguageCode;
    }

    @OneToMany(mappedBy = "languageByInvoiceLanguageCode")
    public Collection<ProjectEntity> getProjectsByLanguageCode() {
        return projectsByLanguageCode;
    }

    public void setProjectsByLanguageCode(Collection<ProjectEntity> projectsByLanguageCode) {
        this.projectsByLanguageCode = projectsByLanguageCode;
    }
}
