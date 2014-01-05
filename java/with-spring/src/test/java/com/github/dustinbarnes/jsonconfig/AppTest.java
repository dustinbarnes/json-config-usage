package com.github.dustinbarnes.jsonconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context.xml")
public class AppTest
{
    @Autowired
    private App app;

    @Test
    public void testConfig()
    {
        app.dumpConfig();
    }
}
