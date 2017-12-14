package name.taolei.zealot.test.springboot.mybatises;

import name.taolei.zealot.test.springboot.mybatises.service.TestService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("name.taolei.zealot.test.springboot.mybatises")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();
        TestService testService = context.getBean(TestService.class);
        testService.doSomething();
    }
}
