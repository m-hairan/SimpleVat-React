package com.simplevat.test;

import com.simplevat.entity.Language;
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
