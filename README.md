# richardburton-api

## Keytool Generator

`keytoolcommand = keytool -genseckey -keyalg AES -alias jwtsecretkey -keystore keys@richardburton -keysize 256`

## app.properties

A properties file `app.properties` must be placed in src/main/resources folder. The format is `key=value`. It must specify:

- `keystoreloc`: the location of the KeyStore generated with Keytool.
- `jwtkeyalias`: the alias of the Key generated with Keytool.
- `keystorepsw`: the password of the KeyStore generated with Keytool.

## dev.properties

A properties file `dev.properties` must be placed in src/main/resources folder. The format is `key=value`. It must specify:

- `INITIAL_DATA_PATH`: the location of a csv file with initial data.

This CSV contains one TranslatedBook Publication per line, formmated like:

`ORIGINAL_AUTHOR; ORIGINAL_YEAR; COUNTRY, ORIGINAL_TITLE, TRANSLATED_TITLE, TRANSLATOR, PUBLISHER`

Escape `;` putting the whole field in double quotes. For several translators, separate them with `&`.

## persistence.xml

A configuration file for JPA `persistence.xml` must be placed in src/main/resources/META-INF folder.
Here is an example, of what it should look like.

- `LUCENE_INDEX_BASE` must be set to the actual Apache Lucene index directory. It could be any directory where the user has enough privileges to read, write, create and delete files. If this is not set, Hibernate Search won't work.

```
<persistence xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">

    <persistence-unit name="richardburton_development">
        <description>RichardBurton Development DB</description>
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <jta-data-source>java:jboss/datasources/RichardBurtonDevelopDS</jta-data-source>

        <properties>
            <property name="hibernate.show_sql" value="false"/>
            <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
            <property name="hibernate.search.default.directory_provider" value="filesystem"/>
            <property name="hibernate.search.default.indexBase" value="LUCENE_INDEX_BASE"/>
        </properties>

    </persistence-unit>
</persistence>
```
</persistence>


## standalone.xml

Wildfly configuration `standalone.xml` file must include Development and Testing datasources. In this case, these are configured as a MySQL Database and a H2 Database, respectively. `sa` is the default username and password for the H2 database embedded in Wildfly. `DEVELOPMENT_DATABASE_USERNAME` and `DEVELOPMENT_DATABASE_USERPASSWORD` MUST be replaced with the actual ones, or deployment will fail.

```
<datasources>
  <datasource jndi-name="java:jboss/datasources/RichardBurtonDevelopDS" pool-name="richardburton-develop-ds-pool" enabled="true" use-java-context="true">
    <connection-url>jdbc:mysql://localhost:3306/richardburton_develop?useUnicode=true&amp;character_set_server=utf8mb4&amp;autoReconnect=true&amp;zeroDateTimeBehavior=convertToNull</connection-url>
    <driver>mysql</driver>
    <security>
      <user-name>DEVELOPMENT_DATABASE_USERNAME</user-name>
      <password>DEVELOPMENT_DATABASE_USERPASSWORD</password>
    </security>
  </datasource>
   
  <datasource jndi-name="java:jboss/datasources/RichardBurtonTestingH2DS" pool-name="richardburton-testing-h2ds-pool" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
    <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
    <driver>h2</driver>
    <security>
      <user-name>sa</user-name>
      <password>sa</password>
    </security>
   </datasource>
   <drivers>
      <driver name="h2" module="com.h2database.h2">
        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
      </driver>
      <driver name="mysql" module="com.mysql">
          <driver-class>com.mysql.cj.jdbc.Driver</driver-class>
          <xa-datasource-class>com.mysql.cj.jdbc.MysqlXADataSource</xa-datasource-class>
      </driver>
  </drivers>
</datasources>
```

## test-persistence.xml

A `test-persistence.xml` file must be placed inside `src/test/resources`. With the above datasource configuration, it sould look like this.

- `LUCENE_INDEX_BASE` must be set to the actual Apache Lucene index directory. It could be any directory where the user has enough privileges to read, write, create and delete files. If this is not set, Hibernate Search won't work.

```
<persistence xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">

    <persistence-unit name="richardburton_testing">
        <description>RichardBurton Testing DB</description>
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <jta-data-source>java:jboss/datasources/RichardBurtonTestingH2DS</jta-data-source>

        <properties>
            <property name="hibernate.show_sql" value="false"/>
            <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
            <property name="hibernate.search.default.directory_provider" value="filesystem"/>
            <property name="hibernate.search.default.indexBase" value="LUCENE_INDEX_BASE"/>
        </properties>

    </persistence-unit>

</persistence>
```

## arquillian.xml

An Arquillian Configuration File must be placed inside `src/test/resources`, in order to run in-container integration tests. It should look like this. `WILDFLY_USERNAME` and `WILDFLY_PASSWORD` MUST be the actual credentials to a Wildfly Administrative User. 

```
<arquillian xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns="http://jboss.org/schema/arquillian" xsi:schemaLocation="
        http://jboss.org/schema/arquillian
        http://jboss.org/schema/arquillian/arquillian_1_0.xsd">

    <!-- Sets the protocol which is how Arquillian talks and executes the tests inside the container -->
    <defaultProtocol type="Servlet 3.0"/>

    <!-- Configuration to be used when the WildFly remote profile is active -->
    <container qualifier="wildfly-remote" default="true">
        <protocol type="Servlet 3.0">
            <property name="host">localhost</property>
            <property name="port">8080</property>
        </protocol>
        <configuration>
            <property name="managementAddress">127.0.0.1</property>
            <property name="managementPort">9990</property>
            <property name="username">WILDFLY_USERNAME</property>
            <property name="password">WILDFLY_PASSWORD</property>
        </configuration>
    </container>
</arquillian>
```
            
