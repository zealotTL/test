package name.taolei.zealot.test.springboot.mybatises.config.master;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class DataSourceConfig {
    @Autowired private Environment environment;

    @Bean(name = "dataSourceMaster")
    public DataSource druidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(environment.getProperty("mysql.driver"));
        druidDataSource.setUrl(environment.getProperty("mysql.urlMaster"));
        druidDataSource.setUsername(environment.getProperty("mysql.username"));
        druidDataSource.setPassword(environment.getProperty("mysql.password"));
        druidDataSource.setMaxActive(Integer.valueOf(environment.getProperty("mysql.maxActive")));
        druidDataSource.setMinIdle(Integer.valueOf(environment.getProperty("mysql.minIdle")));
        druidDataSource.setInitialSize(Integer.valueOf(environment.getProperty("mysql.initialSize")));
        druidDataSource.setMaxWait(Long.valueOf(environment.getProperty("mysql.maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(
                Long.valueOf(environment.getProperty("mysql.timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(
                Long.valueOf(environment.getProperty("mysql.minEvictableIdleTimeMillis")));
        druidDataSource.setFilters("stat,wall");
        return druidDataSource;
    }
}
