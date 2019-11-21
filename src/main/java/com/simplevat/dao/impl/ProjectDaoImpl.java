package com.simplevat.dao.impl;

import com.simplevat.dao.ProjectDao;
import com.simplevat.entity.Project;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.AbstractDao;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Utkarsh Bhavsar on 20/03/17.
 */
@Repository
public class ProjectDaoImpl extends AbstractDao<Integer, Project> implements ProjectDao {

    @Override
    @Transactional
    public void deleteByIds(List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            for (Integer id : ids) {
                Project project = findByPK(id);
                project.setDeleteFlag(Boolean.TRUE);
                update(project);
            }
        }
    }

}
