package name.taolei.zealot.test.springboot.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.security.PublicKey;

@Configuration
@ComponentScan("name.taolei.zealot.test.springboot.async")
public class Main {

    public static void main(String[] ags) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();
        AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
        for (int i = 0; i < 50; i++) {
            asyncTaskService.sys(i + "");
        }
        System.out.println("----------");
        for (int i = 0; i < 50; i++) {
            asyncTaskService.sysAsync(i + "");
        }
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.close();
        System.exit(0);
    }
}
