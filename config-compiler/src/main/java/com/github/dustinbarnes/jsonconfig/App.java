package com.github.dustinbarnes.jsonconfig;

import com.typesafe.config.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;


public class App 
{
    private final ConfigRenderOptions PRETTY_JSON = ConfigRenderOptions.concise().setFormatted(true);
    private static final List<String> REMOVE_KEYS = Arrays.asList("db", "keys", "msg");

    public void dumpConfig()
    {
        Config config = ConfigFactory.parseResources(this.getClass(), "/main.conf").resolve();

        for ( String key : REMOVE_KEYS )
        {
            config = config.withoutPath(key);
        }

        printJson(config);
        printProperties(config);
    }

    private void printJson(Config resolvedConfig)
    {
        String intermediate = resolvedConfig.root().render(PRETTY_JSON);

        String finalValue = intermediate.replaceAll("\"_ref ([^\"]+)\"", "\\${$1}");
        System.out.println(finalValue);
    }

    private void printProperties(Config resolvedConfig)
    {
        for ( Map.Entry<String, ConfigValue> e : resolvedConfig.root().entrySet() )
        {
            System.out.println(e.getKey() + "=" + e.getValue().render());
        }
    }

}
