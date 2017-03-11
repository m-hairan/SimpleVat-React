package com.simplevat.test.service;

import com.simplevat.entity.Language;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */
public class TestLanguageService extends BaseManagerTest{

    @Test
    public void testGetLanguages()
    {
        List<Language> languages = this.languageService.getLanguages();

        for(Language language : languages){
            System.out.println(language.getLanguageName());
        }
    }


}
