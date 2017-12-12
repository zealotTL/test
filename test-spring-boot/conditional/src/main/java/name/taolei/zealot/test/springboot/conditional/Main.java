package name.taolei.zealot.test.springboot.conditional;

import name.taolei.zealot.test.springboot.conditional.impl.LinuxTestService;
import name.taolei.zealot.test.springboot.conditional.impl.WindowsTestService;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("name.taolei.zealot.test.springboot.conditional")
public class Main {

    @Bean
    @Conditional(WindowsCondition.class)
    public TestService windowsTestService() {
        return new WindowsTestService();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public TestService linuxTestService() {
        return new LinuxTestService();
    }

    public static void main(String[] ags) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();
        TestService testService = context.getBean(TestService.class);
        testService.doSomething();
        context.close();
        System.exit(0);
    }
}
