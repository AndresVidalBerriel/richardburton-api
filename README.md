# richardburton-api

## app.properties

A properties file `app.properties` containing app-specific configuration must be placed under `src/main/resources` folder.
Below is a template with some defaults and {PLACEHOLDERS}.

```
richardburton.home={HOME DIRECTORY TO APP DATA}

# KeyStore configuration

keystore.location=${richardburton.home}/security
keystore.name=keys@richardburton
keystore.instanceType=PKCS12
keystore.algorithm=HmacSHA256

keystore.keys.jwt=jwtsecretkey

# JWT Token configuration

jwt.ttlms=2100000
jwt.issuer=https://richardburton-api.canoas.ifrs.edu.br

# Hibernate Search configuration

hibernate.search.indexBase=${richardburton.home}/indexes
hibernate.search.test.indexBase=${richardburton.home}/test/indexes
```

## server.properties

A properties file `server.properties` containing server-specific configuration must be placed under `src/main/resources` folder.
Below is a template with some defaults and {PLACEHOLDERS}.

```
# Main server configuration

wildfly.host=localhost
wildfly.management.username={WILDFLY MANAGEMENT USER USERNAME}
wildfly.management.password={WILDFLY MANAGEMENT USER PASSWORD}
wildfly.management.port=9990
wildfly.datasource.name=java:jboss/datasources/RichardBurtonDS
wildfly.datasource.dialect=org.hibernate.dialect.PostgreSQL95Dialect
wildfly.datasource.hbm2dll.auto=create-drop

# Testing server configuration

wildfly.test.host=localhost
wildfly.test.port=8080
wildfly.test.management.username={TESTING WILDFLY INSTANCE MANAGEMENT USER USERNAME}
wildfly.test.management.password={TESTING WILDFLY INSTANCE  MANAGEMENT USER PASSWORD}
wildfly.test.management.address=127.0.0.1
wildfly.test.management.port=9990
wildfly.test.datasource.name=java:jboss/datasources/RichardBurtonTestingDS
wildfly.test.datasource.dialect=org.hibernate.dialect.H2Dialect
```


## dev.properties (THIS WILL BE REMOVED SOON)

A properties file `dev.properties` must be placed in src/main/resources folder. The format is `key=value`. It must specify:

- `INITIAL_DATA_PATH`: the location of a csv file with initial data.

This CSV contains one TranslatedBook Publication per line, formmated like:

`ORIGINAL_AUTHOR; ORIGINAL_YEAR; COUNTRY, ORIGINAL_TITLE, TRANSLATED_TITLE, TRANSLATOR, PUBLISHER`

Escape `;` putting the whole field in double quotes. For several translators, separate them with `&`.


## Wildfly Configuration

The Wildfly Application Server `standalone.xml` must be configured to support the application. The main deployment instance must have a datasource with `jndi-name` equal to the property `wildfly.datasource.name` in `server.properties`. The same applies to the test instance, whose datasources's `jndi-name` must be equal to the property `wildfly.test.datasource.name` in `server.properties`. **Both instances may be the same, case in which two different datasources must be configured for testing and production**. The datasource dialect properties must be compatible with the database server used.

[Here is an example of how to configure a PostgreSQL datasource.](https://www.stenusys.com/how_to_setup_postgresql_datasource_with_wildfly/)
