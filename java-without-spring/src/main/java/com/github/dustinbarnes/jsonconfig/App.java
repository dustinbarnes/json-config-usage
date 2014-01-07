package com.github.dustinbarnes.jsonconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;

import java.io.File;


public class App 
{
    public void dumpConfig()
    {
        Config config = ConfigFactory.systemEnvironment().withFallback(ConfigFactory.systemProperties());

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

        // Now let's spit out all of our info
        System.out.println("!! env: " + environment);
        System.out.println("!! db.host: " + config.getString("db.host"));
        System.out.println("!! db.driver: " + config.getString("db.driver"));
        System.out.println("!! db.username: " + config.getString("db.username"));
        System.out.println("!! db.password: " + config.getString("db.password"));
        System.out.println("!! context: " + config.getString("context"));
        System.out.println("!! googleApiKey: " + config.getString("googleApiKey"));
    }
}
