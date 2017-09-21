package name.taolei.zealot.test.springboot.activeMQ;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class MyMessage implements MessageCreator {
    private String msg;

    public MyMessage(String msg) {
        this.msg = msg;
    }

    public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(msg);
    }
}
