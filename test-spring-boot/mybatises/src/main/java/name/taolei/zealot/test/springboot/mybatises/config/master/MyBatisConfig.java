package name.taolei.zealot.test.springboot.mybatises.config.master;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:mybatis.properties")
public class MyBatisConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private Environment environment;

    private DataSource dataSourceMaster;
    @Autowired
    private DataSource setDataSourceMaster(@Qualifier("dataSourceMaster") DataSource dataSourceMaster){
        return  this.dataSourceMaster = dataSourceMaster;
    }

    @Bean(name = "sqlSessionFactoryMaster")
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceMaster);
        bean.setTypeAliasesPackage(environment.getProperty("typeAliasesPackage"));
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources(environment.getProperty("mapperLocationsMaster")));
            return bean.getObject();
        } catch (Exception e) {
            logger.error("resolver.getResources(\"classpath:/mybatisMaster/*Mapper.xml\")", e);
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "sqlSessionTemplateMaster")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactoryMaster") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManagerMaster")
    @Primary
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSourceMaster);
    }
}
