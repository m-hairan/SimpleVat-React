package com.simplevat.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.dao.LanguageDao;
import com.simplevat.entity.Language;
import com.simplevat.dao.AbstractDao;

/**
 * Created by mohsin on 3/2/2017.
 */
@Repository
public class LanguageDaoImpl extends AbstractDao<Integer, Language> implements LanguageDao {


    @Override
    public Language getLanguageById(Integer languageId) {
        return this.findByPK(languageId);
    }

    @Override
    public List<Language> getLanguages() {
        List<Language> languages = this.executeNamedQuery("allLanguages");
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
