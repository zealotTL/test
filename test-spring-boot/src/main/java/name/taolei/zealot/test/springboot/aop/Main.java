package name.taolei.zealot.test.springboot.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration @ComponentScan("name.taolei.zealot.test.springboot.aop") @EnableAspectJAutoProxy public class Main {

    public static void main(String[] ags) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();
        AopService aopService = context.getBean(AopService.class);
        aopService.add();
        context.close();
        System.exit(0);
    }
}
