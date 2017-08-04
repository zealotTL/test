package name.taolei.zealot.test.springboot.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class TestEvent extends ApplicationEvent {
    private String msg;

    public TestEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }
}
