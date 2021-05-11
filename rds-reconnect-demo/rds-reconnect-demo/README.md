# Read Me 

# Technology Stack
* AWS Aurora for Postgres - one read-write, one read-only replica
* Spring Boot 2.2.9.RELEASE
* Java 8
* [Hikari Connection Pool 3.4.5](https://github.com/brettwooldridge/HikariCP)
* [MyBatis Persistence Framework](https://mybatis.org/mybatis-3/)
* [Logback](http://logback.qos.ch/)

# Application Logic
* RdsReconnectDemoApplication is the entry point in the class - it sets up networkaddress.cache.ttl and networkaddress.cache.negative.ttl as well.
* application.properties has cluster URLs and other HikariCP settings
* On Spring boot startup, read-write (ReadWriteDataSourceConfig) and read-only (ReadOnlyDataSourceConfig) datasources are created using HikariCP. MyBatis mappers are initialized.
* Startup instantiates TestDataWriter class - this spawns 3 threads to write to test table in the database.
* Startup instantiates TestDataReader class - this spawns 8 threads to read from test table in the database.

# Test Scenario
* When application is running, failover AWS Aurora for Postgres.
* Expected:
   * TestDataWriter and TestDataReader are able to continue with zero or minimal (5-10 seconds) interruption.
* Observed:
   * TestDataReader (read-only cluster URL) is able to reconnect to new replica within 30-60 seconds
   * TestDataWriter (read-write cluster URL) is able to reconnect to new writer after 8-10 minutes
   * TestDataWriter will throw "_org.postgresql.util.PSQLException: ERROR: cannot execute INSERT in a read-only transaction_" errors while waiting to reconnect 


# Build
* Navigate to root of the project and run "mvn clean install"

# Run
* Run RdsReconnectDemoApplication as Spring Boot application

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.0-SNAPSHOT/maven-plugin/reference/html/)


