config-compiler
===============

So all the other config stuff is cool. We can use JSON, use environment lifting, etc. But JSON is kind of a pain to get perfectly correct every time. 

You'll notice that I use a project called [Typesafe Config](https://github.com/typesafehub/config) in the two java examples. If you look at their page, they recommend the use of a language they call HOCON - Human-Optimized Config Object Notation. It takes a lot of the painful parts of JSON-as-config and removes them. And it has variable substitution. And it can use dotted-notation to add keys to nested objects. In shorts, it's a lot better. 

So this project is going to read a bunch of config files in HOCON format through Typesafe Config, then output valid JSON- and properties-based configuration files. This lets the nconf (node.js project) still use the same set of configuration files, as well as those people who still insist on .properties files, while giving the config engineer all the power of HOCON. What's not to love? 