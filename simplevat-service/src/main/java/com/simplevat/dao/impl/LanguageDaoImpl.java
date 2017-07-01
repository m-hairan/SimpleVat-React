package com.simplevat.dao.impl;

import com.simplevat.dao.LanguageDao;
import com.simplevat.entity.Language;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

/**
 * Created by mohsin on 3/2/2017.
 */
@Repository
public class LanguageDaoImpl implements LanguageDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Language getLanguageById(Integer languageId) {
        return this.entityManager.find(Language.class, languageId);
    }

    @Override
    public List<Language> getLanguages() {
        List<Language> languages = entityManager.createNamedQuery("allLanguages", Language.class).getResultList();
        return languages;
    }

    @Override
    public Language getDefaultLanguage() {
        List<Language> languages = getLanguages();
        if (CollectionUtils.isNotEmpty(languages)) {
            return languages.get(0);
        }
        return null;
    }
}
