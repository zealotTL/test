package group.zealot.test;

import group.zealot.test.activemq.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class TestMain {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    JmsPoolConnectionFactory jmsPoolConnectionFactory;
    @Autowired
    JmsMessagingTemplate template;

    MessageConsumer consumer;


    @JmsListener(destination = "test-listener", containerFactory = "defaultJmsListenerContainerFactory", concurrency = "1")
    public void sdf(TextMessage message, Session session) {
        try {
//            message.acknowledge();
            System.out.println(Thread.currentThread().getId() + " " + message.getText());
            session.recover();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "ActiveMQ.DLQ", containerFactory = "defaultJmsListenerContainerFactory", concurrency = "1")
    public void sdfsd(TextMessage message, Session session) {
        try {
//            message.acknowledge();
            System.out.println(Thread.currentThread().getId() + " " + message.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void send() {
        while (true) {
            template.convertAndSend("test-listener", "template test message");
        }

    }

}
