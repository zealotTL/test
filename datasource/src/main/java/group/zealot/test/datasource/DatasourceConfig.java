package group.zealot.test.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class DatasourceConfig {
    @Autowired
    Environment environment;

    @Bean
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(environment.getProperty("spring.datasource.druid.driver-class-name"));
        druidDataSource.setUrl(environment.getProperty("spring.datasource.druid.url"));
        druidDataSource.setUsername(environment.getProperty("spring.datasource.druid.username"));
        druidDataSource.setPassword(environment.getProperty("spring.datasource.druid.password"));
        druidDataSource.setInitialSize(environment.getProperty("spring.datasource.druid.initialSize", Integer.class));
        druidDataSource.setMinIdle(environment.getProperty("spring.datasource.druid.minIdle", Integer.class));
        druidDataSource.setMaxActive(environment.getProperty("spring.datasource.druid.maxActive", Integer.class));
        druidDataSource.setMaxWait(environment.getProperty("spring.datasource.druid.maxWait", Long.class));
        druidDataSource.setTimeBetweenEvictionRunsMillis(environment.getProperty("spring.datasource.druid.timeBetweenEvictionRunsMillis", Long.class));
        druidDataSource.setMinEvictableIdleTimeMillis(environment.getProperty("spring.datasource.druid.minEvictableIdleTimeMillis", Long.class));
        druidDataSource.setValidationQuery(environment.getProperty("spring.datasource.druid.validationQuery"));
        druidDataSource.setTestWhileIdle(environment.getProperty("spring.datasource.druid.testWhileIdle", Boolean.class));
        druidDataSource.setTestOnBorrow(environment.getProperty("spring.datasource.druid.testOnBorrow", Boolean.class));
        druidDataSource.setTestOnReturn(environment.getProperty("spring.datasource.druid.testOnReturn", Boolean.class));
        druidDataSource.setConnectionProperties(environment.getProperty("spring.datasource.druid.connection-properties"));

        return druidDataSource;
    }

    @Bean
    public HikariDataSource hikariDataSource(HikariConfig hikariConfig) {
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        hikariConfig.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        hikariConfig.setUsername(environment.getProperty("spring.datasource.username"));
        hikariConfig.setPassword(environment.getProperty("spring.datasource.password"));
        hikariConfig.setMinimumIdle(environment.getProperty("spring.datasource.hikari.minimum-idle", Integer.class));
        hikariConfig.setMaximumPoolSize(environment.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class));
        hikariConfig.setAutoCommit(environment.getProperty("spring.datasource.hikari.auto-commit", Boolean.class));
        hikariConfig.setIdleTimeout(environment.getProperty("spring.datasource.hikari.idle-timeout", Long.class));
        hikariConfig.setPoolName(environment.getProperty("spring.datasource.hikari.pool-name"));
        hikariConfig.setMaxLifetime(environment.getProperty("spring.datasource.hikari.max-lifetime", Long.class));
        hikariConfig.setConnectionTimeout(environment.getProperty("spring.datasource.hikari.connection-timeout", Long.class));
        hikariConfig.setConnectionTestQuery(environment.getProperty("spring.datasource.hikari.connection-test-query"));
        return hikariConfig;
    }
}
