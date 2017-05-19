package com.simplevat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class AbstractFilter<ENTITY> {

	private List<Predicate> predicates;


	protected void add(Predicate predicate) {
		if (predicates == null)
			predicates = new ArrayList<>();
		if (predicate != null)
			predicates.add(predicate);
	}

	protected abstract void buildPredicates(Root<ENTITY> root, CriteriaBuilder cb);

	List<Predicate> getPredicates() {
		return this.predicates;
	}

}
