package group.zealot.test;

import group.zealot.test.activemq.Main;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
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
    QueueBrowser browser;

    @Before
    public void config() {
        try {
            Session session = jmsPoolConnectionFactory.createConnection().createSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("test");
            consumer = session.createConsumer(queue);
            browser = session.createBrowser(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void send() {
        template.convertAndSend("test", "template test message");

    }

    @After
    public void recover() {
        try {
            Message message = consumer.receive(1000);
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("consumer recover:" + textMessage.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
