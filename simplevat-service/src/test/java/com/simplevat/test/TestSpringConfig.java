package com.simplevat.test;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by mohsin on 3/2/2017.
 */
@ContextConfiguration({"/spring/applicationContext.xml"})
public class TestSpringConfig {

    @Test
    public void testSpring()
    {
        System.out.println("Spring Loaded Successfully");
    }
}
