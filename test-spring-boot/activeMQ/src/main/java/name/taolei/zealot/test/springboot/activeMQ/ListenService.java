package name.taolei.zealot.test.springboot.activeMQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ListenService {

    @JmsListener(destination = "test-destination")
    public void receiveMessage(String message) {
        System.out.println("收到：" + message);
    }
}
