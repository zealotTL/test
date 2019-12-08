package group.zealot.test;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.mq.Main;
import group.zealot.test.mq.rocketmq.RocketMqUtil;
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
    @Autowired(required = false)
    JmsMessagingTemplate template;
    @Autowired(required = false)
    RocketMqUtil rocketMqUtil;

    //    @JmsListener(destination = "ActiveMQ.DLQ", containerFactory = "defaultJmsListenerContainerFactory", concurrency = "1")
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "taolei");
        rocketMqUtil.send("Test", jsonObject);
    }

}
