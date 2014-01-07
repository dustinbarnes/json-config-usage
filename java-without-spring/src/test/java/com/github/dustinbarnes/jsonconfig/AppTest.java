package com.github.dustinbarnes.jsonconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AppTest
{
    @Test
    public void testConfig()
    {
        new App().dumpConfig();
    }
}
