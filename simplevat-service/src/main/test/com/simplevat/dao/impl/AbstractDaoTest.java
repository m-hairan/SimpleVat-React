package com.simplevat.dao.impl;

import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.Query;

/**
 * Created by Utkarsh Bhavsar on 21/03/17.
 */
public class AbstractDaoTest {

    @Test
    public void testAddPaging() throws Exception {

        Query query;
        query = Mockito.mock(Query.class);
        Mockito.when(query.setFirstResult(0)).thenReturn(query);
        Mockito.when(query.setMaxResults(AbstractDao.DEFAULT_MAX_SIZE)).thenReturn(query);
        AbstractDao.addPaging(query, 0L, 0L);
        Mockito.verify(query, Mockito.times(1)).setFirstResult(0);
        Mockito.verify(query, Mockito.times(1)).setMaxResults(AbstractDao.DEFAULT_MAX_SIZE);

        Mockito.reset(query);
        Mockito.when(query.setFirstResult(5)).thenReturn(query);
        Mockito.when(query.setMaxResults(AbstractDao.DEFAULT_MAX_SIZE)).thenReturn(query);
        AbstractDao.addPaging(query, 5L, 0L);
        Mockito.verify(query, Mockito.times(1)).setFirstResult(5);
        Mockito.verify(query, Mockito.times(1)).setMaxResults(AbstractDao.DEFAULT_MAX_SIZE);

        Mockito.reset(query);
        Mockito.when(query.setFirstResult(0)).thenReturn(query);
        Mockito.when(query.setMaxResults(30)).thenReturn(query);
        AbstractDao.addPaging(query, -1L, 30L);
        Mockito.verify(query, Mockito.times(1)).setFirstResult(0);
        Mockito.verify(query, Mockito.times(1)).setMaxResults(30);

        Mockito.reset(query);
        Mockito.when(query.setFirstResult(5)).thenReturn(query);
        Mockito.when(query.setMaxResults(20)).thenReturn(query);
        AbstractDao.addPaging(query, 5L, 20L);
        Mockito.verify(query, Mockito.times(1)).setFirstResult(5);
        Mockito.verify(query, Mockito.times(1)).setMaxResults(20);

        Mockito.reset(query);
        Mockito.when(query.setFirstResult(5)).thenReturn(query);
        Mockito.when(query.setMaxResults(AbstractDao.DEFAULT_MAX_SIZE)).thenReturn(query);
        AbstractDao.addPaging(query, 5L, null);
        Mockito.verify(query, Mockito.times(1)).setFirstResult(5);
        Mockito.verify(query, Mockito.times(1)).setMaxResults(AbstractDao.DEFAULT_MAX_SIZE);

        Mockito.reset(query);
        Mockito.when(query.setFirstResult(5)).thenReturn(query);
        Mockito.when(query.setMaxResults((int) AbstractDao.MAX_RESULTS)).thenReturn(query);
        AbstractDao.addPaging(query, 5L, AbstractDao.MAX_RESULTS + 1L);
        Mockito.verify(query, Mockito.times(1)).setFirstResult(5);
        Mockito.verify(query, Mockito.times(1)).setMaxResults((int) AbstractDao.MAX_RESULTS);

    }
}