package com.simplevat.service.impl;

import com.simplevat.dao.LanguageDao;
import com.simplevat.entity.Language;
import com.simplevat.service.LanguageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mohsin on 3/11/2017.
 */
@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageDao languageDao;

    @Override
    public List<Language> getLanguages() {
        return languageDao.getLanguages();
    }

    @Override
    public Language getLanguage(Integer languageId) {
        return languageDao.getLanguageById(languageId);
    }

    @Override
    public Language getDefaultLanguage() {
        return languageDao.getDefaultLanguage();
    }
}
