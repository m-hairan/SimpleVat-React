package com.simplevat.test.dao;

import com.simplevat.entity.Language;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

/**
 * Created by mohsin on 3/2/2017.
 */
public class TestLanguageDao extends BaseManagerTest {

    @Test
    public void testGetLanguageById()
    {
        Language language = this.languageDao.getLanguageById(new Integer(1));
        System.out.println("Lanuage Name Is :"+language.getLanguageName());
    }
}
