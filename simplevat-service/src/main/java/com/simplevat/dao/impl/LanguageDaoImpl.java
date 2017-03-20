package com.simplevat.dao.impl;

import com.simplevat.dao.LanguageDao;
import com.simplevat.entity.Language;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by mohsin on 3/2/2017.
 */
@Repository
public class LanguageDaoImpl implements LanguageDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Language getLanguageById(Integer languageId) {
        return this.entityManager.find(Language.class, languageId);
    }

    @Override
    public List<Language> getLanguages() {
        List<Language> languages = entityManager.createNamedQuery("allLanguages", Language.class).getResultList();
        return languages;
    }
}
