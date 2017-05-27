package com.simplevat.service;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getProjectsByCriteria(ProjectCriteria projectCriteria) throws Exception;

    Project updateOrCreateProject(Project project);
    
    Project getProject(Integer id);

}