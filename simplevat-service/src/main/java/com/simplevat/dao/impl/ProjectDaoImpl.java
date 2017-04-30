package com.simplevat.dao.impl;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.dao.ProjectDao;
import com.simplevat.entity.Project;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utkarsh Bhavsar on 20/03/17.
 */
@Repository
public class ProjectDaoImpl extends AbstractDao implements ProjectDao {


    @SuppressWarnings("unchecked")
    @Override
    public List<Project> getProjectsByCriteria(ProjectCriteria projectCriteria) throws Exception {
        try {
            /* Basic Check */
            if (projectCriteria == null) {
                projectCriteria = new ProjectCriteria();
            }

            /* Create Criteria */
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
            Root<Project> projectRoot = criteriaQuery.from(Project.class);

            /* Add to Predicates */
            List<Predicate> predicates = new ArrayList<>();
            if (projectCriteria.getProjectId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(projectRoot.<Integer>get("projectId"), projectCriteria.getProjectId())));
            }
            if (BooleanUtils.isTrue(projectCriteria.getActive())) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(projectRoot.<Character>get("deleteFlag"), !projectCriteria.getActive())));
            }

            /* Predicates to Criteria */
            if (!predicates.isEmpty()) {
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
            }

            /* Sorting */
            if (projectCriteria.getOrderBy() != null && projectCriteria.getSortOrder() != null) {
                criteriaQuery.orderBy(
                        addOrderCriteria(
                                criteriaBuilder,
                                projectRoot.get(projectCriteria.getOrderBy().getColumnName()),
                                projectCriteria.getSortOrder(),
                                projectCriteria.getOrderBy().getColumnType()
                        )
                );
            }


            Query query = getEntityManager().createQuery(criteriaQuery);

            /* Paging */
            addPaging(query, projectCriteria.getStart(), projectCriteria.getLimit());

            return query.getResultList();
        } catch (Exception ex) {
            throw new Exception("Unable to get locales: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Project updateOrCreateProject(Project project) {
        getEntityManager().merge(project);
        return project;
    }
}
