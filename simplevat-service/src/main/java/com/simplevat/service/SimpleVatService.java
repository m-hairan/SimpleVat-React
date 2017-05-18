package com.simplevat.service;

import java.util.List;


import com.simplevat.dao.Dao;
import com.simplevat.service.exceptions.ServiceErrorCode;
import com.simplevat.service.exceptions.ServiceException;

public interface SimpleVatService<PK,ENTITY> {

	default public ENTITY findByPK(PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if(returnEntity == null) {
			throw new ServiceException("", ServiceErrorCode.RecordDoesntExists);
		}
		return returnEntity;
	}

	default public List<ENTITY> findAll() {
		return getDao().executeNamedQuery("findAll");
	}

	default public void persist(ENTITY entity, PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if(returnEntity != null) {
			throw new ServiceException("", ServiceErrorCode.RecordAlreadyExists);
		}
		getDao().persist(entity);
		
	}

	default public ENTITY update(ENTITY entity, PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if(returnEntity == null) {
			throw new ServiceException("", ServiceErrorCode.RecordDoesntExists);
		}
		return getDao().update(entity);
	}

	default public void delete(ENTITY entity, PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if(returnEntity == null) {
			throw new ServiceException("", ServiceErrorCode.RecordDoesntExists);
		}
		getDao().delete(entity);
		
	}
	
	default public List<ENTITY> findByCriteria(int criteriaType) {
		return getDao().executeCriteria(criteriaType);
	}
	
	public Dao<PK, ENTITY> getDao();
	
}
