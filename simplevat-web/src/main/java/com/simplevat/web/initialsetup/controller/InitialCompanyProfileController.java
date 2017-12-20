/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.initialsetup.controller;

import com.simplevat.web.company.controller.*;
import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Company;
import com.simplevat.entity.CompanyType;
import com.simplevat.entity.Country;
import com.simplevat.entity.IndustryType;
import com.simplevat.entity.Role;
import com.simplevat.entity.Title;
import com.simplevat.service.CompanyService;
import com.simplevat.service.CompanyTypeService;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.CountryService;
import com.simplevat.service.IndustryTypeService;
import com.simplevat.service.RoleService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.common.controller.StreamedContentSessionController;
import com.simplevat.web.login.controller.SecurityBean;
import com.simplevat.web.user.controller.UserProfileController;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@SpringScopeView
public class InitialCompanyProfileController implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitialCompanyProfileController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyTypeService companyTypeService;
    @Autowired
    private IndustryTypeService industryTypeService;
    @Autowired
    private CountryService countryService;
    @Getter
    @Setter
    public CompanyModel companyModel;
    @Getter
    @Setter
    public boolean copyInvoiceAddress;
    public StreamedContent profilePic;
    @Getter
    private List<Country> countries = new ArrayList<>();
    @Getter
    private List<SelectItem> companyTypes = new ArrayList<>();
    @Getter
    @Setter
    public Company company;
    @Getter
    @Setter
    String fileName;
    @Getter
    private boolean renderProfilePic;

    private CompanyHelper companyHelper;

    @Autowired
    private UserServiceNew userService;

    @Autowired
    private RoleService roleService;

    @Getter
    private List<Title> titles;

    @Getter
    @Setter
    private UploadedFile newProfilePic;

    @Getter
    private List<Role> roleList;

    @Getter
    @Setter
    StreamedContentSessionController streamContent;
    Integer userId;

    @PostConstruct
    public void init() {

        Object objSelectedExpenseModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId");
//        if (objSelectedExpenseModel == null) {
//            if (!userService.findAll().isEmpty()) {
//                try {
//                    FacesContext.getCurrentInstance().getExternalContext().redirect("pages/public/login.xhtml");
//                } catch (IOException ex) {
//                    java.util.logging.Logger.getLogger(SecurityBean.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
        userId = Integer.parseInt(objSelectedExpenseModel.toString());
        companyModel = new CompanyModel();
        companyHelper = new CompanyHelper();
        countries = countryService.getCountries();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("STREAMED_CONTENT_COMPANY_LOGO", companyModel.getCompanyLogo());
        renderProfilePic = true;

    }

    public List<CompanyType> completeCompanyType() {
        return companyTypeService.getCompanyTypes();
    }

    public List<IndustryType> completeIndustryType() {
        return industryTypeService.getIndustryTypes();
    }

    public List<Country> completeCountry(String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();
        Iterator<Country> countryIterator = this.countries.iterator();
        LOGGER.debug(" Size :" + countries.size());
        while (countryIterator.hasNext()) {
            Country country = countryIterator.next();
            if (country.getCountryName() != null
                    && !country.getCountryName().isEmpty()
                    && country.getCountryName().toUpperCase().contains(countryStr.toUpperCase())) {
                countrySuggestion.add(country);
            } else if (country.getIsoAlpha3Code() != null
                    && !country.getIsoAlpha3Code().isEmpty()
                    && country.getIsoAlpha3Code().toUpperCase().contains(countryStr.toUpperCase())) {
                countrySuggestion.add(country);
            }
        }
        LOGGER.debug(" Size :" + countrySuggestion.size());
        return countrySuggestion;
    }

    public void handleFileUpload(FileUploadEvent event) {
        companyModel.setCompanyLogo(event.getFile().getContents());
        fileName = event.getFile().getFileName();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("STREAMED_CONTENT_COMPANY_LOGO", event.getFile().getContents());
        renderProfilePic = true;
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String saveUpdate() {
        try {
            Company c = companyHelper.getCompanyFromCompanyModel(companyModel);
            c.setCompanyTypeCode(companyTypeService.findByPK(c.getCompanyTypeCode().getId()));
            c.setCompanyExpenseBudget(BigDecimal.ZERO);
            c.setCompanyRevenueBudget(BigDecimal.ZERO);
            c.setCreatedBy(0);
            c.setCreatedDate(LocalDateTime.now());
            companyService.persist(c);
            System.out.println("companyId==" + c.getCompanyId());
            return "setting-configuration?faces-redirect=true&userId=" + userId;
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void sameAsInvoicingAddress() {
        if (copyInvoiceAddress) {
            companyModel.setCompanyAddressLine1(companyModel.getInvoicingAddressLine1());
            companyModel.setCompanyAddressLine2(companyModel.getInvoicingAddressLine2());
            companyModel.setCompanyAddressLine3(companyModel.getInvoicingAddressLine3());
            companyModel.setCompanyCity(companyModel.getInvoicingCity());
            companyModel.setCompanyCountryCode(companyModel.getInvoicingCountryCode());
            companyModel.setCompanyPoBoxNumber(companyModel.getInvoicingPoBoxNumber());
            companyModel.setCompanyPostZipCode(companyModel.getInvoicingPostZipCode());
            companyModel.setCompanyStateRegion(companyModel.getInvoicingStateRegion());
        } else {
            companyModel.setCompanyAddressLine1("");
            companyModel.setCompanyAddressLine2("");
            companyModel.setCompanyAddressLine3("");
            companyModel.setCompanyCity("");
            companyModel.setCompanyCountryCode(countryService.getDefaultCountry());
            companyModel.setCompanyPoBoxNumber("");
            companyModel.setCompanyPostZipCode("");
            companyModel.setCompanyStateRegion("");
        }
    }

    public List<Role> comoleteRole() {
        return roleService.getRoles();
    }

    public List<Title> filterTitle(String titleStr) {
        List<Title> titleSuggestion = new ArrayList<>();
        Iterator<Title> titleIterator = this.titles.iterator();

        while (titleIterator.hasNext()) {
            Title title = titleIterator.next();
            if (title.getTitleDescription() != null
                    && !title.getTitleDescription().isEmpty()
                    && title.getTitleDescription().toUpperCase().contains(titleStr.toUpperCase())) {
                titleSuggestion.add(title);
            }
        }
        return titleSuggestion;
    }

    public String previousPage() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userId", userId);
        return "firstUserAccount.xhtml?faces-redirect=true";
    }
}
