package group.zealot.test.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;

@SpringBootApplication
@EnableJms
public class Main {
    @Autowired
    JmsMessagingTemplate template;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        Main main = context.getBean(Main.class);
        main.send();
        System.exit(0);
    }

    public void send() {
        template.convertAndSend("test", "test message");
    }
}
