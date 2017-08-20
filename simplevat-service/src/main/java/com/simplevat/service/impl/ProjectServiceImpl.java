package com.simplevat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.criteria.ProjectFilter;
import com.simplevat.dao.Dao;
import com.simplevat.dao.ProjectDao;
import com.simplevat.entity.Project;
import com.simplevat.service.ProjectService;

/**
 * Created by Utkarsh Bhavsar on 21/03/17.
 */
@Service("projectService")
public class ProjectServiceImpl   extends ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsByCriteria(ProjectCriteria projectCriteria) throws Exception {
    	ProjectFilter filter = new ProjectFilter(projectCriteria);
        return getDao().filter(filter);
    }


	@Override
	public Dao<Integer, Project> getDao() {
			return projectDao;
	}
}
