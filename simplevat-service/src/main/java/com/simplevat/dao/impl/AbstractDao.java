package com.simplevat.dao.impl;

import com.simplevat.criteria.AbstractCriteria;
import com.simplevat.criteria.SortOrder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

/**
 * Created by Utkarsh Bhavsar on 20/03/17.
 */
public abstract class AbstractDao {

    @PersistenceContext
    private EntityManager entityManager;

    public static final long MAX_RESULTS = 250L;

    public static final int DEFAULT_MAX_SIZE = 25;

    protected static final int START = 0;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public static Query addPaging(Query query, Long start, Long limit) {
        if (query != null) {
            long _start = (start == null || start < START) ? START : start;
            long _limit = (limit == null || limit < 1) ? DEFAULT_MAX_SIZE : (limit > MAX_RESULTS) ? MAX_RESULTS : limit;
            query.setMaxResults((int) _limit);
            query.setFirstResult((int) _start);
        }
        return query;
    }

    protected Order addOrderCriteria(CriteriaBuilder criteriaBuilder, Path path, SortOrder sortOrder, AbstractCriteria.OrderByType orderByType) {
        Order order;
        if (SortOrder.DESC.equals(sortOrder)) {
            order = AbstractCriteria.OrderByType.NUMBER.equals(orderByType)
                    ? criteriaBuilder.desc(path)
                    : criteriaBuilder.desc(criteriaBuilder.lower(path));
        } else {
            order = AbstractCriteria.OrderByType.NUMBER.equals(orderByType)
                    ? criteriaBuilder.asc(path)
                    : criteriaBuilder.asc(criteriaBuilder.lower(path));
        }
        return order;
    }
}
