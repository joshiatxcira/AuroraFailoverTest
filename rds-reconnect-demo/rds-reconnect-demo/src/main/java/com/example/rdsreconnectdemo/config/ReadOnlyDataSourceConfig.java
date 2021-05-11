package com.example.rdsreconnectdemo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = {"com.example.rdsreconnectdemo.data.ro"}, sqlSessionFactoryRef = "readOnlySessionFactory")
public class ReadOnlyDataSourceConfig {

	@Value("${dataSource.serverName.reader:sql}")
    private String serverName;
    
    @Value("${dataSource.port:5432}")
    private String portNumber;
    
    @Value("${dataSource.databaseName:}")
    private String databaseName;

    @Value("${datasource.username:postgres}")
    private String username;

    @Value("${datasource.password:postgres}")
    private String password;

    @Value("${hikari.ro.connection-timeout}")
    private long connectionTimeout;

    @Value("${hikari.ro.leak-detection-threshold}")
    private long leadDetectionThreshold;

    @Value("${hikari.ro.max-lifetime}")
    private long maxLifeTime;

    @Value("${hikari.ro.maximumPoolSize}")
    private int maximumPoolSize;

    @Value("${hikari.ro.validation-timeout}")
    private long validationTimeout;
    
    @Value("${hikari.datasource.driver}")
    private String dataSourceClassName;

    @Bean(name = "readOnlyDataSource")
    public HikariDataSource dataSource() {

        final HikariConfig config = setupHikariConfig();
        
        config.setDataSourceProperties(setupDataSourceProperties());

        return new HikariDataSource(config);
    }

	private Properties setupDataSourceProperties() {
		
		final Properties dsProperties = new Properties();
        
        dsProperties.put("serverName", serverName);
        dsProperties.put("portNumber", portNumber);
        dsProperties.put("databaseName", databaseName);
        dsProperties.put("user", username);
        dsProperties.put("password", password);
        
		return dsProperties;
	}

	private HikariConfig setupHikariConfig() {
		
		final HikariConfig config = new HikariConfig();

        config.setPoolName(getClass().getSimpleName());
        config.setConnectionInitSql("select 1");
        config.setConnectionTimeout(this.connectionTimeout);
        config.setLeakDetectionThreshold(this.leadDetectionThreshold);
        config.setMaxLifetime(this.maxLifeTime);
        config.setMaximumPoolSize(this.maximumPoolSize);
        config.setValidationTimeout(this.validationTimeout);
        config.setDataSourceClassName(this.dataSourceClassName);
		
        return config;
	}

    @Bean(name = "readOnlyTransactionManager")
    public DataSourceTransactionManager transactionManager() {
    	
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean(name = "readOnlySessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("readOnlyDataSource") DataSource dataSource) throws Exception {

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        return sessionFactory.getObject();
    }
}
