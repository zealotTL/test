package name.taolei.zealot.test.springboot.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("name.taolei.zealot.test.springboot.event")
public class Main {

    public static void main(String[] ags) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();
        TestEventPublish doServiceEventPublish = context.getBean(TestEventPublish.class);
        doServiceEventPublish.publishEvent("测试事件");
        context.close();
        System.exit(0);
    }
}
