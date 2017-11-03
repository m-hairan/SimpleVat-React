package com.simplevat.web.project.controller;

import com.simplevat.entity.Project;
import com.simplevat.web.project.model.ProjectModel;

/**
 *
 * @author admin
 */
public class ProjectHelper {
    
    public ProjectModel getProjectModel(Project project){
        ProjectModel projectModel = new ProjectModel();
        projectModel.setContact(project.getContact());
        projectModel.setContractPoNumber(project.getContractPoNumber());
        projectModel.setCreatedBy(project.getCreatedBy());
        projectModel.setCreatedDate(project.getCreatedDate());
        projectModel.setCurrency(project.getCurrency());
        projectModel.setDeleteFlag(project.getDeleteFlag());
        projectModel.setInvoiceLanguageCode(project.getInvoiceLanguageCode());
        projectModel.setLastUpdateDate(project.getLastUpdateDate());
        projectModel.setLastUpdatedBy(project.getLastUpdateBy());
        projectModel.setProjectBudget(project.getProjectBudget());
        projectModel.setProjectId(project.getProjectId());
        projectModel.setProjectName(project.getProjectName());
        projectModel.setVatRegistrationNumber(project.getVatRegistrationNumber());
        projectModel.setVersionNumber(project.getVersionNumber());
        return projectModel;
    }
}
