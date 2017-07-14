package com.simplevat.service;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Project;

import java.util.List;

public interface ProjectService extends SimpleVatService<Integer, Project> {

    List<Project> getProjectsByCriteria(ProjectCriteria projectCriteria) throws Exception;


}