package name.taolei.zealot.test.springboot.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener implements ApplicationListener<TestEvent> {
    @Override public void onApplicationEvent(TestEvent doServiceEvent) {
        System.out.println("监听：" + doServiceEvent.getMsg());
    }
}
