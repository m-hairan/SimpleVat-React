package com.simplevat.web.project.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Language;
import com.simplevat.entity.Project;
import com.simplevat.service.ContactService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.LanguageService;
import com.simplevat.service.ProjectService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.logging.Level;
import org.springframework.stereotype.Controller;

/**
 * Created by Utkarsh Bhavsar on 25/03/17.
 */
@Controller
@SpringScopeView
public class ProjectListController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProjectListController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ContactService contactService;

    @Getter
    @Setter
    private List<Project> projects;

    @Getter
    @Setter
    private Project selectedProject;

    @Getter
    @Setter
    private List<Language> languages;

    @Getter
    @Setter
    private List<Currency> currencies;

    private List<Project> getProjectFromCriteria() throws Exception {
        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setActive(Boolean.TRUE);
        return projectService.getProjectsByCriteria(projectCriteria);
    }

    @PostConstruct
    public void init() {
        selectedProject = new Project();
            Currency defaultCurrency = currencyService.getDefaultCurrency();
            if (defaultCurrency != null) {
                this.selectedProject.setCurrency(defaultCurrency);
            }
            try {
                this.projects = getProjectFromCriteria();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(ProjectListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public String redirectToCreateProject() {
        return "/pages/secure/project/project.xhtml?faces-redirect=true";
    }

    public String redirectToEditProject() throws Exception {
        System.out.println("selectedProjectId" + selectedProject.getProjectId());
        return "project.xhtml?faces-redirect=true&selectedProjectId=" + selectedProject.getProjectId();
    }

    public void deleteProject(Project project) throws Exception {
        project.setDeleteFlag(Boolean.TRUE);
        projectService.update(project);
        try {
                this.projects = getProjectFromCriteria();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(ProjectListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Project deleted successfully"));
     }

    public List<Project> projects(final String searchQuery) throws Exception {
        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setActive(Boolean.TRUE);
        return projectService.getProjectsByCriteria(projectCriteria);
    }

}
