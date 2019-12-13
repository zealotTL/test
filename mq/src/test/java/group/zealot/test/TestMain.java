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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Component
public class TestMain {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    JmsPoolConnectionFactory jmsPoolConnectionFactory;
    @Autowired(required = false)
    JmsMessagingTemplate template;
    @Autowired(required = false)
    RocketMqUtil rocketMqUtil;

    @Autowired(required = false)
    TestRocketmq testMain;

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
    public void send() throws InterruptedException {
        int i = 0;
//        CountDownLatch latch = new CountDownLatch(1000000);
//        while (i < 1000000) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name1", "taoleitaoleitaolei");
//        jsonObject.put("name2", "taoleitaoleitaolei");
//        jsonObject.put("name3", "taoleitaoleitaolei");
//        jsonObject.put("name4", "taoleitaoleitaolei");
//        jsonObject.put("name5", "taoleitaoleitaolei");
//        jsonObject.put("name6", "taoleitaoleitaolei");
//        jsonObject.put("name7", "taoleitaoleitaoleitaoleitaoleitaoleitaoleitaoleitaolei");
//        jsonObject.put("name8", "taoleitaoleitaoleitaoleitaoleitaoleitaoleitaoleitaolei");
//        jsonObject.put("name9", "taoleitaoleitaoleitaoleitaoleitaoleitaoleitaoleitaolei");
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//        rocketMqUtil.send("Test", jsonObject);
//            i++;
//        }
//        latch.await();
        System.out.println();
    }


}
