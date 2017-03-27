package com.simplevat.dao;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Project;

import java.util.List;

/**
 * Created by Utkarsh Bhavsar on 20/03/17.
 */
public interface ProjectDao {

    List<Project> getProjectsByCriteria(ProjectCriteria projectCriteria) throws Exception;

    Project updateOrCreateProject(Project project);

}
