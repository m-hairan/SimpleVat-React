package com.simplevat.dao.impl;

import com.simplevat.dao.CommonDao;
import com.simplevat.dao.LanguageDao;
import com.simplevat.entity.Language;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by mohsin on 3/2/2017.
 */
@Repository
public class LanguageDaoImpl implements LanguageDao{

    @PersistenceContext
    private EntityManager entityManager;

    public Language getLanguageById(Integer languageId) {
        return this.entityManager.find(Language.class,languageId);
    }
}
