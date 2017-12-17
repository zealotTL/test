package name.taolei.zealot.test.springboot.activeMQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class MyMessageListener implements MessageListener {
    public void onMessage(Message message) {
        //这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
        TextMessage textMsg = (TextMessage) message;
        try {
            System.out.println("消息内容是：" + textMsg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //当收到消息后，自动调用该方法,spring配置默认监听器，负责接收消息
    @JmsListener(containerFactory = "defaultJmsListenerContainerFactory", destination = "test-topic")
    public void onMessage2(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println(tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}