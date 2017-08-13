package name.taolei.zealot.test.springboot.mybatises.config.master;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
