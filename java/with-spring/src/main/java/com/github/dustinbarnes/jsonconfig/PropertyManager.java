package com.github.dustinbarnes.jsonconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.File;
import java.util.Properties;

public class PropertyManager extends PropertyPlaceholderConfigurer
{
    private Config config;

    public PropertyManager()
    {
        config = ConfigFactory.systemEnvironment().withFallback(ConfigFactory.systemProperties());

        String configPath;
        try
        {
            configPath = config.getString("CONFIG_PATH");
        }
        catch ( ConfigException.Missing e )
        {
            System.out.println("!! No CONFIG_PATH setup. Aborting.");
            return;
        }

        String environment;
        try
        {
            environment = config.getString("CONFIG_ENV");
        }
        catch ( ConfigException.Missing e )
        {
            System.out.println("!! No CONFIG_ENV setup. Aborting.");
            return;
        }

        // Load our 'secrets' file
        config = config.withFallback(ConfigFactory.parseFile(new File(configPath + "/secrets.json")));

        // Load our default config file
        config = config.withFallback(ConfigFactory.parseFile(new File(configPath + "/config.json")));

        // Now we lift the environment config up to the root level
        config = config.withFallback(config.getConfig(environment));

        // Now we lift the defaults up to the root level
        config = config.withFallback(config.getConfig("default"));
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props)
    {
        if ( config.hasPath(placeholder) )
        {
            return config.getString(placeholder);
        }
        else
        {
            return super.resolvePlaceholder(placeholder, props);
        }
    }
}
