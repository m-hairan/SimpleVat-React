package com.simplevat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.simplevat.dao.AbstractFilter;
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
	default public List<ENTITY> findByAttributes(Map<String, String> attributes) {
		List<ENTITY> listEntity = new ArrayList<>();
		
		if(attributes != null && attributes.size() > 0) {
			listEntity = getDao().findByAttributes(attributes);
		}
		
		return listEntity;
	}
	
	default public List<ENTITY> filter(AbstractFilter<ENTITY> filter) {
		List<ENTITY> listEntity = new ArrayList<>();
		if(filter != null) {
			listEntity = getDao().filter(filter);
		}
		return listEntity;
		
	}
	
	public Dao<PK, ENTITY> getDao();
	
}
