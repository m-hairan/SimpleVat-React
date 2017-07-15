package com.simplevat.service;

import com.simplevat.entity.Language;
import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */
public interface LanguageService extends SimpleVatService<Integer,Language> {

    List<Language> getLanguages();

    Language getLanguage(Integer languageId);
    
    Language getDefaultLanguage();
}
