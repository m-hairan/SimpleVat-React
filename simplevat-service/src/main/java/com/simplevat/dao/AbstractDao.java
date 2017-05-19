package com.simplevat.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;


public  abstract class AbstractDao<PK,ENTITY> implements Dao<PK, ENTITY> {
	
	protected Class<ENTITY> entityClass;
	
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
	public AbstractDao() {
    	ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    	this.entityClass = (Class<ENTITY>) genericSuperclass.getActualTypeArguments()[1];
	}
    
	@Override
	public ENTITY findByPK(PK pk) {
		return entityManager.find(entityClass, pk);
	}

	@Override
	public List<ENTITY> executeNamedQuery(String namedQuery) {
		List<ENTITY> result = entityManager.createNamedQuery(namedQuery,
				entityClass).getResultList();
		return result;
	}

	@Override
	@Transactional
	public ENTITY persist(ENTITY entity) {
		 entityManager.persist(entity);
		 entityManager.flush();
		 entityManager.refresh(entity);
		 return entity;
	}

	@Override
	@Transactional
	public ENTITY update(ENTITY entity) {
		return entityManager.merge(entity);
	}
	@Override
	@Transactional
	public void delete(ENTITY entity) {
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ENTITY> executeCriteria(int criteriaType) {
		CriteriaQuery<ENTITY> criteriaQuery = getCriteria(criteriaType);
		Query query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	@Override
	public List<ENTITY> findByAttributes(Map<String, String> attributes) {
        List<ENTITY> results;
        //set up the Criteria query
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> cq = cb.createQuery(entityClass);
        Root<ENTITY> foo = cq.from(entityClass);
 
        List<Predicate> predicates = new ArrayList<Predicate>();
        for(String s : attributes.keySet())
        {
            if(foo.get(s) != null){
                predicates.add(cb.like((Expression) foo.get(s), "%" + attributes.get(s) + "%" ));
            }
        }
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<ENTITY> q = entityManager.createQuery(cq);
 
        results = q.getResultList();
        return results;
	}

}
