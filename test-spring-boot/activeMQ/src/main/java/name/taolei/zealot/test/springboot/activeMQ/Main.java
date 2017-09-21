package name.taolei.zealot.test.springboot.activeMQ;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@ComponentScan("name.taolei.zealot.test.springboot.activeMQ")
public class Main {

    public static void main(String[] ags) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();
        SendService sendService = context.getBean(SendService.class);
        boolean fg = true;
        while (fg) {
            sendService.send(true);
            sendService.send(false);
        }

        context.close();
        System.exit(0);
    }
}
