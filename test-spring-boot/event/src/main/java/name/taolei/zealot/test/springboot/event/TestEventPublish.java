package name.taolei.zealot.test.springboot.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TestEventPublish {
    @Autowired ApplicationContext applicationContext;

    public void publishEvent(String msg) {
        applicationContext.publishEvent(new TestEvent(this, msg));
    }
}
