package com.simplevat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.simplevat.dao.AbstractFilter;
import com.simplevat.dao.Dao;
import com.simplevat.service.exceptions.ServiceErrorCode;
import com.simplevat.service.exceptions.ServiceException;

public abstract class SimpleVatService<PK, ENTITY> {

	public ENTITY findByPK(PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if (returnEntity == null) {
			throw new ServiceException("", ServiceErrorCode.RecordDoesntExists);
		}
		return returnEntity;
	}

	public List<ENTITY> executeNamedQuery(String namedQuery) {
		return getDao().executeNamedQuery(namedQuery);
	}

	public void persist(ENTITY entity, PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if (returnEntity != null) {
			throw new ServiceException("", ServiceErrorCode.RecordAlreadyExists);
		}
		getDao().persist(entity);

	}

	public ENTITY update(ENTITY entity, PK pk) {
		// commenting this as we are calling update for save and update method both
		// ENTITY returnEntity = getDao().findByPK(pk);
		// if(returnEntity == null) {
		// throw new ServiceException("", ServiceErrorCode.RecordDoesntExists);
		// }
		return getDao().update(entity);
	}

	public void delete(ENTITY entity) {
		getDao().delete(entity);
	}

	public void persist(ENTITY entity) {
		getDao().persist(entity);
	}

	public ENTITY update(ENTITY entity) {
		return getDao().update(entity);
	}

	public void delete(ENTITY entity, PK pk) {
		ENTITY returnEntity = getDao().findByPK(pk);
		if (returnEntity == null) {
			throw new ServiceException("", ServiceErrorCode.RecordDoesntExists);
		}
		getDao().delete(entity);

	}

	public List<ENTITY> findByAttributes(Map<String, String> attributes) {
		List<ENTITY> listEntity = new ArrayList<>();

		if (attributes != null && attributes.size() > 0) {
			listEntity = getDao().findByAttributes(attributes);
		}

		return listEntity;
	}

	public List<ENTITY> filter(AbstractFilter<ENTITY> filter) {
		List<ENTITY> listEntity = new ArrayList<>();
		if (filter != null) {
			listEntity = getDao().filter(filter);
		}
		return listEntity;

	}

	protected abstract Dao<PK, ENTITY> getDao();

}
