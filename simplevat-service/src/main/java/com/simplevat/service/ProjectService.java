package com.simplevat.service;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Project;

import java.util.List;

public abstract class ProjectService extends SimpleVatService<Integer, Project> {

	public abstract List<Project> getProjectsByCriteria(ProjectCriteria projectCriteria) throws Exception;


}