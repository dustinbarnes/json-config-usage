json-config
===========

This project is a proof-of-concept showing a json-based configuration that can be used in node.js, as well as java. In the java world, we have two examples: one with Spring Framework, and one without. 

This means that every node and java project can share the same configuration!

Why JSON?
---------

JSON has several benefits when it comes to configuration. The natural structure of objects often better support the separation of distinct blocks. 

JSON objects can be merged smartly

Syntax can be validated

Easily machine- and human-readable

Design Goals
------------

The design goals are quite straightforward:

1. The command-line arguments and environment variables override the configuration.
2. We can lift configuration sections (see below)
3. We can overlay a secrets file (that would contain passwords, api keys, oauth tokens, etc) on top of the main config file
4. We break out configuration per environment, but the application is ignorant of this distinction

Config Files
------------
```config/config.json``` has all of our default configuration, broken out by environment, with a "defaults" block.

```config/secrets.json``` has our secret key/value pairs. In a production environment, developers could view and modify the ```config.json``` file, but not the ```secrets.json``` file. 

Configuration Lifting
---------------------

It is often a good idea to break out configuration by environment, with a set of defaults in case no value is provided. So, given this json block: 

    {
        "dev": {
            "host": "foo.com"
        },
        "default": {
            "login": "username"
        }
    }
    
We want to have the code be ignorant of the environment- and default-specific sections, and not have to perform the fallback operations manually every time we ask for a key. After the config engines have done their work, we want the final config object to behave like this one: 

    { 
        "host": "foo.com",
        "login": "username"
    }
 
This lets the rest of our code be completely agnostic to the environment and fallback details. The way we implement this is specific to each project, but the end result should be the same. 

The way we accomplish this is through "lifting". We take a block and promote it to the top level manually, properly setting the precedence of lookups in the configuration engine. In reality, the final configuration object will look more like this: 

    {
        "dev": {
            "host": "foo.com"
        },
        "default": {
            "login": "username"
        },
        "host": "foo.com",
        "login": "username"
    }
    
