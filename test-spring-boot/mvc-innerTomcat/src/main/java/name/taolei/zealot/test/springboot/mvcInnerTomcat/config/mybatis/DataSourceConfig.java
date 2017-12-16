package name.taolei.zealot.test.springboot.mvcInnerTomcat.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:jdbc.properties")

public class DataSourceConfig {
    @Autowired private Environment environment;

    //    @Bean
    //    public DataSource dataSource(){
    //        DataSource dataSource = new DataSource();
    //        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    //        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8");
    //        dataSource.setUsername("root");
    //        dataSource.setPassword("1994728");
    //        return dataSource;
    //    }
    @Bean(name = "dataSource")
    public DataSource druidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(environment.getProperty("mysql.driver"));
        druidDataSource.setUrl(environment.getProperty("mysql.url"));
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


    @Bean
    public ServletRegistrationBean druidServletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        servletRegistrationBean.addInitParameter("allow", environment.getProperty("allow"));
        servletRegistrationBean.addInitParameter("deny",environment.getProperty("deny"));
        servletRegistrationBean.addInitParameter("loginUsername", environment.getProperty("loginUsername"));
        servletRegistrationBean.addInitParameter("loginPassword", environment.getProperty("loginPassword"));
        return servletRegistrationBean;
    }

    /**
     * 注册DruidFilter拦截
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean duridFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<String, String>();
        //设置忽略请求
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
