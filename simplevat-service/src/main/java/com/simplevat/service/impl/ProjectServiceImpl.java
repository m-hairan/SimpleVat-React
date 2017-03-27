package com.simplevat.service.impl;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.dao.ProjectDao;
import com.simplevat.entity.Project;
import com.simplevat.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Created by Utkarsh Bhavsar on 21/03/17.
 */
@Service
@ManagedBean(name = "projectService")
@SessionScoped
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsByCriteria(ProjectCriteria projectCriteria) throws Exception {
        return projectDao.getProjectsByCriteria(projectCriteria);
    }

    @Override
    @Transactional
    public Project updateOrCreateProject(Project project) {
        return projectDao.updateOrCreateProject(project);
    }
}
