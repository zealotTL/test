package group.zealot.test;

import group.zealot.test.activemq.Main;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.junit.Before;
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


    @JmsListener(destination = "test-listener", containerFactory = "defaultJmsListenerContainerFactory", concurrency = "2-4")
    public void sdf(TextMessage message, Session session) {
        try {
//            message.acknowledge();
            System.out.println(Thread.currentThread().getId() + " " + message.getText());
//            session.recover();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //    @Before
    public void config() {
        ActiveMQConnectionFactory factory = (ActiveMQConnectionFactory) jmsPoolConnectionFactory.getConnectionFactory();
        RedeliveryPolicy redeliveryPolicy = factory.getRedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(false);//重发时间间隔递增
        redeliveryPolicy.setBackOffMultiplier(1.0);//重发时间间隔倍数增长，需 UseExponentialBackOff 为true
        redeliveryPolicy.setMaximumRedeliveries(3);//最大重发次数
        redeliveryPolicy.setMaximumRedeliveryDelay(10000);//最大重发间隔时间
        redeliveryPolicy.setInitialRedeliveryDelay(2000);//初始重发时间间隔

//            defaultJmsListenerContainerFactory.setConnectionFactory(jmsPoolConnectionFactory);
//            defaultJmsListenerContainerFactory.setPubSubDomain(false);


    }

    @Test
    public void send() {
        while (true) {
            template.convertAndSend("test-listener", "template test message");
        }

    }

    @Test
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
