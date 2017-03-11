package com.simplevat.service.impl;

import com.simplevat.dao.LanguageDao;
import com.simplevat.entity.Language;
import com.simplevat.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */

@Service
public class LanugageServiceImpl implements LanguageService {

    @Autowired
    LanguageDao languageDao;

    @Override
    public List<Language> getLanguages() {
        return languageDao.getLanguages();
    }
}
