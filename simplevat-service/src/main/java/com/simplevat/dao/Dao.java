package com.simplevat.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;


public interface Dao<PK,ENTITY> {
	
	public ENTITY findByPK(PK pk);
	
	public List<ENTITY> executeNamedQuery(String namedQuery);
	
	public ENTITY persist(ENTITY entity);
	
	public ENTITY update(ENTITY entity);
	
	public void delete(ENTITY entity);
	
	public List<ENTITY> executeCriteria(int criteriaType);
	
	public List<ENTITY> findByAttributes(Map<String, String> attributes);
	
	public default CriteriaQuery<ENTITY> getCriteria(int criteriaType) {
		throw new RuntimeException("Please implement this method to use it");
	}
	
	public EntityManager getEntityManager();
}
