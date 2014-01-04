var nconf = require('nconf');

nconf.argv().env();

var configPath = nconf.get('CONFIG_PATH');
if ( !configPath )
{
	console.log("No CONFIG_PATH setup. Aborting.");
	return 1;
}

var env = nconf.get('CONFIG_ENV');
if ( !env )
{
	console.log("No CONFIG_ENV setup. Aborting.");
	return 1;
}

// Load our 'secrets' file
nconf.file('secret', {file: configPath + '/secrets.json'});

// Load our default config file
nconf.file('basic', {file: configPath + '/config.json'});

// Now we lift the environment config up to the root level
nconf.add('env_lifted', {type: 'literal', store: nconf.get(env)});

// Now we lift the defaults up to the root level
nconf.add('defaults_lifted', {type: 'literal', store: nconf.get('default')});

// Now let's spit out all of our info
console.log("env: " + env);
console.log("db.host: " + nconf.get("db:host"));
console.log("db.driver: " + nconf.get("db:driver"));
console.log("db.username: " + nconf.get("db:username"));
console.log("db.password: " + nconf.get("db:password"));
console.log("context: " + nconf.get("context"));
console.log("googleApiKey: " + nconf.get("googleApiKey"));