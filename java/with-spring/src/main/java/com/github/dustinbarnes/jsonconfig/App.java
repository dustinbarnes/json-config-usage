package com.github.dustinbarnes.jsonconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class App 
{
    @Value("${CONFIG_ENV}")
    private String environment;

    @Value("${db.host}")
    private String dbHost;

    @Value("${db.driver}")
    private String dbDriver;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${context}")
    private String context;

    @Value("${googleApiKey}")
    private String googleApiKey;

    public void dumpConfig()
    {
        // Now let's spit out all of our info
        System.out.println("!! env: " + environment);
        System.out.println("!! db.host: " + dbHost);
        System.out.println("!! db.driver: " + dbDriver);
        System.out.println("!! db.username: " + dbUsername);
        System.out.println("!! db.password: " + dbPassword);
        System.out.println("!! context: " + context);
        System.out.println("!! googleApiKey: " + googleApiKey);
    }
}
