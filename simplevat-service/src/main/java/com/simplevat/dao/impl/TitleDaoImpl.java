package com.simplevat.dao.impl;

import com.simplevat.dao.TitleDao;
import com.simplevat.entity.Title;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by mohsin on 3/12/2017.
 */

@Repository
public class TitleDaoImpl implements TitleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Title> getTitles() {
        List<Title> titles = entityManager.createNamedQuery("allTitles",Title.class).getResultList();
        return titles;
    }
}
