package com.simplevat.dao;

import com.simplevat.entity.Language;

import java.util.List;

/**
 * Created by mohsin on 3/2/2017.
 */
public interface LanguageDao {

    public Language getLanguageById(Integer languageId);

    public List<Language> getLanguages();
}

