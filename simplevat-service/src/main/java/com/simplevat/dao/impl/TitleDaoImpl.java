package com.simplevat.dao.impl;

import com.simplevat.dao.TitleDao;
import com.simplevat.entity.Title;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Created by mohsin on 3/12/2017.
 */
@Repository
public class TitleDaoImpl extends com.simplevat.dao.AbstractDao<Integer, Title> implements TitleDao<Integer, Title> {

    @Override
    public List<Title> getTitles() {
        List<Title> titles = getEntityManager().createNamedQuery("allTitles", Title.class).getResultList();
        return titles;
    }
}
