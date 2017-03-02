package com.simplevat.dao.impl;

import com.simplevat.dao.CommonDao;
import com.simplevat.dao.LanguageDao;
import com.simplevat.entity.Language;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mohsin on 3/2/2017.
 */
@Repository
@Transactional
public class LanguageDaoImpl extends CommonDao implements LanguageDao{
    public Language getLanguageById(Integer languageId) {
        return this.getSessionFactory().getCurrentSession().get(Language.class,languageId);
    }
}
