package name.taolei.zealot.test.springboot.mybatis;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = MybatisAutoConfiguration.class)
public class Run {

    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }
}
