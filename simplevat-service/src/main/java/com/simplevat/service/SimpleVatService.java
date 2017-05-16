package com.simplevat.service;

import java.util.List;


import com.simplevat.dao.Dao;

public interface SimpleVatService<PK,ENTITY> {

	default public ENTITY findByPK(PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if(returnEntity == null) {
			throw new RuntimeException("Entity does not exists");
		}
		return returnEntity;
	}

	default public List<ENTITY> findAll() {
		return getDao().executeNamedQuery("findAll");
	}

	default public void persist(ENTITY entity, PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if(returnEntity != null) {
			throw new RuntimeException("Entity already exists");
		}
		getDao().persist(entity);
		
	}

	default public ENTITY update(ENTITY entity, PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if(returnEntity == null) {
			throw new RuntimeException("Entity does not exists");
		}
		return getDao().update(entity);
	}

	default public void delete(ENTITY entity, PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if(returnEntity == null) {
			throw new RuntimeException("Entity does not exists");
		}
		getDao().delete(entity);
		
	}
	
	default public List<ENTITY> findByCriteria(int criteriaType) {
		return getDao().executeCriteria(criteriaType);
	}
	
	public Dao<PK, ENTITY> getDao();
	
}
