# I think I chose Makefiles just to make everybody cringe. I mean, there's like 3 billion 
# build tools for the JS world, and a few good ones for the java world. And yet, all of 
# them are a giant pain to use just to execute some commands. And shell scripts require too 
# much work, so what's a common man to do when faced with the need for structured command
# execution with minimal interference? Make. 

all: prepare run compare

run: nodejs java-with-spring java-without-spring

compare:
	@diff target/node-results target/java-with-spring-results >> target/comp-results; true
	@diff target/node-results target/java-without-spring-results >> target/comp-results; true
	@echo
	@[[ "$$(stat -f %z target/comp-results)" -ne "0" ]] && echo "Files are different :(" || echo "Files are identical :)";

nodejs: nodejs-prep nodejs-no-env nodejs-no-path nodejs-dev nodejs-stage

nodejs-prep:
	@echo "Running node..."
	@cd node; npm install 2> /dev/null

nodejs-no-env:
	@cd node; CONFIG_PATH=$$(pwd)/../config node testing.js | grep '^!!' >> ../target/node-results

nodejs-no-path:
	@cd node; CONFIG_ENV=dev node testing.js | grep '^!!' >> ../target/node-results	

nodejs-dev:
	@cd node; CONFIG_ENV=dev CONFIG_PATH=$$(pwd)/../config node testing.js | grep '^!!' >> ../target/node-results	

nodejs-stage:
	@cd node; CONFIG_ENV=stage CONFIG_PATH=$$(pwd)/../config node testing.js | grep '^!!' >> ../target/node-results	

java-with-spring: jws-prep jws-no-env jws-no-path jws-dev jws-stage

jws-prep:
	@echo "Running java-with-spring..."
	@cd java/with-spring; mvn clean > /dev/null

jws-no-env: 
	@cd java/with-spring; CONFIG_PATH=$$(pwd)/../../config mvn test | grep '^!!' >> ../../target/java-with-spring-results

jws-no-path: 
	@cd java/with-spring; CONFIG_ENV=dev mvn test | grep '^!!' >> ../../target/java-with-spring-results	

jws-dev:
	@cd java/with-spring; CONFIG_ENV=dev CONFIG_PATH=$$(pwd)/../../config mvn test | grep '^!!' >> ../../target/java-with-spring-results	

jws-stage:
	@cd java/with-spring; CONFIG_ENV=stage CONFIG_PATH=$$(pwd)/../../config mvn test | grep '^!!' >> ../../target/java-with-spring-results	

java-without-spring: jwos-prep jwos-no-env jwos-no-path jwos-dev jwos-stage

jwos-prep:
	@echo "Running java-without-spring..."
	@cd java/without-spring; mvn clean > /dev/null

jwos-no-env: 
	@cd java/without-spring; CONFIG_PATH=$$(pwd)/../../config mvn test | grep '^!!' >> ../../target/java-without-spring-results

jwos-no-path: 
	@cd java/without-spring; CONFIG_ENV=dev mvn test | grep '^!!' >> ../../target/java-without-spring-results	

jwos-dev:
	@cd java/without-spring; CONFIG_ENV=dev CONFIG_PATH=$$(pwd)/../../config mvn test | grep '^!!' >> ../../target/java-without-spring-results	

jwos-stage:
	@cd java/without-spring; CONFIG_ENV=stage CONFIG_PATH=$$(pwd)/../../config mvn test | grep '^!!' >> ../../target/java-without-spring-results	

prepare:
	@rm -rf target
	@mkdir target
	@touch target/comp-results