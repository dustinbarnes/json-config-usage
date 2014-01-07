# I think I chose Makefiles just to make everybody cringe. I mean, there's like 3 billion 
# build tools for the JS world, and a few good ones for the java world. And yet, all of 
# them are a giant pain to use just to execute some commands. And shell scripts require too 
# much work, so what's a common man to do when faced with the need for structured command
# execution with minimal interference? Make. 

CP=CONFIG_PATH=$$(pwd)/../config
CED=CONFIG_ENV=dev
CES=CONFIG_ENV=stage
NODE_CMD=node testing.js | grep '^!!' >> ../target/node-results
JWS_CMD=mvn test | grep '^!!' >> ../target/jws-results
JWOS_CMD=mvn test | grep '^!!' >> ../target/jwos-results

all: prepare run compare

run: nodejs java-with-spring java-without-spring

compare:
	@diff target/node-results target/jws-results >> target/comp-results; true
	@diff target/node-results target/jwos-results >> target/comp-results; true
	@echo
	@[[ "$$(stat -f %z target/comp-results)" -ne "0" ]] && echo "Files are different :(" || echo "Files are identical :)";

nodejs: nodejs-prep nodejs-no-env nodejs-no-path nodejs-dev nodejs-stage

nodejs-prep:
	@echo "Running node..."
	@cd node; npm install 2> /dev/null

nodejs-no-env:
	@cd node; $(CP) $(NODE_CMD)

nodejs-no-path:
	@cd node; $(CED) $(NODE_CMD)

nodejs-dev:
	@cd node; $(CED) $(CP) $(NODE_CMD)

nodejs-stage:
	@cd node; $(CES) $(CP) $(NODE_CMD)

java-with-spring: jws-prep jws-no-env jws-no-path jws-dev jws-stage

jws-prep:
	@echo "Running java-with-spring..."
	@cd java-with-spring; mvn clean > /dev/null

jws-no-env: 
	@cd java-with-spring; $(CP) $(JWS_CMD)

jws-no-path: 
	@cd java-with-spring; $(CED) $(JWS_CMD)

jws-dev:
	@cd java-with-spring; $(CED) $(CP) $(JWS_CMD)

jws-stage:
	@cd java-with-spring; $(CES) $(CP) $(JWS_CMD)

java-without-spring: jwos-prep jwos-no-env jwos-no-path jwos-dev jwos-stage

jwos-prep:
	@echo "Running java-without-spring..."
	@cd java-without-spring; mvn clean > /dev/null

jwos-no-env: 
	@cd java-without-spring; $(CP) $(JWOS_CMD)

jwos-no-path: 
	@cd java-without-spring; $(CED) $(JWOS_CMD)

jwos-dev:
	@cd java-without-spring; $(CED) $(CP) $(JWOS_CMD)

jwos-stage:
	@cd java-without-spring; $(CES) $(CP) $(JWOS_CMD)

prepare:
	@rm -rf target
	@mkdir target
	@touch target/comp-results