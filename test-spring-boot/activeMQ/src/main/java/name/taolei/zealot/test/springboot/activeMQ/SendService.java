package name.taolei.zealot.test.springboot.activeMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendService {
    @Autowired @Qualifier("pubJmsTemplate") JmsTemplate pubJmsTemplate;
    @Autowired @Qualifier("subJmsTemplate") JmsTemplate subJmsTemplate;

    /**
     * 是否发送主体消息true/队列消息false
     *
     * @param fg
     */
    public void send(boolean fg) {
        if (fg) {
            pubJmsTemplate.send("test-destination", new MyMessage());
        } else {
            subJmsTemplate.send("test-destination", new MyMessage());
        }

    }
}
